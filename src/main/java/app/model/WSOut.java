package app.model;

public class WSOut {
	
	private String name;
	private String message;
	private String action;

    public WSOut(String action, String message, String name) {
    	this.name = name;
    	this.action = action;
    	this.message = message;
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

}
