package club.dayuange.hanlder;

import club.dayuange.utils.LoggerInitialization;
import club.dayuange.utils.WebUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.logging.log4j.Logger;

/**
 * Http请求统一处理，该类不需要被访问，权限为default
 */
public class WebServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	Logger logger=LoggerInitialization.logger;
	public final static DealRequest dealRequest=new DealRequest();

	/**
	 * 500页面
	 */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error(cause.getMessage());
		if (ctx.channel().isActive()){
			WebUtils.sendError(ctx, HttpResponseStatus.BAD_GATEWAY);
		}
	}
	/**
	 * 处理
	 */
	protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest request) throws Exception {
		dealRequest.mainDealRequest1(ctx,request);
	}
}