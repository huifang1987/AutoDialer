package marvell.android.testsuit;

import java.io.File;

import marvell.android.util.LogUtil;
import marvell.android.util.MTelephonyManager;
import marvell.android.util.MathUtil;
import android.content.Context;
import android.os.Environment;

public class TConstant {

	// TC
	final public static int moTCIndex = 1;
	final public static String moTCName = "mo";
	final public static int downloadTCIndex = 2;
	final public static String downloadTCName = "download";
	final public static int pingTCIndex = 3;
	final public static String pingTCName = "ping";
	final public static int browserTCIndex = 4;
	final public static String browserTCName = "browser";

	// Test percent
	final public static int moTCRate = 100;
	final public static int downloadTCRate = 1;
	final public static int pingTCRate = 60;
	final public static int browserTCRate = 40;
	final public static int totalTCRate = moTCRate + downloadTCRate
			+ pingTCRate;

	final public static int unknowTCIndex = 99;
	final public static String unknowTCName = "unknown case";

	final public static int firstTCIndex = moTCIndex;
	final public static int lastTCIndex = pingTCIndex;

	final public static int minIntervalSeconds = 5;
	final public static int maxIntervalSeconds = 10;

	final public static int testingCaseMsg = 1;
	final public static int finishTestMsg = 3;
	final public static int sleepSecondsMsg = 2;
	final public static int wait4ResetMsg = 4;

	final public static int unkwnownResult = 0;
	final public static int passResult = 1;
	final public static int failResult = 2;
	final public static int callDropResult = 3;
	final public static int callFailResult = 4;
	final public static int apCallFailResult = 5;
	final public static int initialFTPFailResult = 6;
	final public static int noDLFailResult = 7;
	final public static int lowDLFailResult = 8;
	final public static int noPingResponseResult = 9;
	final public static int unAcceptableLostPingPackagesResult = 10;
	final public static int apSendPingFailResult = 11;

	final public static int noSIM = 0;
	final public static int onlySIM1 = 1;
	final public static int onlySIM2 = 2;
	final public static int twoSIM = 3;

	final public static int maxContinousFailNumber = 10;
	final public static int maxResetNumber = 15;

	final public static String internalSD = Environment
			.getExternalStorageDirectory().getPath();

	final public static String apLogPath = internalSD + "/aplog";
	final public static String resultLogPath = internalSD + "/testResult";
	final public static String resultXmlFileName = "result.xml";
	final public static String configureXml = internalSD+"/fieldTrail"
			+ "/configure.xml";

	final public static int minutesBeforeTest = 100;
	final public static int secondsAfterTest = 40;
	final public static int wait4AcatSecond = 20;

	final public static String exceedMaxFailLimit_Exist = "continue fail for "
			+ maxContinousFailNumber + " times";
	final public static String foundAssert_Exsit = "assert ";
	final public static String normal_Exist = "Manually stop test";

	final public static int mainActivity = 1;
	final public static int configureActivity = 2;

	final public static String ftpAddress = "120.193.110.48";
	final public static String ftpUsername = "phsftpusr01";
	final public static String ftpPassword = "#*ftp123de";
	final public static int ftpPort = 21;
	final public static String ftpDownloadPath = File.separator + "Download"
			+ File.separator + "Public";
	final public static String ftpUploadPath = File.separator + "temp";
	final public static String ftpLocalPath = internalSD + File.separator
			+ "temp";

	final public static int uploadFTPTask = 1;
	final public static int downloadFTPTask = 2;
	final public static int disconnectFTPTask = 3;

	final public static int waitState = 0;
	final public static int startedState = 1;
	final public static int transferredState = 2;
	final public static int failedState = 3;
	final public static int completedState = 4;
	final public static int abortedState = 5;

	final public static int minFTPSeconds = 30;
	final public static int maxFTPSeconds = 50;

	final public static int minLTEFTPKps = 60;
	final public static int unAcceptableFTPKps = 512;

	final public static int maxNoThroughputTimes = 10;
	final public static int maxLowThroughputTimes = 20;

	final public static int unAcceptableLossPingPacketsRate = 20;
	final public static int noPingResponseLossPacketsRate = 100;

	final public static int maxSingleFailLogNumber = 4;
	final public static int maxTotalSavedFailLog = 25;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int firstSlotId = 1;

	public static int secondSlotId = 2;

	public static String cmccDutNumber = "10010";

	public static String unicomDutNumber = "10010";

	public static String cncDutNumber = "10010";

	public static String defaultCNDutNumber = "10010";

	public static String sosDutNumber = "112";

	final public static int minCallDuration = 25;

	final public static int moTimeout = 10;

	final public static int AutoAnswerCall_Notification_ID = 1;

