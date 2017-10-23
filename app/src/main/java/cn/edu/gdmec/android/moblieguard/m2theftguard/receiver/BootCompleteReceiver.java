package cn.edu.gdmec.android.moblieguard.m2theftguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.edu.gdmec.android.moblieguard.App;

/**
 * Created by asus-pc on 2017/10/23.
 */

public class BootCompleteReceiver extends BroadcastReceiver{
    public void onReceive(Context context, Intent intent){
        ((App)(context.getApplicationContext())).correctSIM();
    }

}
