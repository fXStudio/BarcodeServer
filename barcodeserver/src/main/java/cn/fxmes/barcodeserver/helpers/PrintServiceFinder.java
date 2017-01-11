package cn.fxmes.barcodeserver.helpers;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import cn.fxmes.barcodeserver.spi.IPrintServcieFinder;

/**
 * 获取打印服务
 * 
 * @author Ajaxfan
 */
@Component
final class PrintServiceFinder implements IPrintServcieFinder {
	private static Logger log = Logger.getLogger(PrintServiceFinder.class);

	@Override
	@Cacheable(cacheNames = "printers")
	public PrintService getPrintService(String name) {
		for (PrintService printService : PrintServiceLookup.lookupPrintServices(null, null)) {
			log.debug(printService.getName());
			log.debug(name);

			if (printService.getName().equalsIgnoreCase(name)) {
				return printService;
			}
		}
		return PrintServiceLookup.lookupDefaultPrintService();
	}
}
