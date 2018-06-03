package cn.seu.memcachedjava.parse;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author ceshi
 * @Title: Parser
 * @Package cn.seu.memcachedjava.parse
 * @Description:
 * @date 2018/3/2517:26
 */
public interface Parser {
    boolean parse(BufferedReader br) throws IOException;
}
