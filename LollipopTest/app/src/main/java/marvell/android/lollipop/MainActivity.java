package marvell.android.lollipop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import marvell.android.testcase.DownloadTC;
import marvell.android.testcase.MO;
import marvell.android.testcase.PingTC;
import marvell.android.testsuit.TConstant;
import marvell.android.util.Configure;
import marvell.android.util.DebugUtil;
import marvell.android.util.ExportResult;
import marvell.android.util.LogUtil;
import marvell.android.util.MLog;
import marvell.android.util.MNotificationManager;
import marvell.android.util.MTelephonyManager;
import marvell.android.util.MathUtil;
import marvell.android.util.ShareProferencesUtil;
import marvell.android.util.TimeUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	// Button
	Button startTestButton;
	Button configureButton;

	// TextView
	TextView currentTestingTextView;
	TextView configurationTextView;

	TextView mo_pass_TextView;
	TextView mo_fail_TextView;
	TextView mo_detail_TextView;

	TextView dl_pass_TextView;
	TextView dl_fail_TextView;
	TextView dl_detail_TextView;

	TextView ping_pass_TextView;
	TextView ping_fail_TextView;
	TextView ping_detail_TextView;

	Context context;

	String resultXmlFileName;
	String resultRootPath;
	String lastAcatPath;

	Boolean isTesting = false;
	int sims = TConstant.noSIM;

	int slotId;

	// reset
	int reset;

	// Total
	int totalPass;
	int totalFail;
	int continueFail;

	// MO Case
	int moPass;
	int moCallDrop;
	int moCallFail;
	int moApCallFail;

	// Download Case;
	int dlPass;
	int dlInitalFail;
	int dlNoTransfer;
	int dlLowThroughput;
	long dlTotalRxBytes;
	int dlTotalDuring;

	// Ping Case
	int pingPass;
	int noPingResponse;
	int unacceptableLostPingPackages;
	int apFailSendPing;

	int totalTransmittedPingPakcages;
	int totalReceivedPingPackages;

	// SIM1
	boolean hasSim1 = false;
	String sim1Number = "";
	boolean sim1IsCmcc = false;

	// SIM2
	boolean hasSim2 = false;
	String sim2Number = "";
	boolean sim2IsCmcc = false;

	boolean enableAutoAnswer = false;
	boolean enableAutoReset = false;

	// testCase
	Date startDate;
	Date endDate;
	int interval;

	boolean hasACATLog = false;
	String sdlFolder = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		
		LogUtil.d("Now initial the MainActivity");
		
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_main);

		context = this.getApplicationContext();

		Map<String, String> map = Configure.getConfiguration(context);

		enableAutoAnswer = Boolean.parseBoolean(map.get("autoAnswer"));

		LogUtil.d("autoReset" + map.get("autoReset"));

		enableAutoReset = Boolean.parseBoolean(map.get("autoReset"));

		String sim1 = map.get("sim1Number");
		if ("".equals(sim1))
			sim1Number = "unknown";
		else
			sim1Number = sim1;

		String sim2 = map.get("sim2Number");
		if ("".equals(sim2))
			sim2Number = "unknown";
		else
			sim2Number = sim2;

		startTestButton = (Button) findViewById(R.id.startTestButton);

		startTestButton.setOnClickListener(this);

		configureButton = (Button) findViewById(R.id.configureButton);

		configureButton.setOnClickListener(this);

		configurationTextView = (TextView) findViewById(R.id.configurationTextView);

		if (enableAutoAnswer) {

			MNotificationManager.showAutoAnswerCallNotification(context);
			configurationTextView.setText("SIM1=" + sim1Number + "/ SIM2="
					+ sim2Number + "\r\nEnable autoAnswer");

		} else {

			MNotificationManager.cancleAutoAnswerCallNotification(context);
			configurationTextView.setText("SIM1=" + sim1Number + "/ SIM2="
					+ sim2Number);
		}

		currentTestingTextView = (TextView) findViewById(R.id.currentTestingTextView);

		mo_pass_TextView = (TextView) findViewById(R.id.first_TC_Pass_TextView);

		mo_fail_TextView = (TextView) findViewById(R.id.first_TC_Fail_TextView);

		mo_detail_TextView = (TextView) findViewById(R.id.first_TC_Detail_TextView);

		dl_pass_TextView = (TextView) findViewById(R.id.second_TC_Pass_TextView);

		dl_fail_TextView = (TextView) findViewById(R.id.second_TC_Fail_TextView);

		dl_detail_TextView = (TextView) findViewById(R.id.second_TC_Detail_TextView);

		ping_pass_TextView = (TextView) findViewById(R.id.third_TC_Pass_TextView);

		ping_fail_TextView = (TextView) findViewById(R.id.third_TC_Fail_TextView);

		ping_detail_TextView = (TextView) findViewById(R.id.third_TC_Detail_TextView);

		isTesting = Configure.getTestingState(context);

		if (isTesting) {

			continueTestData();

			currentTestingTextView
					.setText("Continue test after two minutes. Reset from assert "
							+ reset);

			configureButton.setVisibility(View.GONE);

			startTestButton.setText(context
					.getString(R.string.stopTestButtonName));

			for (int caseId = TConstant.firstTCIndex; caseId <= TConstant.lastTCIndex; caseId++) {

				updateResultView(caseId);
			}

			TestThread testThread = new TestThread();

			testThread.start();

		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			ShareProferencesUtil.setFinishCount(0);
			Log.d("fanghui", "clear count success!");
			return true;
		} else if (id == R.id.action_about){
			Intent intent = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	public void onClick(View v) {

		LogUtil.d("onClick");

		switch (v.getId()) {
		case R.id.startTestButton:

			LogUtil.d("onClick startTestButton");

			if (isTesting) {

				LogUtil.d("stop TestThread");

				Configure.disableTesting(context);

				isTesting = false;

				saveFinishStatus(TConstant.normal_Exist);

				sendFinishTestMsg(TConstant.normal_Exist);

				configureButton.setVisibility(View.GONE);

				startTestButton.setText(context
						.getString(R.string.startTestButtonName));

			} else {

				LogUtil.d("start TestThread");

//				lastAcatPath = MLog.getLastAcatFolder();
//
//				if ("".equals(lastAcatPath)) {
//
//					new AlertDialog.Builder(this).setTitle("Prompt")
//							.setMessage("你没有打开 DIAG2SD 功能")
//							.setPositiveButton("退出測試", null).show();
//
//				} else
				{

					isTesting = true;

					LogUtil.d("Offline ACAT log path is " + lastAcatPath);

					Configure.enableTesting(context);

					createResultXmlFile();

					initialTestData();

					configureButton.setVisibility(View.GONE);

					startTestButton.setText(context
							.getString(R.string.stopTestButtonName));

					startTestButton.setText(getString(R.string.stopTestButtonName));

					TestThread testThread = new TestThread();

					testThread.start();
				}
			}

			break;

		case R.id.configureButton:

			// LogUtil.d("onClick configureButton");

//			DebugThread debugThread = new DebugThread();
//
//			debugThread.start();

			 nextActivity(TConstant.configureActivity);

			// List<GsmCell> cells =
			// MTelephonyManager.getNeighbourCellInfo(context);
			// for(GsmCell cell: cells){
			// cell.printAll();
			// }

			break;
		}

	}

	class DebugThread extends Thread {

		@Override
		public void run() {

			LogUtil.d("Now start the debug test");

			//mo(TConstant.firstSlotId);
			
			// MTelephonyManager.getIMEI(context, 1);
			//
			// MTelephonyManager.getIMEI(context, 2);
			//
			// MTelephonyManager.getIMSI(context, 1);
			//
			// MTelephonyManager.getIMSI(context, 2);

			// DebugUtil.checkGetIMEI(context);
			// DebugUtil.checkGetIMSI(context);
			// DebugUtil.checkHasSIM(context);
			// DebugUtil.checkGetCallState(context);
			// DebugUtil.mo(context);
			// DebugUtil.endCall(context);
			// DebugUtil.printCallColumnNames(context);
			// DebugUtil.checkGetMsisdn(context);

//			DebugUtil.initialData(context);
		}
	}

	public void continueTestData() {

		getLastResultData();

		lastAcatPath = MLog.getLastAcatFolder();

		ExportResult.AddFinishReset2Xml(resultXmlFileName);

	}

	public void getLastResultData() {

		resultRootPath = Configure.getResultPath(context);
		
		resultXmlFileName = resultRootPath + TConstant.resultXmlFileName;

		Map<String, String> lastResult = ExportResult
				.getLastResult(resultXmlFileName);
		if (lastResult.size() > 0) {

			startDate = TimeUtil.paraseDateString(lastResult.get("startDate"));
			endDate = TimeUtil.paraseDateString(lastResult.get("endDate"));

			// reset
			reset = Integer.valueOf(lastResult.get("reset"));

			// total
			totalPass = Integer.valueOf(lastResult.get("pass"));
			totalFail = Integer.valueOf(lastResult.get("fail"));
			continueFail = 0;

			// MO Case
			moPass = Integer.valueOf(lastResult.get("moPass"));
			moCallDrop = Integer.valueOf(lastResult.get("callDrop_mo"));
			moCallFail = Integer.valueOf(lastResult.get("apCallFail_mo"));
			moApCallFail = Integer.valueOf(lastResult.get("apCallFail_mo"));

			// Download Case
			dlPass = Integer.valueOf(lastResult.get("dlPass"));
			dlInitalFail = Integer.valueOf(lastResult.get("dlInitalFail"));
			dlNoTransfer = Integer.valueOf(lastResult.get("dlNoTransfer"));
			dlLowThroughput = Integer
					.valueOf(lastResult.get("dlLowThroughput"));
			dlTotalRxBytes = Integer.valueOf(lastResult.get("dlTotalRxBytes"));
			dlTotalDuring = Integer.valueOf(lastResult.get("dlTotalDuring"));

			// Ping Case
			pingPass = Integer.valueOf(lastResult.get("pingPass"));
			noPingResponse = Integer.valueOf(lastResult.get("noResponse"));
			unacceptableLostPingPackages = Integer.valueOf(lastResult
					.get("unacceptableLostPackages"));
			apFailSendPing = Integer.valueOf(lastResult.get("apFailSendPing"));

			totalTransmittedPingPakcages = Integer.valueOf(lastResult
					.get("transmitted"));
			totalReceivedPingPackages = Integer.valueOf(lastResult
					.get("received"));

			sims = TConstant.getSIMs(lastResult.get("sims"));

			switch (sims)

			{
			case TConstant.onlySIM1:
				hasSim1 = true;
				sim1Number = lastResult.get("sim1Number");
				sim1IsCmcc = Boolean
						.parseBoolean(lastResult.get("sim1_isCmcc"));
				break;

			case TConstant.onlySIM2:
				hasSim2 = true;
				sim2Number = lastResult.get("sim2Number");
				sim2IsCmcc = Boolean
						.parseBoolean(lastResult.get("sim2_isCmcc"));

				break;

			case TConstant.twoSIM:

				hasSim1 = true;
				sim1Number = lastResult.get("sim1Number");
				sim1IsCmcc = Boolean.parseBoolean(lastResult.get("isCmccSIM"));

				hasSim2 = true;
				sim2Number = lastResult.get("sim2Number");
				sim2IsCmcc = Boolean
						.parseBoolean(lastResult.get("sim2_isCmcc"));

				break;

			}

		}
	}

	public void updateResultView(int caseId) {

		switch (caseId) {

		case TConstant.moTCIndex:

			mo_pass_TextView.setText(String.valueOf(moPass));
			mo_fail_TextView.setText(String.valueOf(moCallDrop + moCallFail
					+ moApCallFail));
			mo_detail_TextView.setText(String.valueOf("Drop=" + moCallDrop
					+ ":Call Fail=" + (moCallFail + moApCallFail)));

			break;

		case TConstant.downloadTCIndex:

			dl_pass_TextView.setText(String.valueOf(dlPass));

			// int totalDlFail = dlInitalFail + dlNoTransfer + dlLowThroughput;
			//
			// LogUtil.d("totalDlFail=" + totalDlFail + "dlInitalFail="
			// + dlInitalFail + "/dlNoTransfer=" + dlNoTransfer
			// + "/dlLowThroughput=" + dlLowThroughput);

			dl_fail_TextView.setText(String.valueOf(dlInitalFail + dlNoTransfer
					+ dlLowThroughput));

			if (dlTotalDuring > 0) {

				float dl = dlTotalRxBytes / dlTotalDuring / 1000 * 8;

				dl_detail_TextView.setText(String.valueOf("averageDL=" + (dl)
						+ "kpbs"));
			}

		case TConstant.pingTCIndex:

			ping_pass_TextView.setText(String.valueOf(pingPass));

			ping_fail_TextView.setText(String.valueOf(noPingResponse
					+ unacceptableLostPingPackages + apFailSendPing));

			ping_detail_TextView.setText("T vs R = "
					+ totalTransmittedPingPakcages + " vs "
					+ totalReceivedPingPackages);

			// if (totalTransmittedPingPakcages > 0) {
			//
			// float loss = (totalTransmittedPingPakcages -
			// totalReceivedPingPackages)
			// / totalTransmittedPingPakcages * 100;
			//
			// ping_detail_TextView.setText("T/R="
			// + totalTransmittedPingPakcages + "/"
			// + totalReceivedPingPackages
			// + String.valueOf(",loss=" + loss + "%"));
			// }

			break;

		}

	}

	class TestThread extends Thread {

		@Override
		public void run() {

			if (reset > 0)
				TimeUtil.sleep(2, TimeUtil.m);
			try{
				File file = new File(Environment.getExternalStorageDirectory().getPath()+"/PhoneNumber/num.txt");
				FileInputStream in = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = reader.readLine();
				int count = 0;
				while (isTesting && line!= null) {
					Log.d("fanghui", line);
					//取下一行数据
					count ++;
					//count 表示读到的行数，从0 行开始；
					// ShareProferencesUtil.getFinishCount()表示上次完成的行数，也是从0开始；
					//本次需要从上次完成的行数的下一行开始进行呼叫
					Log.d("fanghui", "count "+count+" finished "+ShareProferencesUtil.getFinishCount());
					if (count <= ShareProferencesUtil.getFinishCount()){
						//上次已经测试过，不再测试
						Log.d("fanghui",line + " has been dialed last time");
						line = reader.readLine();
						continue;
					}

					//只进行MO业务
					int caseId = TConstant.moTCIndex;

					LogUtil.d("getRandTC=" + caseId);

					slotId = TConstant.getTargetSlotId(context);

					LogUtil.d("isTestable  = " + caseId);

					startDate = new Date();

					Map<String, String> resultMap = new HashMap<String, String>();

					sendTestingCaseMsg(caseId);
					//进行第一次呼叫
					resultMap = mo(slotId, line);

					int result = Integer.valueOf(resultMap.get("result"));

//					if (result == TConstant.passResult){
//						//如果第一次呼叫成功，则呼叫第二次
//						TimeUtil.sleep(10, TimeUtil.s);
//						resultMap = mo(slotId, line);
//						int result_2 = Integer.valueOf(resultMap.get("result"));
//						updateTestResult(slotId, caseId, result_2, resultMap);
//						sleepRandSeconds(slotId, caseId, result_2);
//						ShareProferencesUtil.setFinishCount(count);
//					}else{
//						//如果第一次呼叫，再不进行第二次呼叫
//						updateTestResult(slotId, caseId, result, resultMap);
//						sleepRandSeconds(slotId, caseId, result);
//						ShareProferencesUtil.setFinishCount(count);
//					}
						//不管成功与否，都呼叫第二次
						//TimeUtil.sleep(5, TimeUtil.s);
						//resultMap = mo(slotId, line);
						//int result_2 = Integer.valueOf(resultMap.get("result"));
						updateTestResult(slotId, caseId, result, resultMap);
						sleepRandSeconds(slotId, caseId, result);
						ShareProferencesUtil.setFinishCount(count);

					line = reader.readLine();

				}

			} catch(IOException ioe){
				ioe.printStackTrace();
			}
			LogUtil.d("stop TestThread");

		}
	}

	public boolean isTestable(int caseId) {

		boolean testable = true;

		if (hasAssert(context)) {

			testable = false;

			chekContinousTest();

		} else {

			switch (caseId) {

			case TConstant.moTCIndex:

				break;

			}

		}

		return testable;

	}

	public static boolean hasAssert(Context context) {

		boolean result = Configure.getAssertState(context);

		if (result) {

			LogUtil.d("有ASSERT");

			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(NOTIFICATION_SERVICE);

			Notification notification = new Notification.Builder(context)
					.setContentTitle("自动化测试").setContentText("无法测试啦，有ASSERT了")
					.build();

			notificationManager.notify(222, notification);

		} else

			LogUtil.d("没发现ASSERT");

		return result;
	}

	public void chekContinousTest() {
		if (isTesting) {

			if (TConstant.exceedMaxContiousFailNumber(continueFail)
					|| hasAssert(context)) {

				isTesting = false;

				String stopReason = "";
				if (hasAssert(context))
					stopReason = TConstant.foundAssert_Exsit + (reset + 1)
							+ " times";
				else
					stopReason = TConstant.exceedMaxFailLimit_Exist;

				if (hasAssert(context) && enableAutoReset
						&& reset < TConstant.maxResetNumber) {

					Configure.enableReset(context);

					ExportResult.AadStartReset2Xml(resultXmlFileName);

					sendWait4ResetMsg();

				} else {

					Configure.disableTesting(context);

					sendFinishTestMsg(stopReason);

					TimeUtil.sleep(1, TimeUtil.m);

					saveFinishStatus(stopReason);

				}

			}
		}
	}

	public void sendWait4ResetMsg() {

		Message message = new Message();
		message.what = TConstant.wait4ResetMsg;
		mHandler.sendMessage(message);

	}

	public void sendFinishTestMsg(String reason) {

		Bundle bundle = new Bundle();
		bundle.putString("reason", reason);

		Message message = new Message();
		message.what = TConstant.finishTestMsg;
		message.setData(bundle);

		mHandler.sendMessage(message);

	}

	public void saveFinishStatus(String reason) {

		ExportResult.addFinishTestReason2Xml(resultXmlFileName, reason);

	}

	public void sendTestingCaseMsg(int caseId) {

		Bundle bundle = new Bundle();
		bundle.putInt("caseId", caseId);

		Message message = new Message();
		message.what = TConstant.testingCaseMsg;
		message.setData(bundle);

		mHandler.sendMessage(message);

	}

	public void sendSleepSecondMsg(int seconds, int slotId, int lastCaseId,
			int lastCaseResult) {

		String text = "SIM" + TConstant.getSIMString(slotId) + " "
				+ TConstant.getTCName(lastCaseId) + " "
				+ TConstant.getResultDescription(lastCaseResult)
				+ ", now rand sleep for " + seconds + "s";

		Bundle bundle = new Bundle();
		bundle.putString("text", text);
		bundle.putInt("lastCaseId", lastCaseId);

		Message message = new Message();
		message.what = TConstant.sleepSecondsMsg;
		message.setData(bundle);

		mHandler.sendMessage(message);

	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			switch (msg.what) {

			case TConstant.testingCaseMsg:// TestHandler是Activity的类名

				currentTestingTextView.setText("Now is testing the case "
						+ TConstant.getTCName(bundle.getInt("caseId")));
				break;

			case TConstant.sleepSecondsMsg:

				updateResultView(bundle.getInt("lastCaseId"));

				if (isTesting)

					currentTestingTextView.setText(

					bundle.getString("text"));

				break;

			case TConstant.wait4ResetMsg:

				currentTestingTextView
						.setText("Assert is triggered, wait for reset");

				break;

			case TConstant.finishTestMsg:

				currentTestingTextView.setText("Finish the test for "
						+ bundle.getString("reason"));

				configureButton.setVisibility(View.GONE);

				startTestButton.setText(context
						.getString(R.string.startTestButtonName));

				break;
			}

		}
	};

	public void updateTestResult(int slotId, int lastCaseId, int lastResult,
			Map<String, String> resultMap) {

		LogUtil.d("updateTestResult lastCaseId=" + lastCaseId);


		switch (lastCaseId) {
		case TConstant.moTCIndex:

			switch (lastResult) {
			case TConstant.passResult:
				moPass++;
				break;
			case TConstant.callFailResult:
				moCallFail++;
				break;
			case TConstant.callDropResult:
				moCallDrop++;
				break;
			case TConstant.apCallFailResult:
				moApCallFail++;
				break;

			}

			break;

		case TConstant.downloadTCIndex:
			switch (lastResult) {
			case TConstant.passResult:

				dlTotalRxBytes = dlTotalRxBytes
						+ Long.valueOf(resultMap.get("totalRxBytes"));
				dlTotalDuring = dlTotalDuring
						+ Integer.valueOf(resultMap.get("during"));
				dlPass++;
				break;
			case TConstant.initialFTPFailResult:
				dlInitalFail++;
				break;
			case TConstant.lowDLFailResult:
				dlLowThroughput++;
				break;
			case TConstant.noDLFailResult:
				dlNoTransfer++;
				break;

			}

			break;
		case TConstant.pingTCIndex:
			switch (lastResult) {
			case TConstant.passResult:

				totalTransmittedPingPakcages = totalTransmittedPingPakcages
						+ Integer.valueOf(resultMap.get("transmitted"));
				totalReceivedPingPackages = totalReceivedPingPackages
						+ Integer.valueOf(resultMap.get("received"));
				pingPass++;

				break;

			case TConstant.apSendPingFailResult:

				apFailSendPing++;
				break;

			case TConstant.noPingResponseResult:

				noPingResponse++;

				break;
			case TConstant.unAcceptableLostPingPackagesResult:

				unacceptableLostPingPackages++;

				totalTransmittedPingPakcages = totalTransmittedPingPakcages
						+ Integer.valueOf(resultMap.get("transmitted"));
				totalReceivedPingPackages = totalReceivedPingPackages
						+ Integer.valueOf(resultMap.get("received"));

				break;

			}
		}

		if (TConstant.passResult == lastResult) {

			totalPass++;

			continueFail = 0;

		} else {

			totalFail++;

			continueFail++;

		}

		saveResult2File(slotId, lastCaseId, lastResult, resultMap);

		//chekContinousTest();
	}

	public String saveFailLog(int slotId, int caseId, int result, int total) {

		String logName = "no log";

		LogUtil.d("saveFailLog");

		if (TConstant.passResult != result) {

			boolean saved = false;
			switch (result) {

			case TConstant.callDropResult:

				if (moCallDrop <= TConstant.maxSingleFailLogNumber)
					saved = true;

				break;
			case TConstant.callFailResult:
				if (moCallFail <= TConstant.maxSingleFailLogNumber)
					saved = true;
				break;
			case TConstant.apCallFailResult:
				if (moApCallFail <= TConstant.maxSingleFailLogNumber)
					saved = true;
				break;
			case TConstant.initialFTPFailResult:
				if (dlInitalFail <= TConstant.maxSingleFailLogNumber)
					saved = true;
				break;
			case TConstant.noDLFailResult:
				if (dlNoTransfer <= TConstant.maxSingleFailLogNumber)
					saved = true;
				break;

			case TConstant.lowDLFailResult:
				if (dlLowThroughput <= TConstant.maxSingleFailLogNumber)
					saved = true;
				break;
			case TConstant.noPingResponseResult:
				if (noPingResponse <= TConstant.maxSingleFailLogNumber)
					saved = true;
				break;
			case TConstant.unAcceptableLostPingPackagesResult:
				if (unacceptableLostPingPackages <= TConstant.maxSingleFailLogNumber)
					saved = true;
				break;

			case TConstant.apSendPingFailResult:
				if (apFailSendPing <= TConstant.maxSingleFailLogNumber)
					saved = true;
				break;

			}

			if (saved) {

				logName = TConstant.getLogName(caseId, slotId, result, total);

				String desLogPath = resultRootPath + logName + "_reset" + reset;

				LogUtil.d("desLogPath is " + desLogPath);

				SaveLogThread logThread = new SaveLogThread(desLogPath);

				logThread.start();
			}
		}

		return logName;
	}

	public void saveResult2File(int slotId, int lastCaseId, int lastResult,
			Map<String, String> resultMap) {

		LogUtil.d("resultXmlFileName=" + resultXmlFileName);

		ExportResult.addTestResult2Xml(resultXmlFileName, lastCaseId,
				lastResult, startDate, endDate, slotId, resultMap, interval);

	}

	class SaveLogThread extends Thread {

		private String desPath;

		public SaveLogThread(String desPath) {
			this.desPath = desPath;
		}

		@Override
		public void run() {
			MLog.saveAPLog(resultRootPath, reset);
			MLog.saveAcatLog(lastAcatPath, desPath);

		}
	}

	public Map<String, String> mo(int slotId, String destNum) {

		MO mo = new MO(context, slotId, destNum);

		mo.startTest();

		endDate = new Date();

		return mo.getResult();

	}

	public Map<String, String> download(int slotId) {

		 DownloadTC download = new DownloadTC(context, slotId);
		
		 download.startTest();
		
		 endDate = new Date();
		
		 return download.getResult();


	}

	
	public void sleepRandSeconds(int slotId, int caseId, int result) {

		interval = MathUtil.getRandomInRanget(TConstant.minIntervalSeconds,
				TConstant.maxIntervalSeconds);
		

		sendSleepSecondMsg(interval, slotId, caseId, result);

		LogUtil.d("rand sleep for " + interval + " seconds");

		int now = 1;

		while ((now++ < interval) && isTesting) {

			TimeUtil.sleep(1, TimeUtil.s);
		}
	}

	private void createResultXmlFile() {

		resultXmlFileName = ExportResult.createResultXml();

		resultRootPath = resultXmlFileName.substring(0,
				resultXmlFileName.lastIndexOf("/") + 1);

		Configure.saveResultPath(context, resultRootPath);

		LogUtil.d("resultXmlFileName=" + resultXmlFileName);
		LogUtil.d("resultXmlPath=" + resultRootPath);

	}

	public void initialTestData() {

		intialResultData();

		initialSimsData();
	}

	private void intialResultData() {

		startDate = new Date();
		endDate = new Date();

		// reset
		reset = 0;

		// total
		totalPass = 0;
		totalFail = 0;
		continueFail = 0;

		// MO Case
		moPass = 0;
		moCallDrop = 0;
		moCallFail = 0;
		moApCallFail = 0;
		// DL Case
		dlPass = 0;
		dlInitalFail = 0;
		dlNoTransfer = 0;
		dlLowThroughput = 0;
		dlTotalRxBytes = 0;
		dlTotalDuring = 0;

		// Ping Case
		pingPass = 0;
		noPingResponse = 0;
		unacceptableLostPingPackages = 0;
		apFailSendPing = 0;
		totalTransmittedPingPakcages = 0;
		totalReceivedPingPackages = 0;

	}

	public void initialSimsData() {

		sims = MTelephonyManager.getSims(context);

		switch (sims) {
		case TConstant.onlySIM1:
			hasSim1 = true;
			sim1IsCmcc = MTelephonyManager.isCMCC(context,
					TConstant.firstSlotId);
			break;
		case TConstant.onlySIM2:
			hasSim2 = true;
			sim2IsCmcc = MTelephonyManager.isCMCC(context,
					TConstant.secondSlotId);
			break;
		case TConstant.twoSIM:
			hasSim1 = true;
			hasSim2 = true;
			sim1IsCmcc = MTelephonyManager.isCMCC(context,
					TConstant.firstSlotId);
			sim2IsCmcc = MTelephonyManager.isCMCC(context,
					TConstant.secondSlotId);
			break;

		}

		HashMap<String, String> simInformation = new HashMap<String, String>();
		simInformation.put("sim1IsCmcc", String.valueOf(sim1IsCmcc));
		simInformation.put("sim1Number", sim1Number);
		simInformation.put("sim2IsCmcc", String.valueOf(sim2IsCmcc));
		simInformation.put("sim2Number", sim2Number);
		simInformation.put("hasSim1", String.valueOf(hasSim1));
		simInformation.put("hasSim2", String.valueOf(hasSim2));

		LogUtil.d("resultXmlFileName=" + resultXmlFileName);

		ExportResult
				.addEnv2Xml(resultXmlFileName, sims, simInformation);

	}

	private void nextActivity(int nextActivityNumber) {

		Intent intent = new Intent();

		switch (nextActivityNumber) {

		case TConstant.configureActivity:

			intent.putExtra("sim1Number", sim1Number);
			intent.putExtra("sim2Number", sim2Number);
			intent.putExtra("enableAutoAnswer", enableAutoAnswer);
			intent.putExtra("enableAutoReset", enableAutoReset);
			intent.setClass(this, ConfigureActivity.class);

			break;

		}

		startActivity(intent);

	}
	
	public Map<String, String> ping(int slotId) {

		PingTC ping = new PingTC(context, slotId);

		ping.startTest();

		endDate = new Date();

		return ping.getResult();

	}
}
