package club.dayuange.server;

import club.dayuange.hanlder.WebServerInitializer;
import club.dayuange.scanner.MainStartup;
import club.dayuange.utils.LoggerInitialization;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class WebServer {

    /**
     * 服务启动，入参为端口号
     */
    public static void run(int port) throws InterruptedException {
        // 初始化指定包下的所有类及子类

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new WebServerInitializer());

            Channel ch = b.bind(port).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}