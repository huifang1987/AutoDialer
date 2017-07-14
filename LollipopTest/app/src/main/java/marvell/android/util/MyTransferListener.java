package marvell.android.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import marvell.android.testsuit.TConstant;

import android.net.TrafficStats;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class MyTransferListener implements FTPDataTransferListener {

	private long startMobileRxBytes;

	private long startMobileTxBytes;

	private Date startDate;

	private int state;

	private int dl;

	private int ul;

	private long totalRxBytes;

	private long totalTxBytes;

	private long during;

	public MyTransferListener() {

		state = TConstant.waitState;
		dl = 0;
		ul = 0;

	}

	@Override
	public void aborted() {

		state = TConstant.abortedState;

		caculateThroughput();

		LogUtil.d("aborted");
	}

	@Override
	public void completed() {

		state = TConstant.completedState;
		// TODO Auto-generated method stub
		LogUtil.d("completed");

		caculateThroughput();

	}

	@Override
	public void failed() {
		state = TConstant.failedState;
		// TODO Auto-generated method stub
		LogUtil.d("failed");
	}

	@Override
	public void started() {

		state = TConstant.startedState;
		// TODO Auto-generated method stub

		LogUtil.d("started");

		startMobileTxBytes = TrafficStats.getMobileTxBytes();

		startMobileRxBytes = TrafficStats.getMobileRxBytes();

		startDate = new Date();

	}

	@Override
	public void transferred(int arg0) {

		state = TConstant.transferredState;

	}

	public int getState() {
		return state;
	}

	public Map<String, String> getThroughput() {

		Map<String, String> throughput = new HashMap<String, String>();

		throughput.put("during", String.valueOf(during));

		throughput.put("totalRxBytes", String.valueOf(totalRxBytes));

		throughput.put("totalTxBytes", String.valueOf(totalTxBytes));

		throughput.put("dl", String.valueOf(dl));

		throughput.put("ul", String.valueOf(ul));

		return throughput;
	}

	private void caculateThroughput() {

		long endMobileTxBytes = TrafficStats.getMobileTxBytes();

		long endMobileRxBytes = TrafficStats.getMobileRxBytes();

		Date endDate = new Date();

		during = TimeUtil.getDistanceByType(startDate, endDate, TimeUtil.s);

		totalRxBytes = endMobileRxBytes - startMobileRxBytes;

		totalTxBytes = endMobileTxBytes - startMobileTxBytes;
		
		LogUtil.d("totalRxBytes=" + totalRxBytes);
		
		LogUtil.d("totalTxBytes=" + totalTxBytes);

		if (during > 0) {

			dl = (int) (totalRxBytes * 8 / during / 1000);

			ul = (int) (totalTxBytes * 8 / during / 1000);
		}
	}

}
