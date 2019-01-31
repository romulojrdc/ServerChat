package chat.conf.server;

public interface Commands {
	public static final String QUIT = "/quit";
	public static final String LOGIN = "/login";
    public static final String CLOGOUT = "/logout";
    public static final String SAY = "/say";
    
    public static final String NICK = "/nick";
    public static final String REG = "/register";
    
    
    public void runCommand(ChatThread chatObj, String input);
}
