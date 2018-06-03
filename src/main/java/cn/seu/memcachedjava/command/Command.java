package cn.seu.memcachedjava.command;

import cn.seu.memcachedjava.Utils.CommandType;

/**
 * @author ceshi
 * @Title: Command
 * @Package cn.seu.memcachedjava.command
 * @Description:
 * @date 2018/3/2514:50
 */
public interface Command {
    CommandType getCommandType();
    Object execute();
}
