package chat.conf.command;

import chat.conf.server.ChatThread;
import chat.conf.server.Commands;

public class InvalidCommands implements Commands {

	@Override
	public void runCommand(ChatThread chatObj, String input) {

		// plain message
		chatObj.sendMessage("Invalid command!");
		// print.println(input.toUpperCase());
		System.out.println(" InvalidCommands Done");

	}
}
