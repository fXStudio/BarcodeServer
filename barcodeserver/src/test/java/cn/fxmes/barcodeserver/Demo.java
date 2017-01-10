package cn.fxmes.barcodeserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.fxmes.barcodeserver.helpers.QRCodeUtils;
import cn.fxmes.barcodeserver.loaders.JasperLoader;
import cn.fxmes.barcodeserver.models.BarcodeModel;
import cn.fxmes.barcodeserver.printers.BarcodePrinter;
import cn.fxmes.barcodeserver.spi.IJasperLoader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Demo {
	@Test
	public void test() throws JRException {
		IJasperLoader loader = new JasperLoader();

		JasperReport jasper = loader.get("FXMesBarcode.jasper");

		List<BarcodeModel> list = new ArrayList<BarcodeModel>(1);
		BarcodeModel model = new BarcodeModel();
		model.setSupplier("LA403");
		model.setMtlno("GC24010000027A");
		model.setEngine("3411010-DL001*A*000");
		model.setMartix(QRCodeUtils.getInstace().getByteStream("LA403GC24010000027A3411010-DL001*A*000", false));
		list.add(model);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("REPORT_LOGO", Demo.class.getResourceAsStream("/cn/fxmes/barcodeserver/jaspers/logo.png"));

		BarcodePrinter.print(JasperFillManager.fillReport(jasper, map, new JRBeanCollectionDataSource(list)));
	}
}
