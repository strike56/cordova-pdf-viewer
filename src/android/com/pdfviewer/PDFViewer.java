package com.pdfviewer;

import android.content.Intent;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class PDFViewer extends CordovaPlugin {
    CallbackContext callbackContext;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("showPdf")) {
            this.callbackContext = callbackContext;
            showPdf(args);
            return true;
        }
        return false;
    }

    private void showPdf(JSONArray args) throws JSONException {
        final Intent i = new Intent(cordova.getContext(), PDFViewerActivity.class);
        i.putExtra("fileUrl", args.getString(0));
        i.putExtra("title", args.getString(1));
        i.putExtra("scrollDir", args.getString(2));
        this.cordova.getActivity().startActivity(i);
    }
}