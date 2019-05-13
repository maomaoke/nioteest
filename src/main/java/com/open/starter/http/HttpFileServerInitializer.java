package com.open.starter.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-12-10:40
 * @FUNCATION
 */
public class HttpFileServerInitializer extends ChannelInitializer<SocketChannel> {

    private final String url;

    public HttpFileServerInitializer(String url) {
        this.url = url;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("http-decoder",
                new HttpRequestDecoder());
        ch.pipeline().addLast("http-aggregator",
                new HttpObjectAggregator(64436));
        ch.pipeline().addLast("http-encoder",
                new HttpResponseEncoder());
        ch.pipeline().addLast("http-chunked",
                new ChunkedWriteHandler());
        ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
    }
}
