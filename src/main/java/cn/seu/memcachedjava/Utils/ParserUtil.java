package cn.seu.memcachedjava.Utils;

import cn.seu.memcachedjava.node.EntryNode;
import cn.seu.memcachedjava.parse.CommandParser;
import cn.seu.memcachedjava.parse.Parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ceshi
 * @Title: ParserUtil
 * @Package cn.seu.memcachedjava.Utils
 * @Description:
 * @date 2018/3/2518:17
 */
public class ParserUtil {
    private static Parser parser = new CommandParser();
    public static Parser getParser() {
        return parser;
    }
}
