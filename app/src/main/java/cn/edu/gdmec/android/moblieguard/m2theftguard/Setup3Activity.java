package cn.edu.gdmec.android.moblieguard.m2theftguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.moblieguard.R;


public class Setup3Activity extends BaseSetUpActivity implements View.OnClickListener{
    private EditText mInputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_3);
        ((RadioButton) findViewById(R.id.rb_third)).setChecked(true);

        ((RadioButton) findViewById(R.id.rb_third)).setChecked(true);
        findViewById(R.id.btn_addcontact).setOnClickListener(this);
        mInputPhone = (EditText) findViewById(R.id.et_inputphone);
        String safephone = sp.getString("safephone",null);
        if (!TextUtils.isEmpty(safephone)){
            mInputPhone.setText(safephone);
        }
    }
    public void showNext(){
        String safePhone = mInputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(safePhone)){
            Toast.makeText(this, "请输入安全号码", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("safephone",safePhone);
        edit.commit();
        startActivityAndFinishSelf(Setup4Activity.class);
    }
    public void showPre(){
        startActivityAndFinishSelf(Setup2Activity.class);

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_addcontact:
                startActivityForResult(new Intent(this,ContactSelectActivity.class),0);
                break;
        }
    }
    protected void onActivityResult(int requesCode,int resultCode,Intent data){
        super.onActivityResult(resultCode,resultCode,data);
        if (data!=null){
            String phone = data.getStringExtra("phone");
            mInputPhone.setText(phone);
        }
    }
}
