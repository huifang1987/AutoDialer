package marvell.android.util;

import java.util.Iterator;
import java.util.Set;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class AndroidHelper {

	public static void printCusorColumnNames(Cursor c) {

		String[] names = c.getColumnNames();
		for (String name : names) {

			LogUtil.d("columnName = " + name);

		}
	}

	static public void printCusrorData(Cursor cursor) {

		int totalRowCounts = cursor.getCount();

		for (int rowIndex = 0; rowIndex < totalRowCounts; rowIndex++) {

			cursor.moveToPosition(rowIndex);

			for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++)
				LogUtil.d(cursor.getColumnName(columnIndex)
						+ "'="
						+ cursor.getString(cursor.getColumnIndex(cursor
								.getColumnName(columnIndex))));

		}

	}

	public static void printIntentData(Intent intent) {

		Bundle bundle = intent.getExtras();

		if (bundle != null) {

			LogUtil.d("该消息携带的数据如下：");

			// 获得bundle的一个key的集合

			Set set = bundle.keySet();

			// 获得上述集合的迭代器

			Iterator iterator = set.iterator();

			// 用迭代器遍历集合

			while (iterator.hasNext()) {

				// 取得集合中的一个内容

				String str = (String) iterator.next();

				// 取得Bundle中的内容

				LogUtil.d(str + "--->" + bundle.get(str));

			}

		} else {

			LogUtil.d("该消息没有携带数据");

		}

	}
}
