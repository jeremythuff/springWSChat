package app.model;

import javax.persistence.*;
import org.springframework.messaging.MessageChannel;


@Entity
@Table(name="current_users")
public class User {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name="session_id")
	private String sessionId;
	@Column(name="name")
	private String name;

	private static MessageChannel channel;
	
	@Override
    public String toString() {
        return String.format(
                "User[id=%d, sessionId='%s', name='%s']",
                id, sessionId, name);
    }
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}
	
	public MessageChannel getChannel() {
		return channel;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}
	
}
