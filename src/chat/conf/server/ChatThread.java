package chat.conf.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import chat.conf.client.User;
import chat.conf.command.AbstractCommand;

public class ChatThread implements Runnable {
    private Socket socket;
    private PrintWriter print;
    private ChatServer server;
    private User user;
    
    public ChatThread(ChatServer server, Socket socket, User user){
        this.server = server;
        this.socket = socket;
        this.user = user;
    }

	@Override
	public void run() {
        try {
        	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	print = new PrintWriter(socket.getOutputStream(), true);
        	String users = server.showUserList(this);
        	print.println("Hello " + user.getUsername() + ", User List > " + users);
        	
       	 	while (true) {
               String input = in.readLine();
                
                AbstractCommand.execute(this, input.trim());

                System.out.println("Done BroadCast");
            }
            
            
        } catch (IOException e) {
            System.out.println("Error handling client #" + user.getUsername());
        } finally {
            try { 
            	server.removeUser(this);
            	socket.close(); 
            	
            } catch (IOException e) {}
            System.out.println("Connection with client # " + user.getUsername() + " closed > " + server.getClientCount() );
            
        }
    }  
    

	public void sendMessage(String message) {
		print.println(message);
	}

	public PrintWriter getWriter() {
		return print;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void broadcast(String input) {
		 server.broadcast(input, this, false);
	}
	
	public void broadcastSelf(String input, boolean info) {
		if (info) {
			 server.broadcastUser(this, input);
		} else {
			 server.broadcastUser(this, "me> "+ input);
		}
		
	}
	
	public void changeNick(String nick) {
		
		int result = server.setUserNick(this, nick);
		if (0 == result) {
			server.broadcastUser(this, "Nick already taken");
		} else if (1 == result) {
			server.broadcastUser(this, "You are using the same name :)");
		} else {
			server.broadcastUser(this, "Your new name is " +nick);
		}
		System.out.println("change nick >> result " + result);
		
	}
	public void register(String pwd) {
		boolean result = server.setPassword(this, pwd);
		System.out.println("register >> result " + result);
	}
	
}
