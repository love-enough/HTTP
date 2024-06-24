package org.study.Server;

import org.study.Server.handler.HttpRequestHandler;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleHttpServer {
    // 处理 HttpRequest 的线程池
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());

    /**
     * SimpleHttpServer 的根路径
     */
    private String basePath;

    /**
     * 服务监听端口
     */
    private int port = 8080;

    public void start() throws Exception {
        System.out.println("start at " + port + ", basePath: " + this.basePath);

        // serverSocket 接受客户端的请求
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket;
        /**
         * 服务端 serverSocket 接受客户端请求，并创建客户端 socket 对象
         */
        while ((socket = serverSocket.accept()) != null) {
            /**
             * 接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池队列
             */
            executor.execute(new HttpRequestHandler(socket, this.basePath));
        }
        serverSocket.close();
    }

    public void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() &&
                new File(basePath).isDirectory()) {
            this.basePath = basePath;
        }
    }

    public void setPort(int port) {
        if (port > 0) {
            this.port = port;
        }
    }
}