	public static enum State {

		testing, autoAnswer, autoReset, autoCheck

	}

	public static enum Setting {

		sim1Msisdn, sim2Msisdn, resultPath

	}

	public static enum Statistics {

		reset, totalPass, totalFail, continueFail, moPass, moCallDrop, moCallFail, moApCallFail, dlPass, dlInitialFail, dlNoThroughput, dlLowThroughput, dlTotalRx, dlTotalDuring, pingPass, pingNoResponse, pingUnAcceptablePackages, pingSendFail, pingTotalRx, pingTotalTx

	}

	public static enum SimStatus {
		BOTH, SIM1, SIM2, BLANK
	}

	public static enum Result {

		pass, unknown, callDrop, callFail, apNotInitialCall, notInitialFtpTransfer, noThroughput, lowThroughput, noPingResponse, unAcceptablePingPackagesLost, apNotSendPing
	}

	public static Result getResult(String des) {

		Result result = Result.unknown;

		if (des.equals("pass"))
			result = Result.pass;
		else if (des.equals("callDrop"))
			result = Result.callDrop;
		else if (des.equals("callFail"))
			result = Result.callFail;
		else if (des.equals("apNotInitialCall"))
			result = Result.apNotInitialCall;
		else if (des.equals("notInitialFtpTransfer"))
			result = Result.notInitialFtpTransfer;
		else if (des.equals("noThroughput"))
			result = Result.noThroughput;
		else if (des.equals("lowThroughput"))
			result = Result.lowThroughput;
		else if (des.equals("noPingResponse"))
			result = Result.noPingResponse;
		else if (des.equals("unAcceptablePingPackagesLost"))
			result = Result.unAcceptablePingPackagesLost;
		else if (des.equals("apNotSendPing"))
			result = Result.apNotSendPing;

		return result;

	}

	public static int getTargetSlotId(Context context) {

		int defaultSlotId = firstSlotId;

		SimStatus sims = getSimStatus(context);

		if (SimStatus.BOTH == sims) {

			int random = MathUtil.getRandomInRanget(1, 2);
			if (2 == random)
				defaultSlotId = secondSlotId;
		} else if (SimStatus.SIM2 == sims) {

			defaultSlotId = secondSlotId;

		}

		return defaultSlotId;

	}

	public static int getSIMs(String simStatus) {

		if (simStatus.equals(SimStatus.SIM1))
			return onlySIM1;
		else if (simStatus.equals(SimStatus.SIM2))
			return onlySIM2;
		else if (simStatus.equals(SimStatus.BOTH))
			return twoSIM;
		else
			return noSIM;

	}

	public static SimStatus getSimStatus(Context context) {

		SimStatus sims = SimStatus.BLANK;

		boolean hasFirstSIM = MTelephonyManager.hasSIM(context, firstSlotId);

		boolean hasSecondSIM = MTelephonyManager.hasSIM(context, secondSlotId);

		if (hasFirstSIM && hasSecondSIM)

			sims = SimStatus.BOTH;

		if (hasFirstSIM && !hasSecondSIM)
			sims = SimStatus.SIM1;

		if (!hasFirstSIM && hasSecondSIM)
			sims = SimStatus.SIM2;

		return sims;
	}

	public static String getDutNumber(Context context, int slotId) {

		String number = sosDutNumber;

		if (MTelephonyManager.hasSIM(context, slotId)) {

			String imsi = MTelephonyManager.getIMSI(context, slotId);

			if (isCMCC(imsi))
				number = cmccDutNumber;
			else if (isUnicom(imsi))
				number = unicomDutNumber;
			else if (isCNC(imsi))
				number = cncDutNumber;
			else
				number = defaultCNDutNumber;
		}

		return number;

	}

	static public boolean isCNC(String imsi) {

		if (imsi.startsWith("46003"))
			return true;
		else
			return false;

	}

	static public boolean isCMCC(String imsi) {

		if (imsi.startsWith("46000") || imsi.startsWith("46002")
				|| imsi.startsWith("46007") || imsi.startsWith("46008"))
			return true;
		else
			return false;

	}

	static public boolean isUnicom(String imsi) {

		if (imsi.startsWith("46001"))
			return true;
		else
			return false;

	}

	public static int getCallDuration(String dutNumber) {

		return MathUtil.getRandomInRanget(minCallDuration,
				getMaxCallDuration(dutNumber));

	}

	public static int getMaxCallDuration(String dutNumber) {

		int duration = 30;

		if (dutNumber.equals(cmccDutNumber))
			duration = 40;

		if (dutNumber.equals(unicomDutNumber))
			duration = 40;

		if (dutNumber.equals(sosDutNumber))
			duration = 30;

		if (dutNumber.equals(defaultCNDutNumber))
			duration = 1200;

		return duration;

	}

