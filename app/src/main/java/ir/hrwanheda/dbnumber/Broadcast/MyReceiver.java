package ir.hrwanheda.dbnumber.Broadcast;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.Objects;

import ir.hrwanheda.dbnumber.wedgetSwing.FloatingViewService;

public class MyReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {

        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        Objects.requireNonNull(telephony).listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {

                if(state == 1)
                {
                    Toast.makeText(context,"number : "+incomingNumber,Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(new Intent(context, FloatingViewService.class));
                    }else {
                        context.startService(new Intent(context, FloatingViewService.class));
                    }
                }

            }
        },PhoneStateListener.LISTEN_CALL_STATE);
    }
}
