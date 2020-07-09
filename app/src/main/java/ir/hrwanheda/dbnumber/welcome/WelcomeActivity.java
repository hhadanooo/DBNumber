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

import java.util.Objects;

import ir.hrwanheda.dbnumber.CheckPermissionAndIntroSlider.ActivityCheckPerm;
import ir.hrwanheda.dbnumber.CheckPermissionAndIntroSlider.IntroSlider;
import ir.hrwanheda.dbnumber.MainActivity;
import ir.hrwanheda.dbnumber.R;

public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("pref",MODE_PRIVATE);


        setContentView(R.layout.activity_welcome);
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
                startActivity(new Intent(WelcomeActivity.this, ActivityCheckPerm.class));
                finish();
                /*
                if(preferences.getString("start","").isEmpty())
                {
                    startActivity(new Intent(WelcomeActivity.this, ActivityCheckPerm.class));
                    finish();
                }else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }

                 */

            }
        }, 2500);
    }
}