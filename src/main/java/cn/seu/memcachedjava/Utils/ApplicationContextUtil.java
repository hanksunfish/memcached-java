package cn.seu.memcachedjava.Utils;

import org.springframework.context.ApplicationContext;

/**
 * @author ceshi
 * @Title: ApplicationContextUtil
 * @Package cn.seu.memcachedjava.Utils
 * @Description:
 * @date 2018/3/2518:58
 */
public class ApplicationContextUtil {

    private static ApplicationContext context;

    public static void setContext(ApplicationContext ac) {
        context = ac;
    }
    public static ApplicationContext getContext() {
        return  context;
    }
}
