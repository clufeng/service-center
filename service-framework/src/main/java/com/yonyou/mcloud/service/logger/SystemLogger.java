package com.yonyou.mcloud.service.logger;

import Ice.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *     ice 默认系统日志
 * </p>
 * Created by hubo on 16/1/29
 */
public class SystemLogger implements Logger {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SystemLogger.class);

    public SystemLogger(String logName) {
        LoggerFactory.getLogger(logName);
    }

    @Override
    public void print(String message) {
        logger.info(message);
    }

    @Override
    public void trace(String category, String message) {
        logger.debug(category + ": " +message);
    }

    @Override
    public void warning(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public Logger cloneWithPrefix(String prefix) {
        return new SystemLogger(prefix);
    }
}
