package chat.conf.command;

import chat.conf.server.ChatThread;
import chat.conf.server.Commands;

public class SayCommands implements Commands {

	
	@Override
	public void runCommand(ChatThread chatObj, String input) {

		 // plain message
		chatObj.broadcast(input);
		chatObj.broadcastSelf(input,false);
		//chatObj.broadcast(" second broadcast > " + input);
        //print.println(input.toUpperCase());
        System.out.println(" SayCommands Done BroadCast");
		
	}

}
