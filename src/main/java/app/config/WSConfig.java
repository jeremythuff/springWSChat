package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import app.controller.eventInterceptors.StompDisconnectEvent;
import app.controller.eventInterceptors.TopicSubscriptionInterceptor;
import app.model.CurrentUserRepo;

@Configuration
@EnableWebSocketMessageBroker
public class WSConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	@Autowired
	private CurrentUserRepo currentUserRepo;

	@Bean
    public StompDisconnectEvent stompConnectEvent() {
        return new StompDisconnectEvent();
    }
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/WSRes");
		config.setApplicationDestinationPrefixes("/ws");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").withSockJS();
	}
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
	    registration.setInterceptors(new TopicSubscriptionInterceptor(currentUserRepo));
	}

}
