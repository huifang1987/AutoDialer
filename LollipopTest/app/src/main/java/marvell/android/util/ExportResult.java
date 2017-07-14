package marvell.android.util;

import android.annotation.SuppressLint;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import marvell.android.testsuit.TConstant;

import org.dom4j.Document;
import org.dom4j.Element;

@SuppressLint("SimpleDateFormat")
public class ExportResult {

	static public String createResultXml() {

		LogUtil.d("createResultXml");

		FileUtil.mkDir(TConstant.resultLogPath);

		String xmlFilePath = TConstant.resultLogPath + "/"
				+ TimeUtil.getDateString4Win(new Date());

		FileUtil.mkDir(xmlFilePath);

		String xmlFileName = xmlFilePath + "/" + TConstant.resultXmlFileName;

		// String fileName = TConstant.logPath + "/result_"
		// + TimeUtil.getDateString(new Date()) + ".xml";

		LogUtil.d("resultXmlFileName=" + xmlFileName);

		if (XmlUtil.createXml(xmlFileName)) {

			addBaseDate(xmlFileName);

		}

		return xmlFileName;

	}

	static private boolean addBaseDate(String fileName) {

		Document document = XmlUtil.getXmlDocument(fileName);

		Element rootElement = document.getRootElement();

		Element resultElement = rootElement.addElement("result");
		{

			resultElement.addAttribute("startDate",
					TimeUtil.getDateString(new Date()));
			resultElement.addAttribute("total", "0");
			resultElement.addAttribute("pass", "0");
			resultElement.addAttribute("fail", "0");
			resultElement.addAttribute("endDate",
					TimeUtil.getDateString(new Date()));
		}

		Element simElement = resultElement.addElement("sim");
		simElement.addAttribute("isDSDSAP", "false");
		simElement.addAttribute("sims", "0");

		Element resetListElement = resultElement.addElement("resetList");
		resetListElement.addAttribute("total", "0");
		resetListElement.addAttribute("startDate",
				TimeUtil.getDateString(new Date()));

		Element moElement = resultElement.addElement("mo");
		{
			moElement.addAttribute("total", "0");
			moElement.addAttribute("pass", "0");
			moElement.addAttribute("fail", "0");

			Element callFailElement = moElement.addElement("callFail");
			{
				callFailElement.addAttribute("total", "0");
			}
			Element callDropElement = moElement.addElement("callDrop");
			{
				callDropElement.addAttribute("total", "0");
			}
			Element apCallFailElement = moElement.addElement("apCallFail");
			{
				apCallFailElement.addAttribute("total", "0");
			}
		}

//		Element downloadElement = resultElement.addElement("download");
//		{
//			downloadElement.addAttribute("total", "0");
//			downloadElement.addAttribute("pass", "0");
//			downloadElement.addAttribute("fail", "0");
//			downloadElement.addAttribute("averageThroughput", "0kbps");
//			downloadElement.addAttribute("totalRxBytes", "0");
//			downloadElement.addAttribute("during", "0");
//
//			Element initialFTPFailElement = downloadElement
//					.addElement("initialFTPFail");
//			{
//				initialFTPFailElement.addAttribute("total", "0");
//			}
//			Element noDataTransferElement = downloadElement
//					.addElement("noDataTransfer");
//			{
//				noDataTransferElement.addAttribute("total", "0");
//			}
//			Element lowThroughputElement = downloadElement
//					.addElement("lowThroughput");
//			{
//				lowThroughputElement.addAttribute("total", "0");
//			}
//		}

//		Element pingElement = resultElement.addElement("ping");
//		{
//			pingElement.addAttribute("total", "0");
//			pingElement.addAttribute("pass", "0");
//			pingElement.addAttribute("fail", "0");
//			pingElement.addAttribute("transmitted", "0");
//			pingElement.addAttribute("received", "0");
//			pingElement.addAttribute("loss", "0");
//			// noResponse
//			Element noPingResponseElement = pingElement
//					.addElement("noResponse");
//			{
//				noPingResponseElement.addAttribute("total", "0");
//			}
//
//			// unacceptableLostPackages
//			Element unacceptableLostPingPackagesElement = pingElement
//					.addElement("unacceptableLostPackages");
//			{
//				unacceptableLostPingPackagesElement.addAttribute("total", "0");
//			}
//
//			Element apFailSendPingElement = pingElement
//					.addElement("apFailSendPing");
//			{
//				apFailSendPingElement.addAttribute("total", "0");
//			}
//
//		}

		resultElement.addElement("detail");

		return XmlUtil.saveXmlDocument(fileName, document);

	}

