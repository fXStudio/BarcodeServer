package cn.fxmes.barcodeserver.models;

import java.io.InputStream;

public class BarcodeModel {
	private String supplier;
	private String mtlno;
	private String engine;
	private InputStream martix;

	public InputStream getMartix() {
		return martix;
	}

	public void setMartix(InputStream martix) {
		this.martix = martix;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getMtlno() {
		return mtlno;
	}

	public void setMtlno(String mtlno) {
		this.mtlno = mtlno;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}
}
