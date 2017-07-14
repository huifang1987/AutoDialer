package marvell.android.testcase;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import marvell.android.testsuit.ITC;
import marvell.android.testsuit.TConstant;
import marvell.android.util.FileUtil;
import marvell.android.util.LogUtil;
import marvell.android.util.MathUtil;
import marvell.android.util.MyTransferListener;
import marvell.android.util.StringUtil;
import marvell.android.util.TimeUtil;
import android.content.Context;
import android.net.TrafficStats;

public class DownloadTC implements ITC {

	private Context context;

	private int slotId;

	private int result;

	private String detail;

	private FTPClient client;
	private MyTransferListener transferListener;
	private int state;
	private String downloadLocalFileName;

	public DownloadTC(Context context, int slotId) {

		this.context = context;

		this.slotId = slotId;

		result = TConstant.unkwnownResult;

		detail = "";

	}

	@Override
	public void startTest() {

		client = new FTPClient();
		transferListener = new MyTransferListener();

		DownloadThread download = new DownloadThread();
		download.start();

		int during = TConstant.getFTPDuring();

		int timeout = 0;

		state = transferListener.getState();

		int noThroughputTimes = 0;

		int lowThroughputTimes = 0;

		Date noThroughputDate = new Date();
		Date lowThroughputDate = new Date();

		while (state < TConstant.completedState
				&& noThroughputTimes <= TConstant.maxNoThroughputTimes
				&& lowThroughputTimes <= TConstant.maxLowThroughputTimes) {

			long nowStartMobileRxBytes = TrafficStats.getMobileRxBytes();
			Date nowStartDate = new Date();

			TimeUtil.sleep(2, TimeUtil.s);

			long nowEndMobileRxBytes = TrafficStats.getMobileRxBytes();

			Date nowEndDate = new Date();

			float dl = (nowEndMobileRxBytes - nowStartMobileRxBytes)
					* 8
					/ TimeUtil.getDistanceByType(nowStartDate, nowEndDate,
							TimeUtil.ms);

			// LogUtil.d("C_DL=" + dl + "Kbps");

			if (TConstant.isNoThroughput((int) dl))
				noThroughputTimes++;

			else {
				noThroughputTimes = 0;
				noThroughputDate = new Date();
			}

			if (TConstant.isLowThroughput((int) dl)) {
				lowThroughputTimes++;
			} else {
				lowThroughputDate = new Date();
				lowThroughputTimes = 0;
			}

			timeout = timeout + 2;

			if (timeout > during)
				disConnectFTP();

			state = transferListener.getState();

			LogUtil.d("state=" + state + "/during=" + during + "/timeout="
					+ timeout + "/C_DL=" + dl + "Kbps" + "/noTimes="
					+ noThroughputTimes + "/lowTimes=" + lowThroughputTimes);

		}

		disConnectFTP();

		if (null != downloadLocalFileName && !"".equals(downloadLocalFileName)) {

			LogUtil.d("downloadLocalFileName=" + downloadLocalFileName);

			if (FileUtil.deleteFile(downloadLocalFileName))
				LogUtil.d("success deleteFile" + downloadLocalFileName);
			else
				LogUtil.d("Fail to delete the file " + downloadLocalFileName);
		}

		if (state == TConstant.completedState
				|| state == TConstant.abortedState) {

			result = TConstant.passResult;

			detail = "successfully finish the download";

		} else if (noThroughputTimes >= TConstant.maxNoThroughputTimes) {

			result = TConstant.noDLFailResult;

			detail = "From " + TimeUtil.getDateString(noThroughputDate)
					+ ", no data tranfer more than "
					+ (TConstant.maxNoThroughputTimes * 2) + "s.";

		} else if (lowThroughputTimes >= TConstant.maxLowThroughputTimes) {

			result = TConstant.lowDLFailResult;

			detail = "From " + TimeUtil.getDateString(lowThroughputDate)
					+ ", low data tranfer more than "
					+ (TConstant.maxLowThroughputTimes * 2) + "s.";

		} else {

			result = TConstant.initialFTPFailResult;

			detail = "Fail to initial ftp transfer";
		}

	}

	@Override
	public Map<String, String> getResult() {

		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("result", String.valueOf(result));

		resultMap.put("detail", detail);

		resultMap.putAll(transferListener.getThroughput());

		return resultMap;
	}

	class DownloadThread extends Thread {

		@Override
		public void run() {

			try {

				client.connect(TConstant.ftpAddress, TConstant.ftpPort);
				LogUtil.d("successfully connect");

				client.login(TConstant.ftpUsername, TConstant.ftpPassword);
				LogUtil.d("successfully login");

				client.changeDirectory(TConstant.ftpDownloadPath);
				LogUtil.d("successfully changeDirectory");

				String[] names = client.listNames();

				if (names.length > 1) {

					int rand = MathUtil.getRandomInRanget(0, names.length - 1);

					String remoteFileName = names[rand];

					FileUtil.mkDir(TConstant.ftpLocalPath);

					String downloadRemoteFileName = TConstant.ftpDownloadPath
							+ File.separator + remoteFileName;

					downloadLocalFileName = TConstant.ftpLocalPath
							+ File.separator + StringUtil.getUUID();

					LogUtil.d("remote=" + downloadRemoteFileName + "/local="
							+ downloadLocalFileName);

					client.setPassive(true);

					client.download(downloadRemoteFileName, new File(
							downloadLocalFileName), transferListener);
				}

			} catch (IllegalStateException e) {
				LogUtil.d("IllegalStateException");

			} catch (IOException e) {
				LogUtil.d("IOException");
			} catch (FTPIllegalReplyException e) {
				LogUtil.d("FTPIllegalReplyException");
			} catch (FTPException e) {
				LogUtil.d("FTPException");
			} catch (FTPDataTransferException e) {
				LogUtil.d("FTPDataTransferException");
			} catch (FTPAbortedException e) {
				LogUtil.d("FTPAbortedException");
			} catch (FTPListParseException e) {
				LogUtil.d("FTPListParseException");
			}

		}

	}

	private void disConnectFTP() {

		try {
			client.abortCurrentDataTransfer(true);
			LogUtil.d("disconnect the data transfer");

		} catch (IOException e) {
			LogUtil.d("IOException");
		} catch (FTPIllegalReplyException e) {
			LogUtil.d("FTPIllegalReplyException");
		}
	}
	
	
}
