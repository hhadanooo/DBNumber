package ir.hrwanheda.dbnumber.customView;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.hrwanheda.dbnumber.R;

public class CustomView extends LinearLayout {

    TextView tv_list;
    RelativeLayout lay_list_item;

    public CustomView(Context context , String name , DisplayMetrics dm ) {
        super(context);
        inflate(context, R.layout.custom_view, this);
        tv_list = findViewById(R.id.tv_list);
        lay_list_item = findViewById(R.id.lay_list_item);

        tv_list.setText(name);
        lay_list_item.getLayoutParams().width = (int) (dm.widthPixels*.57);
        lay_list_item.getLayoutParams().height = (int) (dm.widthPixels*.12);
    }

}
