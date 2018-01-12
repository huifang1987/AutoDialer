package marvell.android.lollipop;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView versionTv = (TextView) findViewById(R.id.tv_version);
        TextView timeTv = (TextView) findViewById(R.id.tv_time);
        TextView history = (TextView) findViewById(R.id.tv_history);
        versionTv.setText(getText(R.string.version)+getVersionName(this));
        timeTv.setText(getText(R.string.release_date)+getDate());
        history.setText(getText(R.string.feature));
    }


    private String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    private String getDate(){
        String dateString = "1970/1/1";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        dateString = formatter.format(date);
        return dateString;
    }
}
