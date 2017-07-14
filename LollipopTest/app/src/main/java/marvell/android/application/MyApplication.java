package marvell.android.application;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: Fanghui
 * @mail: fangh18@chinaunicom.cn
 * @date: 2017-7-13.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        deleteFile(Environment.getExternalStorageDirectory().getPath()+"/fieldTrail");
        mkDirOnSdcard("fieldTrail");
        copyAssets(this, "fieldTrail");
        mkDirOnSdcard("PhoneNumber");
    }

    public static MyApplication getInstance(){
        return instance;
    }


    private void mkDirOnSdcard(String dirName){
        File sd= Environment.getExternalStorageDirectory();
        String path=sd.getPath()+"/"+dirName;
        File file=new File(path);
        if(!file.exists())
            file.mkdir();
    }

    /**
     * 递归删除文件或者目录
     *
     * @param path 文件路径
     * @return 是否删除成功
     */
    public boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }


    private void copyAssets(Context context, String dirName) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            if (filename.contains("images") || filename.contains("sounds") || filename.contains("webkit"))
                continue;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(Environment.getExternalStorageDirectory().getPath()+"/"+dirName, filename);
                if (outFile.exists()){
                    continue;
                }
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
