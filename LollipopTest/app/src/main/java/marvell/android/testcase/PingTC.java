package marvell.android.testcase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import marvell.android.testsuit.ITC;
import marvell.android.testsuit.TConstant;
import marvell.android.util.LogUtil;
import marvell.android.util.MathUtil;
import marvell.android.util.PingUtil;
import marvell.android.util.StringUtil;
import android.content.Context;

public class PingTC implements ITC {

	private Context context;

	private int slotId;


	private int result;

	private String command;

	private String detail;

	private String transmitted;
	private String received;
	private String loss;
	private String time;

	public PingTC(Context context, int slotId) {

		this.context = context;

		this.slotId = slotId;


		result = TConstant.unkwnownResult;

		detail = "";
		command = "";
		transmitted = "";
		received = "";
		loss = "";
		time = "";

	}

	@Override
	public void startTest() {

		result = TConstant.apSendPingFailResult;

		LogUtil.d("now start to do the ping test");

		int number = MathUtil.getRandomInRanget(20, 40);

		int size = TConstant.getPingPacketSize();

		String host = TConstant.getRandHost();

		// command = "/system/bin/ping -c " + number + " -s " + size + " " +
		// "www.hads.com";

		command = "/system/bin/ping -c " + number + " -s " + size + " " + host;

		List<String> responseList = PingUtil.execute(command);

		for (String response : responseList) {

			if (response.contains("transmitted")) {

				String[] data = response.split(",");

				for (int i = 0; i < data.length; i++) {

					if (data[i].contains("transmitted")) {
						transmitted = StringUtil.getNumberString(data[i]);

					} else if (data[i].contains("received")) {

						received = StringUtil.getNumberString(data[i]);

					} else if (data[i].contains("loss")) {

						loss = StringUtil.getNumberString(data[i]);

						result = TConstant.checkPingResult(loss);

					} else if (data[i].contains("time")) {
						time = StringUtil.getNumberString(data[i]);

					}

				}

				detail = response;
			}

			if (response.contains("rtt"))
				detail = detail + ", " + response;

		}

		// detail = responseList.toString();

		if (result == TConstant.noPingResponseResult) {

			String newCommand = "/system/bin/ping -c 4 -s " + size
					+ " 120.193.110.48";

			detail = "No response and then ping the fix address: "
					+ PingUtil.execute(newCommand).toString();

		}

		if (result == TConstant.apSendPingFailResult) {

			detail = "this command is not successfully sent ";
		}

		LogUtil.d("now end the ping test");

		LogUtil.d("detail=" + detail);
	}

	@Override
	public Map<String, String> getResult() {

		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("result", String.valueOf(result));

		resultMap.put("detail", detail);

		resultMap.put("transmitted", transmitted);
		resultMap.put("received", received);
		resultMap.put("loss", loss);
		resultMap.put("time", time);
		resultMap.put("command", command);

		return resultMap;
	}

}
