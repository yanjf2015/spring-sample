package com.study.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.websocket.bean.Greeting;
import com.study.websocket.message.HelloMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author jingfeng.yan
 */
public class HelloMessageHandler extends TextWebSocketHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        HelloMessage helloMessage = objectMapper.readValue(new String(message.asBytes()), HelloMessage.class);
        System.out.println(helloMessage.getName());
        TextMessage webSocketMessage = new TextMessage(objectMapper.writeValueAsBytes(new Greeting("Hello, "
                + helloMessage.getName() + "!")));
        session.sendMessage(webSocketMessage);
        super.handleTextMessage(session, message);
    }
}
