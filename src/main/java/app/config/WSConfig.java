package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import app.controller.StompConnectEvent;
import app.controller.TopicSubscriptionInterceptor;
import app.model.CurrentUserRepo;

@Configuration
@EnableWebSocketMessageBroker
public class WSConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	@Bean
    public StompConnectEvent stompConnectEvent() {
        return new StompConnectEvent();
    }
	
	@Autowired
	private CurrentUserRepo currentUserRepo;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/WSRes");
		config.setApplicationDestinationPrefixes("/ws");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").withSockJS();
	}
	
	public void configureClientInboundChannel(ChannelRegistration registration) {
	    registration.setInterceptors(new TopicSubscriptionInterceptor(currentUserRepo));
	}

}
