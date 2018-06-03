package cn.seu.memcachedjava.command;

import cn.seu.memcachedjava.Utils.CommandType;
import cn.seu.memcachedjava.node.EntryNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ceshi
 * @Title: GetCommand
 * @Package cn.seu.memcachedjava.command
 * @Description:
 * @date 2018/3/2514:59
 */

@Setter
@Getter
@Component
public class GetCommand extends AbstractRetrievalCommand{
    final private CommandType commandType = CommandType.get;

    @Override
    protected Object retrive(List<String> keys, List<EntryNode> nodes) {
        StringBuilder resultBuilder = new StringBuilder();
        //VALUE <key> <flags> <bytes> [<cas unique>]\r\n
        //<data block>\r\n
        for (int i = 0; i < nodes.size(); i++) {
            EntryNode node = nodes.get(i);
            if (node != null) {
                resultBuilder.append("VALUE ")
                        .append(keys.get(i) + " ")
                        .append(node.getFlags() + " ")
                        .append(node.getExptime() + " ")
                        .append(node.getBytes() + " ")
                        .append("\r\n")
                        .append(node.getDataBlock())
                        .append("\r\n");
            }
        }
        resultBuilder.append("END" + "\r\n");
        return resultBuilder.toString();
    }
}
