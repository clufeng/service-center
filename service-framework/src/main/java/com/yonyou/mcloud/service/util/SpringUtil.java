package com.yonyou.mcloud.service.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 *     Spring 工具类，<br/>
 *     主要获取实体bean
 * </p>
 *
 * Created by hubo on 2016/1/7
 */
public class SpringUtil {

    private volatile static ApplicationContext context;


    public static Object getBean(String name) {
        return getContext().getBean(name);
    }

    private static ApplicationContext getContext() {
        if(context == null) {
            synchronized (SpringUtil.class) {
                if(context == null) {
                    context = new ClassPathXmlApplicationContext("spring.xml", "spring-*.xml");
                    if(context instanceof ConfigurableApplicationContext) {
                        ((ConfigurableApplicationContext)context).registerShutdownHook();
                    }
                }
            }
        }
        return context;
    }

}
