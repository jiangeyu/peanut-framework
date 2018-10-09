package com.peanut.framework;


import com.peanut.framework.annotation.Transaction;
import com.peanut.framework.dao.DatabaseHelper;
import com.peanut.framework.proxy.Proxy;
import com.peanut.framework.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:46 2018/7/25
 * @desc
 */
public class TransactionProxy implements Proxy {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<Boolean> flagContainer = ThreadLocal.withInitial(() -> false);


    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        boolean flag = flagContainer.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            flagContainer.set(true);
            try {
                DatabaseHelper.startTransaction();
                logger.debug("start transaction");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                logger.debug("commit transaction");

            } catch (Exception e) {
                DatabaseHelper.roolbackTransction();
                logger.debug("rollback transaction");
            } finally {
                flagContainer.remove();
            }
        }
        return result;
    }
}
