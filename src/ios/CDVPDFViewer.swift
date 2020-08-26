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
        guard let file = FileManager.default.contents(atPath: url) else { return }

        let document = PDFDocument(fileData: file, fileName: title)!
        let readerController = PDFViewController.createNew(with: document, title: title, actionStyle: .activitySheet, backButton: createBackButton(command: command));
        readerController.scrollDirection = scrollDir == "vertical" ? .vertical : .horizontal
        readerController.modalPresentationStyle = .fullScreen
        if #available(iOS 13.0, *) {
            readerController.isModalInPresentation = true
        }
        readerController.backgroundColor = .white;
        let navController = UINavigationController(rootViewController: readerController)

        if url.count > 0 {
            self.viewController.present(navController, animated: false, completion: {
                navController.presentationController?.presentedView?.gestureRecognizers?[0].isEnabled = false;
            })
        }
        self.commandDelegate!.send(CDVPluginResult(status: CDVCommandStatus.error, messageAs: "Not correct url: \(url)"), callbackId: cmd.callbackId)
    }
}
