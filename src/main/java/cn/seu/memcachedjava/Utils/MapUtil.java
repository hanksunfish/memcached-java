package cn.seu.memcachedjava.Utils;

import cn.seu.memcachedjava.node.EntryNode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ceshi
 * @Title: MapUtil
 * @Package cn.seu.memcachedjava.Utils
 * @Description:
 * @date 2018/3/2515:02
 */
public class MapUtil {

    private static Map<String, EntryNode> map = new ConcurrentHashMap<>();

    public static Map<String, EntryNode> getMap() {
        return map;
    }
}
