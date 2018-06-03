package cn.seu.memcachedjava.parse;

import cn.seu.memcachedjava.Utils.CommandType;
import cn.seu.memcachedjava.Utils.ThreadLocalUtil;
import cn.seu.memcachedjava.command.CommandAttr;
import cn.seu.memcachedjava.errormsg.ErrorMessage;
import cn.seu.memcachedjava.node.SocketNode;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author ceshi
 * @Title: CommandParser
 * @Package cn.seu.memcachedjava.parse
 * @Description:
 * @date 2018/3/2515:08
 */

@Component
public class CommandParser implements Parser{

    private ThreadLocal<ErrorMessage> errorMessageThreadLocal
            = ThreadLocalUtil.getErrorMessageThreadLocal();
    private  ThreadLocal<CommandAttr> commandAttrThreadLocal
            = ThreadLocalUtil.getCommandAttrThreadLocal();
    private ThreadLocal<SocketNode> socketNodeThreadLocal =
            ThreadLocalUtil.getsocketNodeThreadLocal();
    public boolean parse(BufferedReader br) throws IOException {
        SocketNode socketNode = socketNodeThreadLocal.get();
        char[] data = new char[100];
        int c = 0;
        int index = 0;
        while ((c = br.read()) != -1) {
            if (c == '\n' && index != 0) {
                if (data[index - 1] == '\r') {
                    data[index] = (char) c;
                    break;
                }
            }
            data[index ++] = (char) c;
        }
        // 存储命令行
        if (index == 0) {
            // tcp连接关闭
            socketNode.setShouldShutDown(true);
            return false;
        }
        System.out.println("接受命令：" +String.copyValueOf(data, 0, index));
        data[index --] = '\0';
        data[index] = '\0';
        String commandStr = String.copyValueOf(data, 0, index);
        String[] attrs = commandStr.split(" ");
        CommandAttr commandAttr = null;
        index = 0;
        try {
            //    private CommandType commandType;
            //    @Builder.Default
            //    private List<String> keys = new ArrayList<>(4);
            //    private int flags;
            //    private int exptime;
            //    private int bytes;
            //    @Builder.Default
            //    private boolean noreply = false;
            //    private String dataBlock;
            if ("set".equals(attrs[index])) {
                if (attrs.length < 5 || attrs.length > 6) {
                    ErrorMessage message = new ErrorMessage("command error");
                    errorMessageThreadLocal.set(message);
                    return false;
                }
                // 解析 set 命令
                commandAttr = CommandAttr.builder()
                        .commandType(CommandType.set)
                        .flags(Integer.parseInt(attrs[2]))
                        .exptime(Integer.parseInt(attrs[3]))
                        .bytes(Integer.parseInt(attrs[4]))
                        .build();
                commandAttr.getKeys().add(attrs[1]);
                if (attrs.length == 6) {
                    commandAttr.setNoreply(Boolean.parseBoolean(attrs[5]));
                }

            } else if ("get".equals(attrs[index])) {
                if (attrs.length < 2) {
                    ErrorMessage message = new ErrorMessage("command error");
                    errorMessageThreadLocal.set(message);
                    return false;
                }
                // 解析 get 命令
                commandAttr = CommandAttr.builder()
                        .commandType(CommandType.get)
                        .build();
                for (int i = 1; i < attrs.length; i++) {
                    commandAttr.getKeys().add(attrs[i]);
                }

            } else if ("delete".equals(attrs[index])) {
                if (attrs.length != 2) {
                    ErrorMessage message = new ErrorMessage("command error");
                    errorMessageThreadLocal.set(message);
                    return false;
                }
                // 解析 get 命令
                commandAttr = CommandAttr.builder()
                        .commandType(CommandType.delete)
                        .build();
                commandAttr.getKeys().add(attrs[1]);
            }

            if (commandAttr.getCommandType() == CommandType.set) {
                // 解析 dataBlock
                int dataIndex = 0;
                // 重用 data数组
                int dc = 0;
                while ((dc = br.read()) != -1 && dataIndex <= commandAttr.getBytes()) {
                    data[dataIndex ++] = (char) dc;
                }
                commandAttr.setDataBlock(String.copyValueOf(data, 0, dataIndex-1));

            }
        } catch (NumberFormatException e) {
            ErrorMessage message = new ErrorMessage("command numberformat error");
            errorMessageThreadLocal.set(message);
            return false;
        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage("command error");
            errorMessageThreadLocal.set(message);
            return false;
        }
        commandAttrThreadLocal.set(commandAttr);
        return true;
    }
    // 解析命令
}
