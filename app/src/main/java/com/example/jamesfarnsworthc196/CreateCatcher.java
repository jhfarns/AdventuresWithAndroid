package com.example.jamesfarnsworthc196;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CreateCatcher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Manager Create Added", Toast.LENGTH_SHORT).show();
    }
}
