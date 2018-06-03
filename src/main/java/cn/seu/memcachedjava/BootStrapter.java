package cn.seu.memcachedjava;

import cn.seu.memcachedjava.Utils.ApplicationContextUtil;
import cn.seu.memcachedjava.Utils.ThreadLocalUtil;
import cn.seu.memcachedjava.execute.ExecuteFramwork;
import cn.seu.memcachedjava.node.SocketNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ceshi
 * @Title: BootStrapter
 * @Package cn.seu.memcachedjava
 * @Description: 启动类
 * @date 2018/3/2515:13
 */
public class BootStrapter {

    public static void booter() throws IOException {

        ServerSocket serverSocket = new ServerSocket(MemcachedJavaApplication.PORT);
        System.out.println("server: 127.0.0.1:" + MemcachedJavaApplication.PORT);
        ApplicationContext context = ApplicationContextUtil.getContext();
        ExecuteFramwork executeFramwork = context.getBean(ExecuteFramwork.class);

        // 使用线程池 线程并发 默认设置为10
        ExecutorService executor = Executors.newFixedThreadPool(10);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("A client is connected on port: " + socket.getPort() + ".");
            InputStream in = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            // 支持多客户端，但是并发问题还未细致考虑
            Runnable task = new Thread(
                    () -> {
                        try {
                            SocketNode socketNode = new SocketNode();
                            socketNode.setSocket(socket);
                            ThreadLocalUtil.getsocketNodeThreadLocal().set(socketNode);
                            while (!socketNode.isShouldShutDown()) {
                                /**
                                 * lambda中br,bw其实已经被附上了final属性
                                    也就是说在多个线程中的br,bw是相互独立的
                                    保证了线程安全性
                                  */
                                //System.out.println(Thread.currentThread().getName());
                                executeFramwork.excute(br, bw);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                System.out.println("A client on port: "+ socket.getPort() + " is disconnected.");
                                 //System.out.println(socket.isInputShutdown());
                                 //.out.println(socket.isOutputShutdown());
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            executor.submit(task);
        }
    }
}
