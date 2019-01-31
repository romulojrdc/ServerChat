package chat.conf.command;

import chat.conf.server.ChatThread;
import chat.conf.server.Commands;

public class RegisterCommands implements Commands {

	@Override
	public void runCommand(ChatThread chatObj, String input) {

		// plain message
		chatObj.register(input);
		chatObj.broadcastSelf("Password registered!",true);
		// print.println(input.toUpperCase());
		System.out.println(" RegisterCommands Done");

	}
}
