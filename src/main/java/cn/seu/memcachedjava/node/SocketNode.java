package cn.seu.memcachedjava.node;

import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

/**
 * @author ceshi
 * @Title: SocketNode
 * @Package cn.seu.memcachedjava.node
 * @Description: socket信息，又必须可以扩展该类
 * @date 2018/3/2611:46
 */
@Setter
@Getter
public class SocketNode {

    private Socket socket;
    private boolean shouldShutDown = false;
}
