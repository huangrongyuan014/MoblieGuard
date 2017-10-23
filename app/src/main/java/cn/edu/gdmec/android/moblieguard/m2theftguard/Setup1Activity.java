package cn.edu.gdmec.android.moblieguard.m2theftguard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.moblieguard.R;

public class Setup1Activity extends BaseSetUpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_1);
        ((RadioButton) findViewById(R.id.rb_first)).setChecked(true);
    }

   public void showNext(){startActivityAndFinishSelf(Setup2Activity.class);
   }



    public void showPre(){
        Toast.makeText(this, "当前页面已经是第一页", Toast.LENGTH_SHORT).show();
    }
}
