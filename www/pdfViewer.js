var exec = require('cordova/exec');

function PDFViewer() {}

PDFViewer.prototype.showPdf = function(success, error, opts) {
    exec(success, error, "PDFViewer", "showPdf", opts);
}

var pdfViewer = new PDFViewer();
module.exports = pdfViewer;