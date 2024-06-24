package org.study.Server.handler;

import java.io.*;
import java.net.Socket;

/*
    处理request请求
 */
public class HttpRequestHandler implements Runnable{

    private Socket socket;
    private String basePath;

    public HttpRequestHandler(Socket socket, String basePath) {
        this.socket = socket;
        this.basePath = basePath;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        BufferedReader reader = null;

        // 向客户端输出内容
        PrintStream printStream = null;
        InputStream in = null;
        BufferedReader br = null;

        try {
            // 在 socket 上获得输入流，使用默认字符集的 InputStreamReader
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 读取 HTTP 报文的请求行
            String header = reader.readLine();
            System.out.println("thread name: " + threadName + ", HTTP 报文请求行: " + header);

            // 读取HTTP请求报文的起始行，并根据空格分割开，存入数组，得到请求的资源
            // 由相对路径计算出绝对路径
            String filePath = this.basePath + header.split(" ")[1];

            printStream = new PrintStream(socket.getOutputStream(), true);

            // 如果请求资源的后缀为jpg 或者 ico，则读取资源并输出
            if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                System.out.println("thread name: " + threadName + ", filePath: " + filePath);

                in = new FileInputStream(filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buff = new byte[1024];
                int i;
                while ((i = in.read(buff)) != -1) {
                    baos.write(buff, 0, i);
                }
                byte[] array = baos.toByteArray();

                // 设置响应报文
                printStream.println("HTTP/1.1 200 OK");
                printStream.println("Server: MyServer");
                printStream.println("Content-Type: image/jpeg");
                printStream.println("Content-Length: " + array.length);
                // 根据 HTTP 协议, 空行将结束头信息
                printStream.println();
                printStream.write(array);
            } else if (filePath.endsWith("html")) {
                // 客户端请求的是 html 文件
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                printStream.println("HTTP/1.1 200 OK");
                printStream.println("Server: MyServer");
                printStream.println("Content-Type: text/html; charset=UTF-8");
                printStream.println();

                String line;
                while ((line = br.readLine()) != null) {
                    printStream.println(line);
                }
            }
            printStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            printStream.println("HTTP/1.1 500");
            printStream.println();
            printStream.flush();
        } finally {
            this.close(br, in, reader, socket, printStream);
        }

    }
    private void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                try {
                    if (closeable != null) {
                        closeable.close();
                    }
                } catch (Exception e) {

                }
            }
        }

    }
}
