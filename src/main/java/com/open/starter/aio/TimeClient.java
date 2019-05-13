package com.open.starter.aio;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-09-21:06
 * @FUNCATION
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port));
    }
}
