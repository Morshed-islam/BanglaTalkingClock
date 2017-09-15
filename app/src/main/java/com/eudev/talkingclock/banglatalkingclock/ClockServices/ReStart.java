package com.eudev.talkingclock.banglatalkingclock.ClockServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Morshed on 9/2/2017.
 */

public class ReStart extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TService.class));
    }
}
