package cn.fxmes.barcodeserver.loaders;

import java.io.InputStream;

final class ImageLoader extends BaseLoader<InputStream> {

	@Override
	protected InputStream loadResource(InputStream in) {
		return in;
	}
}
