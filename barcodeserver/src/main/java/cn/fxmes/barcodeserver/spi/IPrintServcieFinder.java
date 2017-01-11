package cn.fxmes.barcodeserver.spi;

import javax.print.PrintService;

public interface IPrintServcieFinder {
	public PrintService getPrintService(String name);
}
