package ir.hrwanheda.dbnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import ir.hrwanheda.dbnumber.customView.CustomView;
import ir.hrwanheda.dbnumber.setting.Setting;

public class MainActivity extends AppCompatActivity {

    View circleTop , view_top_tvName;
    EditText et_search;
    TextView tv_nameApp;
    ImageView iv_search , iv_moreApp, iv_setting;
    LinearLayout lay_search , lay_item , lay_inItem;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        init();
        createItem();

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Setting.class));
            }
        });


    }

    private void createItem() {

        for (int i = 0 ; i < 25; i++){
            CustomView cv = new CustomView(this , (i+1)+"- hhadanooo " , dm);

            lay_inItem.addView(cv);
        }

    }

    private void init() {
        circleTop = findViewById(R.id.circletop);
        et_search = findViewById(R.id.et_search);
        iv_search = findViewById(R.id.iv_search);
        lay_search = findViewById(R.id.lay_search);
        lay_item = findViewById(R.id.lay_item);
        iv_moreApp = findViewById(R.id.iv_moreApp);
        iv_setting = findViewById(R.id.iv_setting);
        tv_nameApp = findViewById(R.id.tv_nameApp);
        view_top_tvName = findViewById(R.id.view_top_tvName);
        lay_inItem = findViewById(R.id.lay_inItem);


        circleTop.getLayoutParams().width = (dm.widthPixels*2);
        circleTop.getLayoutParams().height = (dm.widthPixels);
        RelativeLayout.LayoutParams parameter =  (RelativeLayout.LayoutParams) circleTop.getLayoutParams();
        parameter.setMargins(0, (int) (dm.widthPixels*-.75), 0, 0);
        circleTop.setLayoutParams(parameter);

        lay_search.getLayoutParams().width = (int) (dm.widthPixels*.7);
        et_search.getLayoutParams().width = (int) (dm.widthPixels*.57);
        et_search.getLayoutParams().height = (int) (dm.widthPixels*.1);
        iv_search.getLayoutParams().width = (int) (dm.widthPixels*.1);
        iv_search.getLayoutParams().height = (int) (dm.widthPixels*.1);

        lay_item.getLayoutParams().width = (int) (dm.widthPixels*.8);
        lay_item.getLayoutParams().height = (int) (dm.heightPixels*.65);

        iv_moreApp.getLayoutParams().width = (int) (dm.widthPixels*.1);
        iv_moreApp.getLayoutParams().height = (int) (dm.widthPixels*.1);

        iv_setting.getLayoutParams().width = (int) (dm.widthPixels*.1);
        iv_setting.getLayoutParams().height = (int) (dm.widthPixels*.1);

        view_top_tvName.getLayoutParams().width = (int) (dm.widthPixels*.1);
        view_top_tvName.getLayoutParams().height = (int) (dm.widthPixels*.1);

        tv_nameApp.setTextSize((int) (dm.heightPixels*.015));
    }


}