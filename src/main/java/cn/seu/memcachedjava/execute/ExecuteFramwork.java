package cn.seu.memcachedjava.execute;

import cn.seu.memcachedjava.Utils.ThreadLocalUtil;
import cn.seu.memcachedjava.command.Command;
import cn.seu.memcachedjava.command.CommandAttr;
import cn.seu.memcachedjava.errormsg.ErrorMessage;
import cn.seu.memcachedjava.node.SocketNode;
import cn.seu.memcachedjava.parse.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author ceshi
 * @Title: ExecuteFramwork
 * @Package cn.seu.memcachedjava.execute
 * @Description: 该类类似于DispatcherServlet，请求处理流程框架
 * @date 2018/3/2515:07
 */
@Component
public class ExecuteFramwork {

    @Autowired
    private Parser commandParser ;//= ParserUtil.getParser();
    /**
     * 这两个组件不应该成为成员变量
     * 因为ExecuteFramwork是单例，而流并不是单例，
     * 并发情况下容易造成问题。
     * 简单使用传参简化该问题
     */
    /*private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;*/

    // TODO threadLocal真的是按照你的要求在处理吗？
    private ThreadLocal<ErrorMessage> errorMessageThreadLocal = ThreadLocalUtil.getErrorMessageThreadLocal();
    private ThreadLocal<CommandAttr> commandAttrThreadLocal = ThreadLocalUtil.getCommandAttrThreadLocal();
    private ThreadLocal<SocketNode> socketNodeThreadLocal =
            ThreadLocalUtil.getsocketNodeThreadLocal();
    @Autowired // 收集Command
    private List<Command> commands;

    public void excute(BufferedReader br, BufferedWriter bw) throws IOException {
        SocketNode socketNode = socketNodeThreadLocal.get();
        int port = socketNode.getSocket().getPort();
        boolean parseResult = commandParser.parse(br);
        if (!parseResult) {
            // 统一处理错误信息
            ErrorMessage errorMessage = errorMessageThreadLocal.get();
            if (errorMessage != null) {
                System.out.println(port + ": " + errorMessage.getMassage());
                //发送客户端
                bw.write(errorMessage.getMassage() + "\r\n");
                bw.flush();
            }
            errorMessageThreadLocal.remove();
            commandAttrThreadLocal.remove();
            // errorMessage == null  -> tcp连接断开
            return;
        }
        Command command = null;
        CommandAttr commandAttr = commandAttrThreadLocal.get();
        for (Command c : commands) {
            if (c.getCommandType() == commandAttr.getCommandType()) {
                command = c;
                break;
            }
        }
        // 执行命令
        String result = (String)command.execute();
        // 响应客户端
        System.out.println(port + ": " + result);
        bw.write(result);
        bw.flush();
        // 清除ThreadLocal
        commandAttrThreadLocal.remove();
        errorMessageThreadLocal.remove();
    }
}