	static public boolean addEnv2Xml(String fileName, int sims,
			HashMap<String, String> simInformation) {

		Document document = XmlUtil.getXmlDocument(fileName);

		Element rootElement = document.getRootElement();

		Element resultElement = rootElement.element("result");
		if (null != resultElement) {

			Element simElement = resultElement.element("sim");
			if (null != simElement) {

				simElement.addAttribute("sims", TConstant.getSIMStatus(sims));

				switch (sims)

				{
				case TConstant.onlySIM1:
					Element sim1Element = simElement.addElement("sim1");
					sim1Element.addAttribute("isCmccSIM",
							simInformation.get("sim1IsCmcc"));
					sim1Element.addAttribute("sim1Number",
							simInformation.get("sim1Number"));

					break;
				case TConstant.onlySIM2:
					Element sim2Element = simElement.addElement("sim2");
					sim2Element.addAttribute("isCmccSIM",
							simInformation.get("sim2IsCmcc"));
					sim2Element.addAttribute("sim2Number",
							simInformation.get("sim2Number"));
					break;

				case TConstant.twoSIM:

					Element firstSimElement = simElement.addElement("sim1");
					firstSimElement.addAttribute("isCmcc",
							simInformation.get("sim1IsCmcc"));
					firstSimElement.addAttribute("sim1Number",
							simInformation.get("sim1Number"));

					Element secondSimElement = simElement.addElement("sim2");
					secondSimElement.addAttribute("isCmcc",
							simInformation.get("sim2IsCmcc"));
					secondSimElement.addAttribute("sim2Number",
							simInformation.get("sim2Number"));

					break;

				}

			}

		}

		return XmlUtil.saveXmlDocument(fileName, document);

	}

	static public boolean AadStartReset2Xml(String fileName) {

		Document document = XmlUtil.getXmlDocument(fileName);

		Element rootElement = document.getRootElement();

		Element resultElement = rootElement.element("result");
		if (null != resultElement) {

			Element resetListElement = resultElement.element("resetList");
			int reset = Integer.valueOf(resetListElement
					.attributeValue("total"));
			reset++;

			resetListElement.addAttribute("total", String.valueOf(reset));

			resetListElement.addAttribute("startDate",
					TimeUtil.getDateString(new Date()));

		}

		return XmlUtil.saveXmlDocument(fileName, document);
	}

	static public boolean AddFinishReset2Xml(String fileName) {

		Document document = XmlUtil.getXmlDocument(fileName);

		Element rootElement = document.getRootElement();

		Element resultElement = rootElement.element("result");
		if (null != resultElement) {

			Element resetListElement = resultElement.element("resetList");

			String reset = resetListElement.attributeValue("total");
			String startDate = resetListElement.attributeValue("startDate");

			Element detailElement = resultElement.element("detail");
			Element resetElement = detailElement.addElement("reset");
			resetElement.addAttribute("action", "Reset");
			resetElement.addAttribute("number", reset);
			resetElement.addAttribute("startDate", startDate);
			resetElement.addAttribute("endDate",
					TimeUtil.getDateString(new Date()));
			resetElement.addAttribute("reason", "assert");

			resetListElement.add((Element) resetElement.clone());

			resetElement.addAttribute("startDate",
					TimeUtil.getDateString(new Date()));

		}

		return XmlUtil.saveXmlDocument(fileName, document);
	}

