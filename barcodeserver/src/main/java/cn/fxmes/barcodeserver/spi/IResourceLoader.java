package cn.fxmes.barcodeserver.spi;

/**
 * 资源加载工具
 * 
 * @author Administrator
 *
 * @param <T>
 */
public interface IResourceLoader<T> {
	public T get(String name);
}
