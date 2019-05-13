package com.open.starter.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-11-17:57
 * @FUNCATION
 */
public class EchoClient {

    public static void main(String[] args) throws InterruptedException {

        String host = "127.0.0.1";
        int port = 8080;

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch)
                    throws Exception {
                ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new EchoClientHandler());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        channelFuture.channel().closeFuture().sync();

//        group.shutdownGracefully();
    }
}
