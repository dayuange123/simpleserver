package club.dayuange.hanlder;

import club.dayuange.utils.LoggerInitialization;
import club.dayuange.utils.WebUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.logging.log4j.Logger;


import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;


/**
 * Http请求统一处理，该类不需要被访问，权限为default
 */
class WebServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	Logger logger=LoggerInitialization.logger;
	private final static DealRequest dealRequest=new DealRequest();

	/**
	 * 500页面
	 */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		if (ctx.channel().isActive());
	}
	/**
	 * 通道处理，可扩展，目前只解析一层，没有filter
	 */
	protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest request) throws Exception {

		dealRequest.mainDealRequest(ctx,request);



//		WebUtils.sendError(ctx, BAD_REQUEST);
//		logger.info("uri:"+request.uri());
//		logger.info("ontent:"+request.content());
//		logger.info("headers:"+request.headers().get("cookie"));
//		logger.info("decoderResult:");


	}
}