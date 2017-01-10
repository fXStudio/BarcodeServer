package cn.fxmes.barcodeserver.spi;

import net.sf.jasperreports.engine.JasperReport;

/**
 * 资源加载工具
 * 
 * @author Administrator
 *
 * @param <T>
 */
public interface IJasperLoader {
	public JasperReport get(String name);
}
