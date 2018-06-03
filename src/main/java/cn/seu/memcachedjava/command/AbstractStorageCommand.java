package cn.seu.memcachedjava.command;

import cn.seu.memcachedjava.Utils.MapUtil;
import cn.seu.memcachedjava.Utils.ThreadLocalUtil;
import cn.seu.memcachedjava.node.EntryNode;

import java.util.Map;

/**
 * @author ceshi
 * @Title: AbstractStorageCommand
 * @Package cn.seu.memcachedjava.command
 * @Description:
 * @date 2018/3/2514:56
 */
abstract public class AbstractStorageCommand  implements Command, ExecuteStrategy{

    protected Map<String, EntryNode> map = MapUtil.getMap();
    protected ThreadLocal<CommandAttr> commandAttrThreadLocal = ThreadLocalUtil.getCommandAttrThreadLocal();
    @Override
    public Object execute() {
        return executeCommand();
    }

    /**
     * 执行存储命令的的模板方法；
     * @return
     */
    @Override
    public Object executeCommand() {
        CommandAttr attr = commandAttrThreadLocal.get();
        EntryNode node = EntryNode.builder()
                .flags(attr.getFlags())
                .exptime(attr.getExptime())
                .bytes(attr.getBytes())
                .dataBlock(attr.getDataBlock())
                .build();
        EntryNode aldNode = (EntryNode) store(attr.getKeys().get(0), node);
        return "STORED" + "\r\n";
    }

    /**
     * 储存操作，子类可能需要对node进行处理，以适应不同的存储操作；
     * 例如append命令对dataBlock字段进行追加处理
     * @param key
     * @param node
     * @return
     */
    abstract protected Object store(String key, EntryNode node);
}
