package cn.edu.gdmec.android.moblieguard.m1home.utils;

import android.app.Activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


import cn.edu.gdmec.android.moblieguard.R;
import cn.edu.gdmec.android.moblieguard.m1home.HomeActivity;
import cn.edu.gdmec.android.moblieguard.m1home.entity.VersionEntity;

/**
 * Created by asus-pc on 2017/10/21.
 */

public class VersionUpdateUtils {
    private String mVersion;
    private Activity context;
    private VersionEntity versionEntity;

    private  static final int MESSAGE_IO_ERROR = 102;
    private  static final int MESSAGE_JSON_ERROR = 103;
    private  static final int MESSAGE_SHOW_DIALOG = 104;
    private  static final int MESSAGE_ENTERHOME = 105;

    private Handler handle = new Handler(){
           public void handleMessage(Message msg){
               switch (msg.what){
                   case MESSAGE_IO_ERROR:
                       Toast.makeText(context, "IO错误", Toast.LENGTH_LONG).show();
                       break;
                   case MESSAGE_JSON_ERROR:
                       Toast.makeText(context, "JSON解析错误", Toast.LENGTH_LONG).show();
                       break;
                   case MESSAGE_SHOW_DIALOG:
                       showUpdateDialog(versionEntity);
                       break;
                   case MESSAGE_ENTERHOME:
                       Intent intent = new Intent(context, HomeActivity.class);
                       context.startActivity(intent);
                       context.finish();
                       break;
               }
           }
    };
public VersionUpdateUtils(String mVersion,Activity context){
    this.mVersion = mVersion;
    this.context = context;
}

    public void getCloudVersion(){
        try{
            HttpClient httpClient = new DecompressingHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),500);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(),500);
            HttpGet httpGet = new HttpGet("http://android2017.duapp.com/updateinfo.html");
            HttpResponse execute = httpClient.execute(httpGet);
            if(execute.getStatusLine().getStatusCode()==200){
                HttpEntity httpEntity = execute.getEntity();
                String result = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(result);
                versionEntity = new VersionEntity();
                versionEntity.versioncode = jsonObject.getString("code");
                versionEntity.description = jsonObject.getString("des");
                versionEntity.apkurl = jsonObject.getString("apkurl");
                if (!mVersion.equals(versionEntity.versioncode)){
                   handle.sendEmptyMessage(MESSAGE_SHOW_DIALOG);
                }
            }
        }catch (IOException e){
            handle.sendEmptyMessage(MESSAGE_IO_ERROR);
            e.printStackTrace();
        }catch (JSONException e){
            handle.sendEmptyMessage(MESSAGE_JSON_ERROR);
            e.printStackTrace();
        }
    }
    private void showUpdateDialog(final VersionEntity versionEntity){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("检查到有新版本:"+versionEntity.versioncode);
        builder.setMessage(versionEntity.description);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                downloadNewApk(versionEntity.apkurl);
            }
        });
        builder.setNegativeButton("暂不升级",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                enterHome();
            }
        });
        builder.show();
    }
    private void enterHome(){
        handle.sendEmptyMessage(MESSAGE_ENTERHOME);
    }
    private void downloadNewApk(String apkurl){
        DownloadUtils downloadUtils = new DownloadUtils();
        downloadUtils.downloadApk(apkurl,"mobileguard.apk",context);
    }
}

