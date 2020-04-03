package com.study.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.websocket.bean.Greeting;
import com.study.websocket.handler.Hello2MessageHandler;
import com.study.websocket.message.HelloMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class WebsocketApplicationTests {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private Hello2MessageHandler helloMessageHandler;
    @Autowired
    private TaskScheduler taskScheduler;

	@Test
	public void sockJsClient() throws InterruptedException, ExecutionException, IOException {
        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.start();
        ListenableFuture<WebSocketSession> listenableFuture = sockJsClient.doHandshake(
                helloMessageHandler, "ws://localhost:8080/hi");
        System.out.println(listenableFuture.isDone());
        handler(listenableFuture.get());
        sockJsClient.stop();
	}

    @Test
    public void webSocketClient() throws ExecutionException, InterruptedException, IOException {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        ListenableFuture<WebSocketSession> listenableFuture =
                webSocketClient.doHandshake(helloMessageHandler, "ws://localhost:8080/hi");
        System.out.println(listenableFuture.isDone());
        handler(listenableFuture.get());
    }

    @Test
    public void webSocketStompClient() throws ExecutionException, InterruptedException, JsonProcessingException {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketTransport webSocketTransport = new WebSocketTransport(webSocketClient);
        SockJsClient sockJsClient = new SockJsClient(Collections.singletonList(webSocketTransport));
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(taskScheduler); // for heartbeats
//        stompClient.setReceiptTimeLimit(300);
//        stompClient.setDefaultHeartbeat(new long[]{10, 10});
        String url = "ws://127.0.0.1:8080/websocket";
        ListenableFuture<StompSession> connect = stompClient.connect(url, new StompSessionHandlerAdapter() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Greeting.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println(payload);
                super.handleFrame(headers, payload);
            }
        });
        StompSession stompSession = connect.get();
        HelloMessage helloMessage = new HelloMessage("cfs");
        stompSession.send("/app/hello", objectMapper.writeValueAsString(helloMessage));
        AtomicInteger atomicInteger = new AtomicInteger(0);
        stompSession.subscribe("/topic/greetings", new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Greeting.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                atomicInteger.getAndIncrement();
                try {
                    System.out.println(objectMapper.writeValueAsString(payload));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
        await().atMost(50, TimeUnit.SECONDS).until(() -> atomicInteger.get() == 1);
        stompClient.stop();
    }

    private void handler(WebSocketSession webSocketSession) throws IOException {
        System.out.println(webSocketSession.getId());
        HelloMessage helloMessage = new HelloMessage("test");
        TextMessage webSocketMessage = new TextMessage(objectMapper.writeValueAsBytes(helloMessage));
        webSocketSession.sendMessage(webSocketMessage);
        System.out.println(webSocketSession.isOpen());
        await().atLeast(10, TimeUnit.SECONDS).forever().until(() -> Hello2MessageHandler.atomicInteger.get() == 0);
        webSocketSession.close();
    }
}
