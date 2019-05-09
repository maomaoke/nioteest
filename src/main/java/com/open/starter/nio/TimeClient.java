package com.open.starter.nio;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-09-14:08
 * @FUNCATION
 */
public class TimeClient {

    public static void main(String[] args) {
        new Thread(new TimeClientHandler("127.0.0.1", 8080)).start();
    }
}
