import PDFReader

@objc(CDVPDFViewer) class CDVPDFViewer : CDVPlugin {

    var cmd: CDVInvokedUrlCommand = CDVInvokedUrlCommand()

    func createBackButton(command: CDVInvokedUrlCommand) -> UIBarButtonItem {
        return UIBarButtonItem(title: "Close", style: .done, target: self, action: #selector(self.closeView))
    }

    @objc func closeView() {
        self.commandDelegate!.send(CDVPluginResult(status: CDVCommandStatus.ok, messageAs: "Close"), callbackId: cmd.callbackId)
        self.viewController.dismiss(animated: true)
    }


    @objc(showPdf:)
    func showPdf(command: CDVInvokedUrlCommand) {
        cmd = command
        print(command.argument(at: 0), "CMD")
        var url = ""
        var title = "PDF Preview"
        var scrollDir = "horizontal"
        if command.arguments.count >= 1 {
            url = command.arguments[0] as! String
            if command.arguments.count >= 2 {
                title = command.arguments[1] as! String
            }
            if command.arguments.count >= 3 {
                scrollDir = command.arguments[2] as! String
            }
        }
        let remotePDFDocumentURL = URL(string: url)!
        let document = PDFDocument(url: remotePDFDocumentURL)!
        let readerController = PDFViewController.createNew(with: document, title: title, actionStyle: .activitySheet, backButton: createBackButton(command: command));
        readerController.scrollDirection = scrollDir == "vertical" ? .vertical : .horizontal
        readerController.modalPresentationStyle = .fullScreen
        if #available(iOS 13.0, *) {
            readerController.isModalInPresentation = true
        }
        readerController.backgroundColor = .white;
        let navController = UINavigationController(rootViewController: readerController)

        if url.count > 0 {
           self.viewController.present(navController, animated: false)
        }
        self.commandDelegate!.send(CDVPluginResult(status: CDVCommandStatus.error, messageAs: "Not correct url: \(url)"), callbackId: cmd.callbackId)
    }
}
