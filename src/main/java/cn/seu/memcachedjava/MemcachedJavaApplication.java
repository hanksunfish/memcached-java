package cn.seu.memcachedjava;

import cn.seu.memcachedjava.Utils.ApplicationContextUtil;
import cn.seu.memcachedjava.command.SetCommand;
import cn.seu.memcachedjava.parse.CommandParser;
import cn.seu.memcachedjava.parse.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;


@SpringBootApplication
public class MemcachedJavaApplication {

	public static int PORT = 11211;

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(MemcachedJavaApplication.class, args);
		ApplicationContextUtil.setContext(context);
		//System.out.println(args[0]);
		if (args != null && args.length > 0) {
			PORT = Integer.parseInt(args[0]);
		}
		//System.out.println(PORT);
		if(PORT < 0) {
			System.out.println("error: port < 0");
			return;
		}
		BootStrapter.booter();
	}

}
