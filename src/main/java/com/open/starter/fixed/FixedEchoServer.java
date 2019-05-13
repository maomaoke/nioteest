package com.open.starter.fixed;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-11-19:17
 * @FUNCATION
 */
public class FixedEchoServer {

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                .group(bossGroup, workGroup)
                .channel(ServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new FixedEchoServerHandler());
                    }
                });

        ChannelFuture future = bootstrap.bind(port).sync();
        future.channel().closeFuture().sync();

        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
