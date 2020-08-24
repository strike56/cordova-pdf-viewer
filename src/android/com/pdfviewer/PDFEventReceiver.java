package com.pdfviewer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONException;

public class PDFEventReceiver extends BroadcastReceiver {
    CallbackContext context;
    public static final String FLUSH_AWAY = "com.pdfview.action.FLUSH_AWAY";

    PDFEventReceiver(CallbackContext context) {
        super();
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            final String action = intent.getAction();
            if (FLUSH_AWAY.equals(action)) {
                this.handleFlushAway();
            }
        }
    }

    private void handleFlushAway() {
        PluginResult result = new PluginResult(PluginResult.Status.OK, "PDFView Closed");
        this.context.sendPluginResult(result);
    }
}
