package cn.seu.memcachedjava.command;

import cn.seu.memcachedjava.Utils.MapUtil;
import cn.seu.memcachedjava.Utils.ThreadLocalUtil;
import cn.seu.memcachedjava.node.EntryNode;

import java.util.Map;

/**
 * @author ceshi
 * @Title: AbstractDeletionCommand
 * @Package cn.seu.memcachedjava.command
 * @Description:
 * @date 2018/3/2514:57
 */
abstract public class AbstractDeletionCommand implements Command, ExecuteStrategy{
    protected Map<String, EntryNode> map = MapUtil.getMap();
    protected ThreadLocal<CommandAttr> commandAttrThreadLocal = ThreadLocalUtil.getCommandAttrThreadLocal();
    @Override
    final public Object execute() {
        return executeCommand();
    }

    /**
     * 删除命令执行的模板方法
     * @return
     */
    @Override
    final public Object executeCommand() {
        CommandAttr attr = commandAttrThreadLocal.get();
        Object result = delete(attr.getKeys().get(0));
        if (result == null) {
            return "NOT_FOUND" + "\r\n";
        }
        return "DELETED" + "\r\n";
    }

    /**
     * 子类实现
     * @param key
     * @return
     */
    abstract protected Object delete(String key);
}
