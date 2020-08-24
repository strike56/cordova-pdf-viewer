package com.pdfviewer;

import android.content.Intent;
import android.util.Log;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        this.callbackContext.success("PDFViewer closed");
    }

    private void showPdf(JSONArray args) throws JSONException {
        final Intent i = new Intent(cordova.getContext(), PDFViewerActivity.class);
        Log.d("fileUrl", args.getString(0));
        Log.d("title", args.getString(1));

        String fileUrl = "";
        String title = "PDFViewer";
        String scrollDir = "vertical";
        if(args.length() >= 1 && args.getString(0) != null) {
            fileUrl = args.getString(0);
        }
        if(args.length() >= 2 && args.getString(1) != null) {
            title = args.getString(1);
        }
        if(args.length() >= 3 && args.getString(2) != null) {
            scrollDir = args.getString(2);
        }
        i.putExtra("fileUrl", fileUrl);
        i.putExtra("title", title);
        i.putExtra("scrollDir", scrollDir);
        this.cordova.getActivity().startActivity(i);
    }
}