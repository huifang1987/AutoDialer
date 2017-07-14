package marvell.android.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {

	public static boolean createXml(String fileName) {

		Document document = DocumentHelper.createDocument(); // �����ĵ�

		document.addElement("marvell");

		return saveXmlDocument(fileName, document);

	}

	public static Document getXmlDocument(String fileName) {

		SAXReader reader = new SAXReader();

		Document doc;

		try {

			doc = reader.read(new File(fileName));

			LogUtil.d("successfully open the Xml file ");

		} catch (DocumentException e) {

			doc = null;
			
			LogUtil.d("Fail to open the Xml file " + fileName);

			e.printStackTrace();
		}

		return doc;

	}

	public static boolean saveXmlDocument(String fileName, Document document) {

		boolean isSaved = true;

		/** ��document�е�����д���ļ��� */

		try {

			OutputFormat format = OutputFormat.createPrettyPrint();// �����͸�ʽ
			format.setEncoding("UTF-8");// �����ļ��ڲ����ֵı���
			String encoding = "UTF-8";// �����ļ��ı��룡����format����һ����
			OutputStreamWriter outstream = new OutputStreamWriter(
					new FileOutputStream(fileName), encoding);

			XMLWriter writer = new XMLWriter(outstream, format);

			writer.write(document);
			writer.close();

			LogUtil.d("successfully save the Xml file ");

		} catch (IOException e) {

			isSaved = false;

			e.printStackTrace();
		}

		return isSaved;

	}

}
