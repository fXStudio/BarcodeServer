package cn.fxmes.barcodeserver.loaders;

import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

final class JasperLoader extends BaseLoader<JasperReport> {
	@Override
	protected JasperReport loadResource(InputStream in) {
		try {
			return (JasperReport) JRLoader.loadObject(in);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}
}