	static public boolean addTestResult2Xml(String fileName, int caseId,
			int result, Date caseStartDate, Date caseEndDate, int slotId,
			Map<String, String> resultMap, int sleepSeconds) {

		String detail = resultMap.get("detail");

		Document document = XmlUtil.getXmlDocument(fileName);

		Element rootElement = document.getRootElement();

		Element resultElement = rootElement.element("result");
		if (null != resultElement) {

			resultElement.addAttribute("endDate",
					TimeUtil.getDateString(new Date()));

			boolean isPassResult = TConstant.isPassResult(result);

			int totalPass = Integer.valueOf(resultElement
					.attributeValue("pass"));
			int totalFail = Integer.valueOf(resultElement
					.attributeValue("fail"));

			if (isPassResult)
				totalPass++;
			else
				totalFail++;

			resultElement.addAttribute("pass", String.valueOf(totalPass));
			resultElement.addAttribute("fail", String.valueOf(totalFail));
			resultElement.addAttribute("total",
					String.valueOf(totalFail + totalPass));

			Element detailElement = resultElement.element("detail");
			Element sleepElement = detailElement.addElement("sleep");
			sleepElement.addAttribute("action", "Sleep");
			sleepElement.addAttribute("duration", String.valueOf(sleepSeconds));

			Element listElement = detailElement.addElement("list");
			listElement.addAttribute("sn",
					String.valueOf(totalPass + totalFail));
			listElement.addAttribute("action", TConstant.getTCName(caseId));
			listElement.addAttribute("simId",
					"sim" + TConstant.getSIMString(slotId));
			listElement.addAttribute("result",
					TConstant.getResultDescription(result));
			listElement.addAttribute("detail", detail);


			listElement
					.addAttribute("caseDuration", String.valueOf(TimeUtil
							.getDistanceByType(caseStartDate, caseEndDate,
									TimeUtil.s)));
			listElement.addAttribute("startDate",
					TimeUtil.getDateString(caseStartDate));
			listElement.addAttribute("endDate",
					TimeUtil.getDateString(caseEndDate));

			switch (caseId) {

			case TConstant.moTCIndex:

				Element moElement = resultElement.element("mo");
				int moPass = Integer.valueOf(moElement.attributeValue("pass"));
				int moFail = Integer.valueOf(moElement.attributeValue("fail"));

				if (isPassResult)
					moPass++;
				else
					moFail++;

				moElement.addAttribute("pass", String.valueOf(moPass));
				moElement.addAttribute("fail", String.valueOf(moFail));
				moElement
						.addAttribute("total", String.valueOf(moFail + moPass));

				switch (result) {
				case TConstant.callDropResult:
					Element callDropElement = moElement.element("callDrop");

					int totalMoCallDrop = Integer.valueOf(callDropElement
							.attributeValue("total"));
					totalMoCallDrop++;
					callDropElement.addAttribute("total",
							String.valueOf(totalMoCallDrop));

					callDropElement.add((Element) listElement.clone());

					break;
				case TConstant.callFailResult:

					Element callFailElement = moElement.element("callFail");
					int totalMoCallFail = Integer.valueOf(callFailElement
							.attributeValue("total"));
					totalMoCallFail++;
					callFailElement.addAttribute("total",
							String.valueOf(totalMoCallFail));

					callFailElement.add((Element) listElement.clone());
					break;
				case TConstant.apCallFailResult:
					Element apCallFailElement = moElement.element("apCallFail");
					int totalMoAPCallFail = Integer.valueOf(apCallFailElement
							.attributeValue("total"));
					totalMoAPCallFail++;

					apCallFailElement.addAttribute("total",
							String.valueOf(totalMoAPCallFail));

					apCallFailElement.add((Element) listElement.clone());

					break;

				}

				break;

			case TConstant.downloadTCIndex:

				listElement.addAttribute("simId", "default SIM");

				Element downloadElement = resultElement.element("download");
				int downloadPass = Integer.valueOf(downloadElement
						.attributeValue("pass"));
				int downloadFail = Integer.valueOf(downloadElement
						.attributeValue("fail"));

				if (isPassResult)
					downloadPass++;
				else
					downloadFail++;

				downloadElement.addAttribute("pass",
						String.valueOf(downloadPass));
				downloadElement.addAttribute("fail",
						String.valueOf(downloadFail));
				downloadElement.addAttribute("total",
						String.valueOf(downloadPass + downloadFail));

				switch (result) {

				case TConstant.passResult:

					listElement.addAttribute("dlThroughput",
							resultMap.get("dl") + "kpbs");
					listElement.addAttribute("totalRxBytes",
							resultMap.get("totalRxBytes"));
					listElement.addAttribute("during", resultMap.get("during"));

					long totalRxBytes = Long.valueOf(downloadElement
							.attributeValue("totalRxBytes"))
							+ Long.valueOf(resultMap.get("totalRxBytes"));

					int totalDuring = Integer.valueOf(downloadElement
							.attributeValue("during"))
							+ Integer.valueOf(resultMap.get("during"));

					if (totalDuring > 0) {

						float averageThroughput = totalRxBytes / totalDuring
								/ 1000 * 8;

						downloadElement.addAttribute("averageThroughput",
								String.valueOf(averageThroughput) + "kbps");
						downloadElement.addAttribute("totalRxBytes",
								String.valueOf(totalRxBytes));
						downloadElement.addAttribute("during",
								String.valueOf(totalDuring));
					}

					break;

				case TConstant.initialFTPFailResult:

					Element initialFTPFailElement = downloadElement
							.element("initialFTPFail");

					int totalInitialFTPFail = Integer
							.valueOf(initialFTPFailElement
									.attributeValue("total"));

					totalInitialFTPFail++;

					initialFTPFailElement.addAttribute("total",
							String.valueOf(totalInitialFTPFail));

					initialFTPFailElement.add((Element) listElement.clone());

					break;

				case TConstant.noDLFailResult:

					Element noDataTransferElement = downloadElement
							.element("noDataTransfer");

					int totalNoDataTransfer = Integer
							.valueOf(noDataTransferElement
									.attributeValue("total"));

					totalNoDataTransfer++;

					noDataTransferElement.addAttribute("total",
							String.valueOf(totalNoDataTransfer));

					noDataTransferElement.add((Element) listElement.clone());

					break;

				case TConstant.lowDLFailResult:

					Element lowThroughputElement = downloadElement
							.element("lowThroughput");

					int totalLowDataTransfer = Integer
							.valueOf(lowThroughputElement
									.attributeValue("total"));

					totalLowDataTransfer++;

					lowThroughputElement.addAttribute("total",
							String.valueOf(totalLowDataTransfer));

					lowThroughputElement.add((Element) listElement.clone());

					break;

				}

				break;
			case TConstant.pingTCIndex:

				listElement.addAttribute("simId", "default SIM");

				Element pingElement = resultElement.element("ping");
				int pingPass = Integer.valueOf(pingElement
						.attributeValue("pass"));
				int pingFail = Integer.valueOf(pingElement
						.attributeValue("fail"));

				if (isPassResult)
					pingPass++;
				else
					pingFail++;

				pingElement.addAttribute("pass", String.valueOf(pingPass));
				pingElement.addAttribute("fail", String.valueOf(pingFail));
				pingElement.addAttribute("total",
						String.valueOf(pingPass + pingFail));

				listElement.addAttribute("command", resultMap.get("command"));

				switch (result) {

				case TConstant.noPingResponseResult:

					Element noPingResponse = pingElement.element("noResponse");

					int totalFailSendPingCommand = Integer
							.valueOf(noPingResponse.attributeValue("total"));

					totalFailSendPingCommand++;

					noPingResponse.addAttribute("total",
							String.valueOf(totalFailSendPingCommand));

					noPingResponse.add((Element) listElement.clone());

					break;

				case TConstant.apSendPingFailResult:

					Element apSendPingFailElement = pingElement
							.element("apFailSendPing");

					int totalAPSendPingFail = Integer
							.valueOf(apSendPingFailElement
									.attributeValue("total"));
					totalAPSendPingFail++;

					apSendPingFailElement.addAttribute("total",
							String.valueOf(totalAPSendPingFail));

					apSendPingFailElement.add((Element) listElement.clone());

					break;

				case TConstant.unAcceptableLostPingPackagesResult:

					Element unAcceptableLostPingPackagesElement = pingElement
							.element("unacceptableLostPackages");

					int totalExceedMaxLostPingPackages = Integer
							.valueOf(unAcceptableLostPingPackagesElement
									.attributeValue("total"));

					totalExceedMaxLostPingPackages++;

					unAcceptableLostPingPackagesElement.addAttribute("total",
							String.valueOf(totalExceedMaxLostPingPackages));

					unAcceptableLostPingPackagesElement
							.add((Element) listElement.clone());
					// No need this break, for the ping fail ,it also need
					// caculate the rate
					// break;
				case TConstant.passResult:

					listElement.addAttribute("transmitted",
							resultMap.get("transmitted"));
					listElement.addAttribute("received",
							resultMap.get("received"));
					listElement.addAttribute("loss", resultMap.get("loss")
							+ "%");
					listElement.addAttribute("time", resultMap.get("time"));

					int totalTransmitted = Integer.valueOf(pingElement
							.attributeValue("transmitted"))
							+ Integer.valueOf(resultMap.get("transmitted"));

					int totalReceived = Integer.valueOf(pingElement
							.attributeValue("received"))
							+ Integer.valueOf(resultMap.get("received"));

					pingElement.addAttribute("transmitted",
							String.valueOf(totalTransmitted));
					pingElement.addAttribute("received",
							String.valueOf(totalReceived));

					if (totalTransmitted > 0) {

						float totalLoss = (totalTransmitted - totalReceived)
								/ totalTransmitted * 100;

						pingElement.addAttribute("loss",
								String.valueOf(totalLoss) + "%");
					}
					break;
				}

				break;
			}

		}

		return XmlUtil.saveXmlDocument(fileName, document);
	}

