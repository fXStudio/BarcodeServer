package cn.fxmes.barcodeserver.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import cn.fxmes.barcodeserver.helpers.QRCodeUtils;
import cn.fxmes.barcodeserver.models.BarcodeModel;
import cn.fxmes.barcodeserver.printers.BarcodePrinter;
import cn.fxmes.barcodeserver.spi.IJasperLoader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import wserver.EncryptWS;

/**
 * 服务器监听
 * 
 * @author ajaxfan
 */
class SocketTask {
	private @Autowired IJasperLoader jasperLoader;

	private byte[] buffer = new byte[2048];

	private String jasperName;

	/**
	 * 启动后台线程，实现监听服务
	 * 
	 * @param taskExecutor
	 */
	private SocketTask(TaskExecutor taskExecutor, final ServerSocket serverSocket) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						printBarcode(read(serverSocket.accept().getInputStream()));

						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			/**
			 * @param in
			 * @return
			 * @throws IOException
			 */
			private String read(InputStream in) throws IOException {
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
			private void printBarcode(String code) throws JRException {
				BarcodeModel model = new BarcodeModel();
				model.setSupplier("234234");
				model.setMtlno("werwerwe");
				model.setEngine("234234234");
				model.setMartix(QRCodeUtils.getInstace().getByteStream(code, false));

				BarcodePrinter.print(JasperFillManager.fillReport(jasperLoader.get(jasperName), null,
						new JRBeanCollectionDataSource(Arrays.asList(new BarcodeModel[] { model }))));
			}
		});
	}

	public void setJasperName(String jasperName) {
		this.jasperName = jasperName;
	}
}
