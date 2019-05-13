package com.open.starter.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-12-10:37
 * @FUNCATION
 */
public class HttpFileServer {

    public static void main(String[] args) throws InterruptedException {

        String url = "";

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpFileServerInitializer(url));

        ChannelFuture future = bootstrap.bind("127.0.0.1", 8080).sync();
        future.channel().closeFuture().sync();
    }
}
