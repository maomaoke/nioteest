package com.open.starter.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-09-10:55
 * @FUNCATION
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        for (;!stop;) {
            try {
                int select = selector.select();
                if (select == 0) {
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    try {
                        handleInput(selectionKey);
                    } catch (Exception e) {
                        selectionKey.cancel();
                        e.printStackTrace();
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }


    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {

                if (key.isAcceptable()) {
                    acceptHandler();
                }

                if (key.isReadable()) {
                    readHandler(key);
                }
        }
    }

    private void acceptHandler() throws IOException {
        //相当于tcp的三次握手
        //socketChannel代表着客户端连接的socket_channel
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        socketChannel.write(Charset.forName("UTF-8").encode("你已经连接到服务器了"));
    }

    private void readHandler(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        StringBuffer requestMessage = new StringBuffer();
        while (socketChannel.read(readBuffer) > 0) {
            //切换读模式
            readBuffer.flip();
            byte[] bytes = new byte[readBuffer.remaining()];
            readBuffer.get(bytes);
            requestMessage.append(new String(bytes, "UTF-8"));
        }
        System.out.println(requestMessage);
        socketChannel.register(selector, SelectionKey.OP_READ);
        doWrite(socketChannel, new Date().toString());

    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes(Charset.forName("UTF-8"));
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }

}
