package cn.seu.memcachedjava.command;

import cn.seu.memcachedjava.Utils.CommandType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ceshi
 * @Title: CommandAttr
 * @Package cn.seu.memcachedjava.command
 * @Description: 用于储存出命令解析后的数据
 * @date 2018/3/2514:59
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommandAttr {

    //<command name> <key> <flags> <exptime> <bytes> [noreply]\r\n
    //cas <key> <flags> <exptime> <bytes> <cas unique> [noreply]\r\n

    private CommandType commandType;
    @Builder.Default
    private List<String> keys = new ArrayList<>(4);
    private int flags;
    private int exptime;
    private int bytes;
    @Builder.Default
    private boolean noreply = false;
    private String dataBlock;
}
