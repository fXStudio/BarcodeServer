package cn.fxmes.barcodeserver.loaders;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import cn.fxmes.barcodeserver.spi.IJasperLoader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author Administrator
 */
final class JasperLoader implements IJasperLoader {
	private Logger log = Logger.getLogger(JasperLoader.class);

	private String directory;

	/**
	 * 报表模板加载
	 * 
	 * @throws JRException
	 */
	@Override
	@Cacheable(cacheNames = "jaspers")
	public JasperReport get(String name) throws JRException {
		log.debug("Request log jasper template. The cache is not exists.");

		InputStream in = null;
		JasperReport jr = null;

		try {
			jr = (JasperReport) JRLoader.loadObject(in = JasperLoader.class.getResourceAsStream(getFullPath(name)));
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

	/**
	 * 文件全路径
	 * 
	 * @param name
	 * @return
	 */
	private String getFullPath(String name) {
		return new StringBuilder("/").append(directory).append("/").append(name).toString();
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
