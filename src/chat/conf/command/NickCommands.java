package chat.conf.command;

import chat.conf.server.ChatThread;
import chat.conf.server.Commands;

public class NickCommands implements Commands {

	@Override
	public void runCommand(ChatThread chatObj, String input) {

		// plain message
		chatObj.changeNick(input);
		// print.println(input.toUpperCase());
		System.out.println(" NickCommands Done");

	}
}
