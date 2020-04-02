package com.study.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.websocket.handler.Hello2MessageHandler;
import com.study.websocket.message.HelloMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class WebsocketApplicationTests {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private Hello2MessageHandler helloMessageHandler;

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
