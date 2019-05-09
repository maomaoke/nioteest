package com.open.starter.nio;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-09-10:54
 * @FUNCATION
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer).start();

    }
}
