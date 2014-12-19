package app.model;

import java.util.List;

public class WSOut {
	
	private String name;
	private String message;
	private String action;
	private List<String> users;

    public WSOut(String action, String message, String name) {
    	this.name = name;
    	this.action = action;
    	this.message = message;
    }

    public WSOut(String action, List<String> users) {
    	this.users = users;
    	this.action = action;
	}

	public String getMessage() {
        return message;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAction() {
        return action;
    }
    
    public List<String> getUsers() {
        return users;
    }

}
