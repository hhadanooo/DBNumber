package ir.hrwanheda.dbnumber.CheckPermissionAndIntroSlider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ir.hrwanheda.dbnumber.MainActivity;
import ir.hrwanheda.dbnumber.R;

public class IntroSlider extends AppCompatActivity {
    int[] id = {R.layout.slide1,R.layout.slide2,R.layout.slide3,R.layout.slide4};
    ViewPager viewpager;
    LinearLayout LayoutDots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
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
                // dots
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
}
