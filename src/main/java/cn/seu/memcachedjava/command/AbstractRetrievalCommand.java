package cn.seu.memcachedjava.command;

import cn.seu.memcachedjava.Utils.MapUtil;
import cn.seu.memcachedjava.Utils.ThreadLocalUtil;
import cn.seu.memcachedjava.node.EntryNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ceshi
 * @Title: AbstractRetrievalCommand
 * @Package cn.seu.memcachedjava.command
 * @Description:
 * @date 2018/3/2514:57
 */
abstract public class AbstractRetrievalCommand  implements Command, ExecuteStrategy{

    protected Map<String, EntryNode> map = MapUtil.getMap();
    protected ThreadLocal<CommandAttr> commandAttrThreadLocal = ThreadLocalUtil.getCommandAttrThreadLocal();
    @Override
    final public Object execute() {
        return executeCommand();
    }

    /**
     * 获取命令执行的模板方法
     * @return
     */
    @Override
    final public Object executeCommand() {
        CommandAttr attr = commandAttrThreadLocal.get();
        List<String> keys = attr.getKeys();
        List<EntryNode> nodes = new ArrayList<>();
        for (String key : keys) {
            // null 情况移到下面处理
            nodes.add(map.get(key));
        }
        String result = (String) retrive(keys, nodes);

        return result;
    }

    /**
     * 获取客户端请求的数据
     * 该方法的名字取得并不好，因为Retrieval类型的命令的不同在于返回同一数据的不同形式，
     * 所以抽象处理向map获取后的数据提供用户实现
     * @param keys
     * @param nodes
     * @return
     */
    abstract protected Object retrive(List<String> keys, List<EntryNode> nodes);
}
