package com.study.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.websocket.bean.Greeting;
import com.study.websocket.message.HelloMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jingfeng.yan
 */
@Component
public class Hello2MessageHandler extends TextWebSocketHandler {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        atomicInteger.getAndIncrement();
        System.out.println(new String(message.asBytes()));
        super.handleTextMessage(session, message);
        atomicInteger.getAndDecrement();
    }
}
