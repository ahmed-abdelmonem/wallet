package com.aabdelmonem.wallet.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Wallet Request Interceptor to log HTTP requests and responses. 
 * 
 * @author ahmed.abdelmonem
 * 
 */
@Component
public class WalletRequestInterceptor extends HandlerInterceptorAdapter {

	private static final Log logger = LogFactory.getLog(WalletRequestInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("method: ").append(request.getMethod()).append("\t");
		logMessage.append("uri: ").append(request.getRequestURI()).append("\t");
		logMessage.append("status: ").append(response.getStatus()).append("\t");
		logMessage.append("remoteAddress: ").append(request.getRemoteAddr()).append("\t");

		if (ex != null) {
			logger.error(logMessage.toString(), ex);
		} else {
			logger.info(logMessage.toString());
		}
	}

}
