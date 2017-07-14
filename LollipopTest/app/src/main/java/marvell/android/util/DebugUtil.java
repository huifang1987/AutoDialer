package marvell.android.util;

import marvell.android.testcase.MO;
import marvell.android.testsuit.TestingData;
import marvell.android.testsuit.TConstant;
import android.content.Context;

public class DebugUtil {

	public static void mo(Context context) {

		MO mo = new MO(context, TConstant.secondSlotId, "10010");

		mo.startTest();
		
		mo.getResult();

	}
	
	
	public static void printCallColumnNames(Context context) {

		CallUtil.getAllOutgoingCall(context);
		

	}
	
	public static void endCall(Context context) {

		MTelephonyManager.endCall(context);

	}
	
	public static void initialData(Context context) {

		TestingData.initialData(context);
		
	}
	
	
	public static void checkGetMsisdn(Context context) {

		for (int slotId = TConstant.firstSlotId; slotId <= TConstant.secondSlotId; slotId++) {

			MTelephonyManager.getMsisdn(context, slotId);

		}

	}

	public static void checkGetIMEI(Context context) {

		for (int slotId = TConstant.firstSlotId; slotId <= TConstant.secondSlotId; slotId++) {

			MTelephonyManager.getIMEI(context, slotId);

		}

	}

	public static void checkGetCallState(Context context) {

		for (int slotId = TConstant.firstSlotId; slotId <= TConstant.secondSlotId; slotId++) {

			MTelephonyManager.getCallState(context, slotId);

		}

		MTelephonyManager.getCallState(context);
	}

	public static void checkGetIMSI(Context context) {

		for (int slotId = TConstant.firstSlotId; slotId <= TConstant.secondSlotId; slotId++) {

			MTelephonyManager.getIMSI(context, slotId);

		}

	}

	public static void checkHasSIM(Context context) {

		for (int slotId = TConstant.firstSlotId; slotId <= TConstant.secondSlotId; slotId++) {

			if (MTelephonyManager.hasSIM(context, slotId))

				MTelephonyManager.getIMSI(context, slotId);
		}

	}

}
