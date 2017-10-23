package cn.edu.gdmec.android.moblieguard.m2theftguard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

import cn.edu.gdmec.android.moblieguard.R;

public class Setup4Activity extends BaseSetUpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_4);
         ((RadioButton) findViewById(R.id.rb_four)).setChecked(true);
    }
    public void showNext(){
        startActivityAndFinishSelf(LostFindActivity.class);
    }
    public void showPre(){
        startActivityAndFinishSelf(Setup3Activity.class);


    }
}
