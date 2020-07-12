package ir.hrwanheda.dbnumber.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Objects;

import ir.hrwanheda.dbnumber.CheckPermissionAndIntroSlider.ActivityCheckPerm;
import ir.hrwanheda.dbnumber.CheckPermissionAndIntroSlider.IntroSlider;
import ir.hrwanheda.dbnumber.MainActivity;
import ir.hrwanheda.dbnumber.R;
import ir.hrwanheda.dbnumber.test;

public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ImageView img_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        preferences = getSharedPreferences("pref",MODE_PRIVATE);
        img_logo = findViewById(R.id.activity_welcome_img_logo);


        DisplayMetrics displayMetrics;
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        img_logo.getLayoutParams().width = (int)(displayMetrics.widthPixels * 0.6);
        img_logo.getLayoutParams().height = (int)(displayMetrics.widthPixels * 0.6);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
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
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(preferences.getString("start","").isEmpty())
                {
                    startActivity(new Intent(WelcomeActivity.this, ActivityCheckPerm.class));
                    finish();
                }else {
                    startActivity(new Intent(WelcomeActivity.this, test.class));
                    finish();
                }


            }
        }, 2000);
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

}