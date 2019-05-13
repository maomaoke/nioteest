package com.open.starter.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

/**
 * @AUTHOR CHEN-KE-CHAO
 * @DATE 2019-05-12-10:47
 * @FUNCATION
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    public HttpFileServerHandler(String url) {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if (!msg.decoderResult().isSuccess()) {
            return;
        }

        if (msg.method() != HttpMethod.GET) {
            return;
        }

        String uri = msg.uri();

    }
}
