package marvell.android.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import marvell.android.testsuit.TConstant;


public class MLog {

	public static void saveAll(String name) {

		LogUtil.d("saveAll=" + name);

		String cmd = "/data/bin/log.sh " + name;
		try {

			Runtime.getRuntime().exec(cmd);

		} catch (IOException e) {

			LogUtil.d("error to saveAll log");
		}

	}

	public static void saveCP(String name) {

		LogUtil.d("saveCP=" + name);

		String cmd = "/data/bin/cplog.sh " + name;

		try {

			Runtime.getRuntime().exec(cmd);

		} catch (IOException e) {

			LogUtil.d("error to saveCP log");

		}

	}

	public static void saveAPLog(String desPath, int reset) {

		String desApPath = desPath + "/aplog_reset" + reset;

		File desDir = new File(desPath);
		if (!desDir.exists() || !desDir.isDirectory())
			FileUtil.mkDir(desApPath);

		FileUtil.copyFolder(TConstant.apLogPath, desApPath);

	}

	public static void saveAcatLog(String acatPath, String desPath) {

		Date lastUpdateDate = new Date();

		File acatDir = new File(acatPath);

		if (acatDir.isDirectory()) {

			File desDir = new File(desPath);
			if (!desDir.exists() || !desDir.isDirectory())
				FileUtil.mkDir(desDir);

			TimeUtil.sleep(TConstant.wait4AcatSecond, TimeUtil.s);

			File[] files = acatDir.listFiles();
			for (File file : files) {

				if (file.isFile()) {

					String fileName = file.getName();

					if (fileName.contains(".sdl")) {

						Date lastModifiedDate = new Date(file.lastModified());

						if (lastModifiedDate.compareTo(lastUpdateDate) < 0) {

							if (acatInMinutesBefore(lastModifiedDate,
									lastUpdateDate)) {

								// LogUtil.d("copy before log,name=" +
								// fileName);

								FileUtil.copyFile(file, desPath);

							}
						} else {

							if (acatInMinutesAfter(lastUpdateDate,

							lastModifiedDate)) {

								// LogUtil.d("copy after log,name=" + fileName);
								FileUtil.copyFile(file, desPath);

							}
						}

					}

				}

			}

		}

	}

	public static String getLastAcatFolder() {

		List<String> dirs = FileUtil.getDirContainsName(TConstant.internalSD,
				"Log");

		String lastAcatFolder = "";

		Date lastUpdateDate = new Date();

		TimeUtil.sleep(2, TimeUtil.s);

		for (String dir : dirs) {

			File desDirectory = new File(dir);

			if (desDirectory.isDirectory()) {

				File[] files = desDirectory.listFiles();
				for (File file : files) {

					if (file.isFile()) {

						String fileName = file.getName();

						if (fileName.contains(".sdl")) {

							Date lastModifiedDate = new Date(
									file.lastModified());

							// LogUtil.d("lastModifiedDate"
							// + TimeUtil.getDateString(lastModifiedDate));
							//
							// LogUtil.d("lastUpdateDate"
							// + TimeUtil.getDateString(lastUpdateDate));

							if (lastModifiedDate.compareTo(lastUpdateDate) >= 0) {

								lastUpdateDate = lastModifiedDate;

								lastAcatFolder = desDirectory.getAbsolutePath();
							}

						}
					}

				}

			}

		}

		return lastAcatFolder;

	}

	public static boolean acatInMinutesBefore(Date startDate, Date endDate) {

		return TimeUtil.inLimitedTime(startDate, endDate, TimeUtil.s,
				TConstant.minutesBeforeTest);

	}

	public static boolean acatInMinutesAfter(Date startDate, Date endDate) {

		return TimeUtil.inLimitedTime(startDate, endDate, TimeUtil.s,
				TConstant.secondsAfterTest);

	}

}
