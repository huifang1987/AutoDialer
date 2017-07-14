package marvell.android.util;

import java.util.HashMap;
import java.util.Map;

import marvell.android.testsuit.TConstant;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import android.content.Context;

public class Configure {

	public static Boolean getTestingState(Context context) {

		boolean isTesting = false;

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element testingElement = rootElement.element("testing");
		if (null != testingElement) {

			String value = testingElement.getText();

			if ("1".equals(value))
				isTesting = true;

		}

		return isTesting;

		// Node node = document.selectSingleNode("/marvell/testing");
		// if(null == node)
		// LogUtil.d("node is null");
		// else
		// LogUtil.d("node is not null");
		//
		//
		// LogUtil.d("assert="
		// + document.selectSingleNode("/marvell/testing")
		// .getStringValue());
		//
		// if ("1".equals(document.selectSingleNode("/marvell/testing")
		// .getStringValue()))
		// return true;
		// else
		// return false;

	}

	public static Boolean getAssertState(Context context) {

		boolean hasAssert = false;

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element testingElement = rootElement.element("assert");
		if (null != testingElement) {

			String value = testingElement.getText();

			if ("1".equals(value))
				hasAssert = true;

		}

		return hasAssert;

	}

	public static Boolean getResetState(Context context) {

		boolean reset = false;

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element testingElement = rootElement.element("reset");
		if (null != testingElement) {

			String value = testingElement.getText();

			if ("1".equals(value))
				reset = true;

		}

		return reset;

	}

	public static boolean enableTesting(Context context) {

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element resetElement = rootElement.element("testing");
		if (null != resetElement)
			resetElement.setText(String.valueOf("1"));

		return XmlUtil.saveXmlDocument(TConstant.configureXml, document);

	}

	public static boolean disableTesting(Context context) {

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element resetElement = rootElement.element("testing");
		if (null != resetElement)
			resetElement.setText(String.valueOf("0"));

		return XmlUtil.saveXmlDocument(TConstant.configureXml, document);

	}

	public static String getResultPath(Context context) {

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		String value = "";

		Element rootElement = document.getRootElement();

		Element testingElement = rootElement.element("resultPath");
		if (null != testingElement) {

			value = testingElement.getText();

		}

		return value;

	}

	public static boolean saveResultPath(Context context, String path) {

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element resetElement = rootElement.element("resultPath");
		if (null != resetElement)
			resetElement.setText(String.valueOf(path));

		return XmlUtil.saveXmlDocument(TConstant.configureXml, document);

	}

	public static boolean enableReset(Context context) {

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element resetElement = rootElement.element("reset");
		if (null != resetElement)
			resetElement.setText(String.valueOf("1"));

		return XmlUtil.saveXmlDocument(TConstant.configureXml, document);

	}

	public static boolean disableReset(Context context) {

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element resetElement = rootElement.element("0");
		if (null != resetElement)
			resetElement.setText(String.valueOf(false));

		return XmlUtil.saveXmlDocument(TConstant.configureXml, document);

	}

	public static boolean saveConfiguration(Context context,
			boolean enableAutoAnswer, String sim1Number, String sim2Number,
			boolean autoReset) {

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element autoAnswerElement = rootElement.element("autoAnswer");
		if (null != autoAnswerElement)
			autoAnswerElement.setText(String.valueOf(enableAutoAnswer));

		Element sim1Element = rootElement.element("sim1");
		if (null != sim1Element)
			sim1Element.addAttribute("number", sim1Number);

		Element sim2Element = rootElement.element("sim2");
		if (null != sim2Element)
			sim2Element.addAttribute("number", sim2Number);

		Element autoResetElement = rootElement.element("autoReset");
		if (null != autoResetElement)
			autoResetElement.setText(String.valueOf(autoReset));

		return XmlUtil.saveXmlDocument(TConstant.configureXml, document);
	}

	public static Map<String, String> getConfiguration(Context context) {

		Map<String, String> map = new HashMap<String, String>();

		Document document = XmlUtil.getXmlDocument(TConstant.configureXml);

		Element rootElement = document.getRootElement();

		Element autoAnswerElement = rootElement.element("autoAnswer");
		if (null != autoAnswerElement)
			map.put("autoAnswer", autoAnswerElement.getText());

		Element sim1Element = rootElement.element("sim1");
		if (null != sim1Element)
			map.put("sim1Number", sim1Element.attributeValue("number"));

		Element sim2Element = rootElement.element("sim2");
		if (null != sim2Element)
			map.put("sim2Number", sim2Element.attributeValue("number"));

		Element autoResetElement = rootElement.element("autoReset");
		if (null != autoResetElement)
			map.put("autoReset", autoResetElement.getText());

		return map;

	}

}
