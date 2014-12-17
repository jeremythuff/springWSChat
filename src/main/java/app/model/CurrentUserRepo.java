package app.model;

import org.springframework.data.repository.CrudRepository;

public interface CurrentUserRepo extends CrudRepository<User, Long> {
			
	public User getUserByName(String name);
	
	public User getUserBySessionId(String sessionId);
	
}
