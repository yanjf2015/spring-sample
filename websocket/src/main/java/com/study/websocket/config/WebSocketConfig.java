package com.study.websocket.config;

import com.study.websocket.handler.HelloMessageHandler;
import com.study.websocket.interceptor.HelloHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
                //
        registry.addHandler(helloMessageHandler(), "/hi")
                // 拦截器 “握手之前”和“握手之后”的方法
                .addInterceptors(new HelloHandshakeInterceptor())
                // 执行WebSocket握手的步骤，包括验证客户端起源，协商子协议以及其他详细信息
                .setHandshakeHandler(new DefaultHandshakeHandler())
                // 允许的来源
                .setAllowedOrigins("*")
                // 启用SockJS
                .withSockJS()
                .setTaskScheduler(threadPoolTaskScheduler())
                .setHeartbeatTime(1);
    }

    @Bean
    public TextWebSocketHandler helloMessageHandler() {
        return new HelloMessageHandler();
    }

/*    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }*/

    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

}
