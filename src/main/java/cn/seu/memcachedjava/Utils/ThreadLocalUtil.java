package cn.seu.memcachedjava.Utils;

import cn.seu.memcachedjava.command.CommandAttr;
import cn.seu.memcachedjava.errormsg.ErrorMessage;
import cn.seu.memcachedjava.node.SocketNode;

/**
 * @author ceshi
 * @Title: ThreadLocalUtil
 * @Package cn.seu.memcachedjava.Utils
 * @Description:
 * @date 2018/3/2515:04
 */
public class ThreadLocalUtil {

    private static ThreadLocal<ErrorMessage> errorMessageThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<CommandAttr> commandAttrThreadLocal = new ThreadLocal<>();
    private static  ThreadLocal<SocketNode> socketNodeThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<ErrorMessage> getErrorMessageThreadLocal() {
        return errorMessageThreadLocal;
    }

    public static ThreadLocal<CommandAttr> getCommandAttrThreadLocal() {
        return commandAttrThreadLocal;
    }
    public static ThreadLocal<SocketNode> getsocketNodeThreadLocal() {
        return socketNodeThreadLocal;
    }
}
