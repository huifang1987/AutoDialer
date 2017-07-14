package marvell.android.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import marvell.android.application.MyApplication;


/**
 * 保存在ShareProferences相关文件名，key 存取本地文件
 *
 * @author Administrator
 */
public final class ShareProferencesUtil {

    private static final String FILE_NAME_CONFIG = "config";
    private static final String FILE_NAME_CACHE_INFO = "cache_info";
    private static final String FILE_NAME_USERINFO = "user_info";
    private static final String FILE_NAME_FINISHED_COUNT = "finish_count";

    private ShareProferencesUtil() {

    }


    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareStringData(String fileName, String key,
                                            String value) {
        final SharedPreferences sharedPreference = MyApplication.getInstance()
                .getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareBooleanData(String fileName, String key,
                                             boolean value) {
        final SharedPreferences sharedPreference = MyApplication.getInstance()
                .getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareIntData(String fileName, String key, int value) {
        final SharedPreferences sharedPreference = MyApplication.getInstance()
                .getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareLongData(String fileName, String key, long value) {
        final SharedPreferences sharedPreference = MyApplication.getInstance()
                .getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 从本地文件
     *
     * @param fileName
     * @param key
     * @return
     */
    private static long getShareLongData(String fileName, String key) {
        final SharedPreferences sharedPreference = MyApplication.getInstance()
                .getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getLong(key, 0);
    }

    /**
     * 从本地文件
     *
     * @param fileName
     * @param key
     * @return
     */
    private static int getShareIntData(String fileName, String key) {
        final SharedPreferences sharedPreference = MyApplication.getInstance()
                .getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getInt(key, 0);
    }

    /**
     * 从本地文件
     *
     * @param fileName
     * @param key
     * @return
     */
    private static String getShareStringData(String fileName, String key) {
        final SharedPreferences sharedPreference = MyApplication.getInstance()
                .getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getString(key, "");
    }

    /**
     * 从本地文件
     *
     * @param fileName
     * @param key
     * @return
     */
    private static boolean getShareBooleanData(String fileName, String key,
                                               boolean defultValue) {
        final SharedPreferences sharedPreference = MyApplication.getInstance()
                .getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getBoolean(key, defultValue);
    }

    /**
     * 判断是否退出账号
     *
     * @return
     */
    public static boolean isLogout() {
        return getShareBooleanData(FILE_NAME_CONFIG, "logout",
                true);
    }

    /**
     * 设置是否退出账号
     *
     * @param isLogout
     */
    public static void setLogout(boolean isLogout) {
        saveShareBooleanData(FILE_NAME_CONFIG, "logout", isLogout);
    }

    /**
     * 设置Log开关状态
     *
     * @param flag
     */
    public static void setLogDebugFlag(boolean flag) {
        saveShareBooleanData(FILE_NAME_CONFIG, "log_debug_flag", flag);
    }

    /**
     * 获取Log开关状态
     */
    public static boolean getLogDebugFlag() {
        return getShareBooleanData(FILE_NAME_CONFIG, "log_debug_flag", false);
    }

    /**
     * 设置闪屏页面广告图片地址
     * @param path
     */
    public static void setSplashADImagePath(String path) {
        saveShareStringData(FILE_NAME_CACHE_INFO, "splash_ad_image_local_path", path);
    }

    /**
     * 获取闪屏页面广告图片地址
     * @return
     */
    public static String getSplashADImagePath() {
        return getShareStringData(FILE_NAME_CACHE_INFO, "splash_ad_image_local_path");
    }

    /**
     * 存储用户信息
     * @param key,value
     */
    public static void setUserInfoElement (String key, String value) {
        saveShareStringData(FILE_NAME_USERINFO, key, value);
    }

    /**
     * 读取用户信息
     * @param key
     */
    public static String getUserInfoElement(String key) {
        return getShareStringData(FILE_NAME_USERINFO, key);
    }

    /**
     * 存储已完成呼叫号码的数量
     * @param count
     */
    public static void setFinishCount (int count) {
        saveShareIntData(FILE_NAME_FINISHED_COUNT, "count", count);
    }

    /**
     * 读取已完成呼叫号码的数量
     * @param
     */
    public static int getFinishCount() {
        return getShareIntData(FILE_NAME_FINISHED_COUNT, "count");
    }

}
