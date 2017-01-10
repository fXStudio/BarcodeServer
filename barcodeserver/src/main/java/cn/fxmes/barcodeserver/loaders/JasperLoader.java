package cn.fxmes.barcodeserver.loaders;

import java.io.InputStream;

import cn.fxmes.barcodeserver.spi.IJasperLoader;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author Administrator
 */
public class JasperLoader implements IJasperLoader {
	/** 模板根目录 */
	private static final String PATH = "/resources/";

	/**
	 * 报表模板加载
	 */
	@Override
	public JasperReport get(String name) {
		InputStream in = null;
		JasperReport jr = null;

		try {
			jr = (JasperReport) JRLoader.loadObject(
					in = JasperLoader.class.getResourceAsStream(new StringBuilder(PATH).append(name).toString()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					in = null;
				}
			}
		}
		return jr;
	}
}
