package cn.seu.memcachedjava.command;

import cn.seu.memcachedjava.Utils.CommandType;
import cn.seu.memcachedjava.node.EntryNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author ceshi
 * @Title: SetCommand
 * @Package cn.seu.memcachedjava.command
 * @Description:
 * @date 2018/3/2514:58
 */

@Setter
@Getter
@NoArgsConstructor
@Component
public class SetCommand extends AbstractStorageCommand{

    final private CommandType commandType = CommandType.set;

    @Override
    protected Object store(String key, EntryNode node) {
        return  map.put(key, node);
    }
}
