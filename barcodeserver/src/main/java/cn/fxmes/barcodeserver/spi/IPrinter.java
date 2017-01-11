package cn.fxmes.barcodeserver.spi;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface IPrinter {
	public void print(JasperPrint jasperPrint, String printerName) throws JRException;
}
