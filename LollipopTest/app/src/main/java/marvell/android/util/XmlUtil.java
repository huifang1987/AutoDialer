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

		Document document = DocumentHelper.createDocument(); // 创建文档

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

		/** 将document中的内容写入文件中 */

		try {

			OutputFormat format = OutputFormat.createPrettyPrint();// 缩减型格式
			format.setEncoding("UTF-8");// 设置文件内部文字的编码
			String encoding = "UTF-8";// 设置文件的编码！！和format不是一回事
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
