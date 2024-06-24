package org.study.Server;

public class TestServer {
    public static void main(String[] args) throws Exception {
        SimpleHttpServer httpServer = new SimpleHttpServer();
//        httpServer.setPort(80);
        httpServer.setBasePath("/root/IdeaProjects/HTTP/src/main/java/org/study/Server/data");
        httpServer.start();
    }
}
