package cn.fxmes.barcodeserver.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.fxmes.barcodeserver.helpers.QRCodeUtils;
import cn.fxmes.barcodeserver.models.BarcodeModel;
import cn.fxmes.barcodeserver.spi.IJasperLoader;
import cn.fxmes.barcodeserver.spi.IPrinter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import wserver.EncryptWS;

/**
 * 服务器监听
 * 
 * @author ajaxfan
 */
class SocketTask implements Runnable {
	private static Logger log = Logger.getLogger(SocketTask.class);

	private @Autowired IPrinter barcodePrinter;

	private IJasperLoader jasperLoader;

	private ServerSocket serverSocket;

	private String jasperName;
	private String printerName;

	private byte[] buffer = new byte[2048];

	/**
	 * 不需要提供打印机名称的构造函数
	 * 
	 * @param serverSocket
	 * @param jasperName
	 * @param jasperLoader
	 */
	SocketTask(ServerSocket serverSocket, String jasperName, IJasperLoader jasperLoader) {
		this(serverSocket, jasperName, jasperLoader, null);
	}

	/**
	 * @param serverSocket
	 * @param jasperName
	 * @param jasperLoader
	 * @param printerName
	 */
	SocketTask(ServerSocket serverSocket, String jasperName, IJasperLoader jasperLoader, String printerName) {
		this.serverSocket = serverSocket;
		this.jasperName = jasperName;
		this.jasperLoader = jasperLoader;
		this.printerName = printerName;

		log.debug("Socket listener task created.");
		log.debug("ServerSocket object: " + serverSocket);
		log.debug("JasperName is: " + jasperName);
		log.debug("JasperLoader is: " + jasperLoader);
		log.debug("PrinterName is: " + printerName);
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		for (;;) {
			try {
				printBarcode(read(serverSocket.accept().getInputStream()));

				Thread.sleep(100);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private String read(InputStream in) throws Exception {
		try {
			StringBuilder sb = new StringBuilder();
			int n = 0;
			while ((n = in.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, n));
			}
			return new EncryptWS().decrypt(sb.toString());
		} finally {
			if (in != null) {
				try {
					in.close();
				} finally {
					in = null;
				}
			}
		}
	}

	/**
	 * @param content
	 * @throws JRException
	 */
	private void printBarcode(String code) throws Exception {
		log.info("Scanner barcode decrypted is: " + code);

		BarcodeModel model = new BarcodeModel();
		model.setSupplier(code.substring(0, 5));
		model.setMtlno(code.substring(5, 19));
		model.setEngine(code.substring(19));
		model.setMartix(QRCodeUtils.getInstace().getByteStream(code, false));

		barcodePrinter.print(JasperFillManager.fillReport(jasperLoader.get(jasperName), null,
				new JRBeanCollectionDataSource(Arrays.asList(new BarcodeModel[] { model }))), printerName);
	}
}
