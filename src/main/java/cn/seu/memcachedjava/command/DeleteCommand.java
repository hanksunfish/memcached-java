package cn.seu.memcachedjava.command;

import cn.seu.memcachedjava.Utils.CommandType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author ceshi
 * @Title: DeleteCommand
 * @Package cn.seu.memcachedjava.command
 * @Description:
 * @date 2018/3/2514:59
 */

@Setter
@Getter
@Component
public class DeleteCommand extends AbstractDeletionCommand{
    final private CommandType commandType = CommandType.delete;

    @Override
    protected Object delete(String key) {
        return map.remove(key);
    }
}
