package proxy;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:47 2018/7/13
 * @desc 代理接口
 */
public interface Proxy {

    Object doProxy(ProxyChain chain) throws Throwable;
}
