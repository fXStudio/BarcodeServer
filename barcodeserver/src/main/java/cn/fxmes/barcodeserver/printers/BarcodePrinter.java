package cn.fxmes.barcodeserver.printers;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;

/**
 * 条码打印工具类
 * 
 * @author Administrator
 */
public class BarcodePrinter {
	/**
	 * 报表打印
	 *
	 * @param jasperPrint
	 * @throws JRException
	 */
	public static void print(JasperPrint jasperPrint) throws JRException {
		JRPrintServiceExporter exporter = new JRPrintServiceExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

		exporter.exportReport();
	}
}
