package cn.edu.gdmec.android.moblieguard.m2theftguard;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.moblieguard.R;

public class Setup2Activity extends BaseSetUpActivity implements View.OnClickListener{
    private TelephonyManager mTelephonyManager;
    private Button mBindSIMBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_2);
        ((RadioButton) findViewById(R.id.rb_second)).setChecked(true);
        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mBindSIMBtn = (Button) findViewById(R.id.btn_bind_sim);
        mBindSIMBtn.setOnClickListener(this);
        if (isBin()){
            mBindSIMBtn.setEnabled(false);
        }else{
            mBindSIMBtn.setEnabled(true);
        }
    }
    private boolean isBin(){
        String simString = sp.getString("sim",null);
        if (TextUtils.isEmpty(simString)) {
            return false;
        }
        return true;
    }

    public void showNext(){
        if (!isBin()){
            Toast.makeText(this, "您还没有绑定SIM卡", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivityAndFinishSelf(Setup3Activity.class);
    }



    public void showPre(){
        startActivityAndFinishSelf(Setup1Activity.class);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_bind_sim:
                bindSIM();
                break;
        }
    }
    private void bindSIM(){
        if (!isBin()){
            String simSerialNumber = mTelephonyManager.getSimSerialNumber();
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("sim",simSerialNumber);
            edit.commit();
            Toast.makeText(this, "SIM卡绑定成功！", Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);
        } else {
            Toast.makeText(this, "SIM卡已经绑定!", Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);
        }
    }
}
