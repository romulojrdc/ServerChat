package chat.conf.command;

import chat.conf.server.ChatThread;
import chat.conf.server.Commands;

public abstract class AbstractCommand {
	
	 public static void execute(ChatThread hdlr, String input) {
		 
		 // TODO create replication class for call each one no more switch
		 String command = "/say";
		 String msg = null;
		 if(input.startsWith("/")) {
         	String[] args = input.split(" ");
         	if (args.length > 1) {
         		command = args[0].toLowerCase();
         		msg = input.replace(command, "").trim();
         	} else {
         		command = "/invalid";
         	}
         } else {
        	 msg = input;
         }
         System.out.println("command > " + command);
		 Commands commands = null;
		 switch (command) {
		 		
		 	case Commands.SAY : 
		 		System.out.println("entering say" );
		 		commands = new SayCommands();
		 		break;
		 	case Commands.NICK : 
		 		System.out.println("entering nick" );
		 		commands = new NickCommands();
		 		break;
		 	case Commands.REG : 
		 		System.out.println("entering nick" );
		 		commands = new RegisterCommands();
		 		break;
		 	default : 
		 		commands = new InvalidCommands();
		 		
		 }
		 
		 commands.runCommand(hdlr, msg);
			 
	 }
}
