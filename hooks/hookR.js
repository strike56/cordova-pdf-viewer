#!/usr/bin/env node
/*
A hook to add R.java to the draw activity in Android platform.
*/

var fs = require('fs');
var path = require('path');

function replace_string_in_file(filename, to_replace, replace_with) {
    var data = fs.readFileSync(filename, 'utf8');
    var result = data.replace(to_replace, replace_with);
    fs.writeFileSync(filename, result, 'utf8');
}

var target = "stage";
if (process.env.TARGET) {
    target = process.env.TARGET;
}

var ourconfigfile = path.join( "plugins", "android.json");
var configobj = JSON.parse(fs.readFileSync(ourconfigfile, 'utf8'));
// Add java files where you want to add R.java imports in the following array

var filestoreplace = [
    "platforms/android/app/src/main/java/com/pdfviewer/PDFViewerActivity.java",
];

filestoreplace.forEach(function(val, index, array) {
    if (fs.existsSync(val)) {
        console.log("Android platform available !");
        //Getting the package name from the android.json file,replace with your plugin's id
        var packageName = configobj.installed_plugins["cordova-pdf-viewer"]["PACKAGE_NAME"];
        console.log("With the package name: " + packageName);
        console.log("Adding import for R.java");
        replace_string_in_file(val, "package com.pdfviewer;", "package com.pdfviewer;\n\nimport " + packageName + ".R;");

    } else {
        console.log("No android platform found! :(");
    }
})
