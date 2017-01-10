package cn.fxmes.barcodeserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.fxmes.barcodeserver.tasks.SocketTask;

public class Demo {
 public static void main(String[] args){
//		IJasperLoader loader = new JasperLoader();
//
//		JasperReport jasper = loader.get("FXMesBarcode.jasper");
//
//		List<BarcodeModel> list = new ArrayList<BarcodeModel>(1);
//		BarcodeModel model = new BarcodeModel();
//		model.setSupplier("LA403");
//		model.setMtlno("GC24010000027A");
//		model.setEngine("3411010-DL001*A*000");
//		model.setMartix(QRCodeUtils.getInstace().getByteStream("LA403GC24010000027A3411010-DL001*A*000", false));
//		list.add(model);
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("REPORT_LOGO", Demo.class.getResourceAsStream("/cn/fxmes/barcodeserver/jaspers/logo.png"));
//
//		BarcodePrinter.print(JasperFillManager.fillReport(jasper, map, new JRBeanCollectionDataSource(list)));
		
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-cfg.xml");  
		SocketTask action = (SocketTask)context.getBean("socketTask");
	}
}
