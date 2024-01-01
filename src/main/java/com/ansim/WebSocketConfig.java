package com.ansim;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); //상황에 따라 바로 브로커로 가는 것이 아니라,
                                                        // 메시지의 어떤 처리나 가공이 필요할 때 핸들러를 타게 할 수 있음.
        registry.enableSimpleBroker("/queue"); // 메시지가 1:1로 송신될때 사용
        //registry.setApplicationDestinationPrefixes("/user");
    }
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS(); //STOMP는 Sockjs기반. 프론트엔드에서 이 엔드포인트로 구독
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);
//    }
}