	public static int getRandTC() {

		int rand = MathUtil.getRandomInRanget(0, totalTCRate);

		LogUtil.d("rand=" + rand + " of " + totalTCRate);

		if (rand < moTCRate)

			return moTCIndex;

		else if (rand < moTCRate + downloadTCRate)

			return downloadTCIndex;
		else
			return pingTCIndex;
	}

	public static boolean exceedMaxContiousFailNumber(int continousFailNumber) {

		if (continousFailNumber >= maxContinousFailNumber)
			return true;
		else
			return false;

	}

	public static String getTCName(int index) {

		String caseName = "";

		switch (index) {
		case moTCIndex:

			caseName = moTCName;

			break;

		case downloadTCIndex:

			caseName = downloadTCName;

			break;

		case pingTCIndex:

			caseName = pingTCName;

			break;

		default:
			caseName = unknowTCName;
		}

		return caseName;
	}

	public static String getResultDescription(int result) {

		switch (result) {

		case passResult:
			return "pass";
		case failResult:
			return "fail";
		case callDropResult:
			return "callDrop";
		case callFailResult:
			return "callFail";
		case apCallFailResult:
			return "apNotInitialCall";
		case initialFTPFailResult:
			return "failConnectFTP";
		case noDLFailResult:
			return "noDLTransfer";
		case lowDLFailResult:
			return "lowDLThroughput";
		case noPingResponseResult:
			return "noPingResponse";
		case unAcceptableLostPingPackagesResult:
			return "unAcceptableLostPingPackages";
		case apSendPingFailResult:
			return "apSendPingFail";
		default:
			return "unkwnown";

		}

	}

	public static String getSIMString(int slotId) {

		return String.valueOf(slotId);

	}

	public static String getLogName(int tcIndex, int slotId, int result,
			int total) {

		String name = getTCName(tcIndex) + "_" + getResultDescription(result)
				+ "_" + total;

		switch (tcIndex) {

		case moTCIndex:

			name = name + "_SIM" + getSIMString(slotId);

			break;

		default:
			name = name + "_Main_SIM";
		}

		return name;

	}

	public static boolean isPassResult(int result) {

		if (passResult == result)
			return true;
		else
			return false;
	}

	public static String getSIMStatus(int sim) {

		String simStauts = "";

		switch (sim) {
		case noSIM:
			simStauts = "no any SIM";
			break;
		case onlySIM1:
			simStauts = "only sim1";
			break;
		case onlySIM2:
			simStauts = "only sim2";
			break;
		case twoSIM:
			simStauts = "two sims";
			break;
		default:
			simStauts = "unknow sims";
		}

		return simStauts;

	}

	public static int getPingPacketSize() {

		int size = 64;

		int rand = MathUtil.getRandomInRanget(1, 5);
		switch (rand) {
		case 1:
			size = 8;
			break;
		case 2:
			size = 64;
			break;
		case 3:
			size = 512;
			break;
		case 4:
			size = 1024;
			break;
		case 5:
			size = 1472;
			break;

		}

		return size;

	}

	public static String getRandHost() {

		String host = "www.soufun.com";

		int rand = MathUtil.getRandomInRanget(1, 10);
		switch (rand) {
		case 1:
			host = "www.soufun.com";
			break;
		case 2:
			host = "www.weibo.com";
			break;
		case 3:
			host = "www.cctv.com";
			break;
		case 4:
			host = "www.qq.com";
			break;
		case 5:
			host = "www.people.com.cn";
			break;
		case 6:
			host = "www.51.com";
			break;
		case 7:
			host = "www.163.com";
			break;
		case 8:
			host = "www.autohome.com.cn";
			break;
		case 9:
			host = "www.ifeng.com";
			break;
		case 10:
			host = "www.mop.com";
			break;

		}

		return host;

	}

	public static int checkPingResult(String loss) {

		if (Integer.valueOf(loss) == noPingResponseLossPacketsRate)
			return noPingResponseResult;
		else if (Integer.valueOf(loss) >= unAcceptableLossPingPacketsRate)
			return unAcceptableLostPingPackagesResult;
		else
			return passResult;

	}

	public static int getFTPDuring() {

		return MathUtil.getRandomInRanget(minFTPSeconds, maxFTPSeconds);

	}

	public static boolean isNoThroughput(int throughput) {

		if (throughput < minLTEFTPKps)
			return true;
		else
			return false;
	}

	public static boolean isLowThroughput(int throughput) {

		return MathUtil.rangeInDefined(throughput, 0, unAcceptableFTPKps);

	}

}
