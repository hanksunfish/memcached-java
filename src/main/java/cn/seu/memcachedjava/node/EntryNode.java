package cn.seu.memcachedjava.node;

import lombok.*;

/**
 * @author ceshi
 * @Title: EntryNode
 * @Package cn.seu.memcachedjava.node
 * @Description: 存储节点（value）
 * @date 2018/3/2515:02
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryNode {

    //<command name> <key> <flags> <exptime> <bytes> [noreply]\r\n
    //cas <key> <flags> <exptime> <bytes> <cas unique> [noreply]\r\n

    private int flags;
    private int exptime;
    private int bytes;
    private String dataBlock;
}
