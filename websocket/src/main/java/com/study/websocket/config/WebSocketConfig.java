package com.study.websocket.config;

import com.study.websocket.handler.HelloMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(helloMessageHandler(), "/hi")
                .setAllowedOrigins("*");
//                .withSockJS()
//                .setTaskScheduler(concurrentTaskScheduler())
//                .setHeartbeatTime(1);
    }

    @Bean
    public TextWebSocketHandler helloMessageHandler() {
        return new HelloMessageHandler();
    }

/*    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setServletContext(null);
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }*/

    @Bean
    public TaskScheduler concurrentTaskScheduler() {
        return new ConcurrentTaskScheduler();
    }

}
