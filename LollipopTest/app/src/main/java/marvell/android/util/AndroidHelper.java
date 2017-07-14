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

			LogUtil.d("����ϢЯ�����������£�");

			// ���bundle��һ��key�ļ���

			Set set = bundle.keySet();

			// ����������ϵĵ�����

			Iterator iterator = set.iterator();

			// �õ�������������

			while (iterator.hasNext()) {

				// ȡ�ü����е�һ������

				String str = (String) iterator.next();

				// ȡ��Bundle�е�����

				LogUtil.d(str + "--->" + bundle.get(str));

			}

		} else {

			LogUtil.d("����Ϣû��Я������");

		}

	}
}
