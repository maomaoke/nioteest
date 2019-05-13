package com.open.starter.aio;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-09-20:10
 * @FUNCATION
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        AsyncTimeServerHandler serverHandler = new AsyncTimeServerHandler(port);
        new Thread(serverHandler).start();
    }
}
