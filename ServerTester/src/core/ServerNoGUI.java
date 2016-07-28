package core;

import tcpserver.ServerSocketHandler;

public class ServerNoGUI {
	public static void main(String[] args) {
		ServerSocketHandler serverSocketHandler = new ServerSocketHandler(5004,60);
	}
}
