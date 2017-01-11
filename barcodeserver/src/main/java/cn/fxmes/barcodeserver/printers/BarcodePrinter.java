package cn.fxmes.barcodeserver.printers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fxmes.barcodeserver.spi.IPrintServcieFinder;
import cn.fxmes.barcodeserver.spi.IPrinter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

/**
 * 条码打印工具类
 * 
 * @author Administrator
 */
@Component
final class BarcodePrinter implements IPrinter {
	private static Logger log = Logger.getLogger(BarcodePrinter.class);

	private @Autowired IPrintServcieFinder printServcieFinder;

	private JRPrintServiceExporter exporter = new JRPrintServiceExporter();

	/**
	 * 报表打印
	 *
	 * @param jasperPrint
	 * @throws JRException
	 */
	@Override
	public void print(JasperPrint jasperPrint, String printerName) throws JRException {
		log.debug("Reqeust Print Method.");
		log.debug("The jasperprint is: " + jasperPrint);

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE,
				printServcieFinder.getPrintService(printerName));

		exporter.exportReport();
	}

}