	static public boolean addFinishTestReason2Xml(String fileName, String reason) {

		Document document = XmlUtil.getXmlDocument(fileName);

		Element rootElement = document.getRootElement();

		Element resultElement = rootElement.element("result");
		if (null != resultElement) {

			resultElement.addAttribute("endDate",
					TimeUtil.getDateString(new Date()));
			resultElement.addAttribute("stopTestReason", reason);
		}

		return XmlUtil.saveXmlDocument(fileName, document);

	}

	static public Map<String, String> getLastResult(String fileName) {

		Map<String, String> map = new HashMap<String, String>();

		Document document = XmlUtil.getXmlDocument(fileName);

		Element rootElement = document.getRootElement();

		Element resultElement = rootElement.element("result");
		if (null != resultElement) {

			map.put("startDate", resultElement.attributeValue("startDate"));
			map.put("endDate", resultElement.attributeValue("endDate"));
			map.put("pass", resultElement.attributeValue("pass"));
			map.put("fail", resultElement.attributeValue("fail"));
			map.put("total", resultElement.attributeValue("total"));

			Element resetListElement = resultElement.element("resetList");
			map.put("reset", resetListElement.attributeValue("total"));

			Element moElement = resultElement.element("mo");
			map.put("moTotal", moElement.attributeValue("total"));
			map.put("moFail", moElement.attributeValue("fail"));
			map.put("moPass", moElement.attributeValue("pass"));

			Element moCallFailElement = moElement.element("callFail");
			map.put("callFail_mo", moCallFailElement.attributeValue("total"));

			Element moCallDropElement = moElement.element("callDrop");
			map.put("callDrop_mo", moCallDropElement.attributeValue("total"));

			Element moApCallFailElement = moElement.element("apCallFail");
			map.put("apCallFail_mo",
					moApCallFailElement.attributeValue("total"));
			Element downloadElement = resultElement.element("download");
			map.put("dlPass", downloadElement.attributeValue("pass"));
			map.put("dlTotalRxBytes",
					downloadElement.attributeValue("totalRxBytes"));
			map.put("dlTotalDuring", downloadElement.attributeValue("during"));

			Element dlInitalFailElement = downloadElement
					.element("initialFTPFail");
			map.put("dlInitalFail", dlInitalFailElement.attributeValue("total"));

			Element noDataTransferElement = downloadElement
					.element("noDataTransfer");
			map.put("dlNoTransfer",
					noDataTransferElement.attributeValue("total"));

			Element lowThroughputElement = downloadElement
					.element("lowThroughput");
			map.put("dlLowThroughput",
					lowThroughputElement.attributeValue("total"));

			Element pingElement = resultElement.element("ping");
			map.put("pingPass", pingElement.attributeValue("pass"));
			map.put("transmitted", pingElement.attributeValue("transmitted"));
			map.put("received", pingElement.attributeValue("received"));

			Element noPingResponseCommandElement = pingElement
					.element("noResponse");
			map.put("noResponse",
					noPingResponseCommandElement.attributeValue("total"));

			Element unAcceptableLostPingPackagesElement = pingElement
					.element("unacceptableLostPackages");
			map.put("unacceptableLostPackages",
					unAcceptableLostPingPackagesElement.attributeValue("total"));

			Element apSendPingFailElement = pingElement
					.element("apFailSendPing");
			map.put("apFailSendPing",
					apSendPingFailElement.attributeValue("total"));

			Element simElement = resultElement.element("sim");
			map.put("isDSDSAP", simElement.attributeValue("isDSDSAP"));
			map.put("sims", simElement.attributeValue("sims"));

			Element sim1Element = simElement.element("sim1");
			if (null != sim1Element) {
				map.put("sim1_isCmcc", sim1Element.attributeValue("isCmcc"));
				map.put("sim1Number", sim1Element.attributeValue("sim1Number"));
			}

			Element sim2Element = simElement.element("sim2");
			if (null != sim2Element) {
				map.put("sim2_isCmcc", sim2Element.attributeValue("isCmcc"));
				map.put("sim2Number", sim2Element.attributeValue("sim2Number"));
			}

		}

		return map;
	}
}
