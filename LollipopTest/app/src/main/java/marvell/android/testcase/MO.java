package marvell.android.testcase;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import marvell.android.testsuit.ITC;
import marvell.android.testsuit.TConstant;
import marvell.android.testsuit.TConstant.Result;
import marvell.android.util.CallUtil;
import marvell.android.util.LogUtil;
import marvell.android.util.TimeUtil;

public class MO implements ITC {

	private Context context;

	private int slotId;

	private int result;

	private String detail;

	private String destNum;

	public MO(Context context, int slotId, String destNum) {

		this.context = context;

		this.slotId = slotId;

		this.destNum = destNum;

		this.result = TConstant.unkwnownResult;

		this.detail = "";

	}

	@Override
	public void startTest() {

		//String dutNumber = TConstant.getDutNumber(context, slotId);
		//int duration = TConstant.getCallDuration(dutNumber);
		String dutNumber = destNum;
		int duration = 30; //仅保持15秒

		detail = "mo " + dutNumber + ", hope call can keep " + duration + "s";

		Log.d("fanghui", "destNum: "+dutNumber);


		CallUtil.setIdleMode(context);

		if (CallUtil.initialCall(context, slotId, dutNumber)) {

			if (CallUtil.isConnected(context, slotId, duration)) {

				CallUtil.setIdleMode(context);

				result = TConstant.passResult;

				detail = detail + " , all is ok";

			} else {

				TimeUtil.sleep(2, TimeUtil.s);

				int callConnectedTime = CallUtil.lastOutgoingCallIsConnected(
						context, dutNumber, slotId);

				if (callConnectedTime > 0) {

					result = TConstant.callDropResult;
					detail = detail + ", but call only keep "
							+ callConnectedTime + "s";

				} else {
					result = TConstant.callFailResult;

					detail = detail + ", but call did not connect";

				}
			}
		} else {

			result = TConstant.apCallFailResult;

			detail = detail + ", but ap did not send mo";
		}
	}


	@Override
	public Map<String, String> getResult() {

		Map<String, String> map = new HashMap<String, String>();

		map.put("result", String.valueOf(result));

		map.put("detail", detail);

		LogUtil.d("result=" + String.valueOf(result));
		LogUtil.d("detail=" + detail);

		return map;
	}


}
