package app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String sessionId;
	private String name;
	
	protected User() {};
	
	public User(String sessionId, String name) {
		this.sessionId = sessionId;
		this.name = name;
	}
	
	@Override
    public String toString() {
        return String.format(
                "User[id=%d, sessionId='%s', name='%s']",
                id, sessionId, name);
    }
	
	public String getName() {
		return name;
	}

	public String getSessionId() {
		return sessionId;
	}
}
