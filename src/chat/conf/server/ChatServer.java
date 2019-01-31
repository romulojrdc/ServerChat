package chat.conf.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import chat.conf.client.User;

public class ChatServer {
	
	private int serverPort;
	private Map<String,ChatThread> users = new HashMap<String,ChatThread>();
	private Set<ChatThread> userThreads = new HashSet<>();
    private static int clientNumber = 0;
    
    public static void main(String[] args){
    	int port = PropsConstant.port;
    	ChatServer server = new ChatServer(port);
        server.startServer();
    }

    public ChatServer(int portNumber){
        this.serverPort = portNumber;
    }
    
    public Set<ChatThread> getClients(){
        return userThreads;
    }
    
    private void startServer(){
    	
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
            acceptClients(serverSocket);
        } catch (IOException e){
            System.err.println("port not available : "+serverPort);
            System.exit(1);
        }
    }
    
    private void acceptClients(ServerSocket serverSocket){

        System.out.println("done server start port = " + serverSocket.getLocalSocketAddress());
        while(true){
            try{
            	clientNumber++;
            	User user = new User(clientNumber);
                Socket socket = serverSocket.accept();
                System.out.println("accepts : " + socket.getRemoteSocketAddress());
                ChatThread client = new ChatThread(this, socket, user);
                Thread thread = new Thread(client);
                thread.start();
                addUserThread(client);
               
                
                System.out.println("total users : " + userThreads.size());
            } catch (IOException ex){
                System.out.println("Accept failed on : "+serverPort);
            }
        }
    }
    
    private void addUserThread(ChatThread client) {
    	 userThreads.add(client);
    	 users.put(client.getUser().getUsername().toLowerCase(), client);
	}

	public void broadcast(String message, ChatThread excludeUser,boolean all) {
		System.out.println(userThreads.size());
		for (ChatThread aUser : userThreads) {
			if (!(aUser == excludeUser && !all)) {
				aUser.sendMessage(excludeUser.getUser().getUsername() +  "> " + message);
			} 
		}
	}
	
	public void broadcastUser(ChatThread curr, String message) {
		curr.sendMessage(message);
	}
	
	public String showUserList(ChatThread current) {
		String list = getUsernameList(current);
		System.out.println(list + "< List");
		return list;
	}

	private String getUsernameList(ChatThread excludeUser) {
		StringBuilder b = new StringBuilder();
		
		for (ChatThread aUser : userThreads) {
			if (aUser != excludeUser) {
				if (b.length() > 0) {
					b.append(", ");
				}
				b.append(aUser.getUser().getUsername());
			}
		}
		if (b.length() == 0) {
			b.append("you are the only user");
		}
		return b.toString();
	}

	public void removeUser(ChatThread chatThread) {
		
		boolean  remove = userThreads.remove(chatThread);
		System.out.println(remove +  " " + chatThread.getUser().getUsername());
	}
	
	public int getClientCount() {
		return userThreads.size();
	}
	
	public boolean setPassword(ChatThread chatThread, String pwd) {
		chatThread.getUser().setPwd(pwd);
		return true;
	}
	
	public int setUserNick(ChatThread chatThread, String nick) {
		
		if (chatThread.getUser().getUsername().equalsIgnoreCase(nick)) {
			return 1;
		}
		if (users.containsKey(nick)) {
			ChatThread foundChatThread = users.get(nick);
			if (foundChatThread.getUser().getPwd() != null && !foundChatThread.getUser().getPwd().isEmpty()) {
				return 0;
			} else {
				users.remove(chatThread.getUser().getUsername().toLowerCase());
				chatThread.getUser().setUsername(nick);
				users.put(nick, chatThread);
				String newName = foundChatThread.getUser().getUsername()+"01";
				users.remove(foundChatThread.getUser().getUsername().toLowerCase());
				foundChatThread.getUser().setUsername(newName);
				users.put(newName, foundChatThread);
				
				broadcastUser(foundChatThread, "Your name was changed to > " + newName);
				broadcast(nick + " was changed to " + newName ,foundChatThread, false);
			}
		} else {
			String oldName = chatThread.getUser().getUsername();
			users.remove(chatThread.getUser().getUsername().toLowerCase());
			
			chatThread.getUser().setUsername(nick);
			users.put(nick, chatThread);
			
			broadcast(oldName + " changed to " + nick,chatThread, false);
		}
		
		
		return -1;
	}
}
