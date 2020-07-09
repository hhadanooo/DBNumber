package ir.hrwanheda.dbnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ir.hrwanheda.dbnumber.customView.CustomView;
import ir.hrwanheda.dbnumber.setting.Setting;

public class MainActivity extends AppCompatActivity {

    View circleTop , view_top_tvName;
    EditText et_search;
    TextView tv_nameApp;
    ImageView iv_search , iv_moreApp, iv_setting;
    LinearLayout lay_search , lay_item , lay_inItem;
    public static DisplayMetrics dm;
    RequestQueue queue ;
    AVLoadingIndicatorView avi;
    int delayAnim = 25;
    CustomView cv;
    int numOf = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        queue = Volley.newRequestQueue( this);

        init();


        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Setting.class));
            }
        });
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lay_inItem.removeAllViews();

                final String phoneN = et_search.getText().toString();
                if (!phoneN.isEmpty()){
                    et_search.setEnabled(false);
                    avi.show();
                    avi.setVisibility(View.VISIBLE);
                    iv_search.setVisibility(View.GONE);
                    StringRequest r = new StringRequest(Request.Method.POST,
                            "http://192.168.1.100:8080", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String Names = response;
                            if (!Names.isEmpty()){
                                //Toast.makeText(MainActivity.this, ""+Names, Toast.LENGTH_SHORT).show();
                                String[] s = Names.split("@");
                                numOf = s.length;
                                for (int i = 0 ; i < s.length ; i++){
                                    createItem(i , s[i]);

                                }
                            }else {
                                Toast.makeText(MainActivity.this, "Not found result!", Toast.LENGTH_SHORT).show();
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    avi.hide();
                                    avi.setVisibility(View.GONE);
                                    iv_search.setVisibility(View.VISIBLE);
                                    et_search.setEnabled(true);
                                    delayAnim = 25;
                                    numOf = 0;
                                }
                            } , 250*(int)(numOf*1.5));



                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Not found result!", Toast.LENGTH_SHORT).show();
                            avi.hide();
                            avi.setVisibility(View.GONE);
                            iv_search.setVisibility(View.VISIBLE);
                            et_search.setEnabled(true);
                            delayAnim = 25;
                            numOf = 0;
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param = new HashMap<String, String>();
                            param.put("phoneNumber" , phoneN);
                            return param;
                        }
                    };
                    queue.add(r);
                }else {
                    Toast.makeText(MainActivity.this, "please write something!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void createItem(final int i , final String s) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cv = new CustomView(MainActivity.this , (i+1)+"- "+s , dm);
                cv.startAnimation(AnimationUtils.loadAnimation(MainActivity.this , R.anim.animatcustom));
                lay_inItem.addView(cv);
            }
        } , delayAnim);
        delayAnim += 250;


    }

    private void init() {
        avi = findViewById(R.id.avi);
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
        avi.getLayoutParams().width = (int) (dm.widthPixels*.1);
        avi.getLayoutParams().height = (int) (dm.widthPixels*.1);

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