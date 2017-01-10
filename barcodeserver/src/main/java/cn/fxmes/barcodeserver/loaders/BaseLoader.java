package cn.fxmes.barcodeserver.loaders;

import java.io.InputStream;

import cn.fxmes.barcodeserver.spi.IResourceLoader;

abstract class BaseLoader<T> implements IResourceLoader<T> {
	/** 资源文件的根目录 */
	private static final String RESOURCE_DIR = "/cn/fxmes/barcodeserver/resources/";

	/**
	 * 通过资源名称获取资源文件
	 */
	@Override
	public final T get(String name) {
		return loadResource(ClassLoader.class.getResourceAsStream(new StringBuilder(RESOURCE_DIR).append(name).toString()));
	}

	/**
	 * 实际的资源加载处理
	 * @param in
	 * @return
	 */
	protected abstract T loadResource(InputStream in);
}
