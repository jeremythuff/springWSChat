package app.model;

public class WSOut {
	
	private String name;
	private String message;

    public WSOut(String message, String name) {
    	this.name = name;
    	this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
    public String getName() {
        return name;
    }

}
