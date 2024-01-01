package com.ansim;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker //stomp메시지 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final StompHandler stompHandler; // jwt 토큰 인증 핸들러

    // 유저에 대한 세션키(socket id)를 저장할 용도의 map
    public static final Map<String, String> sessionKeys = new HashMap<>();

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 메시지채널에서 주고받는 메시지를 확인 및/또는 수정할 수 있는 인터셉터 추가
        registration.interceptors(new ChannelInterceptor() {
            // 메시지가 실제로 채널로 전송되기 전에 호출됨
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor headerAccessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                // 소켓 연결되었을 경우
                if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
                    // 유저명을 헤더에서 가져옴
                    List<String> usernames = Optional
                            .ofNullable(headerAccessor.getNativeHeader("username"))
                            .orElseGet(Collections::emptyList);

                    // 유저명이 존재하면 세션키를 맵에 저장
                    if (!usernames.isEmpty()) {
                        String sessionId = headerAccessor.getSessionId();
                        sessionKeys.put(usernames.get(0), sessionId);
                        System.out.println("유저별 세션아이디 : " + sessionKeys);
                    }
                }

                // 소켓 연결 끊겼을 경우
                if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
                    // 세션키를 사용하여 유저 정보 삭제
                    String sessionId = headerAccessor.getSessionId();
                    sessionKeys.values().remove(sessionId);
                }
                return message;
            }
        });
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); //중간에 가공을 거쳐 메시지를 전달
        registry.enableSimpleBroker("/queue"); //메시지를 그대로 구독자들에게 전달
        //registry.setApplicationDestinationPrefixes("/user");
        registry.setUserDestinationPrefix("/users");  // 특정 사용자에게 메시지 전송시 사용할 주소
    }
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") //커넥션 맺는 경로
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS(); //STOMP는 Sockjs기반. 프론트엔드에서 이 엔드포인트로 구독
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);//핸들러 등록
//    }

}