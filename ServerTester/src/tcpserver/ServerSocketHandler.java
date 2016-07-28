package tcpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket端口监听器，派发socket字节流数据处理线程
 * 
 * @author TS
 *
 */
public class ServerSocketHandler {
	private final String notation = "[SrvSocketHandler]: ";
	private ServerSocket serverSocket;

	private int listenPort;
	private int sockTimeOut;

	public ServerSocketHandler() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 启动端口socket监听
	 * @param listenPort 端口号
	 * @param timeOut	socket无活动断开时间(>0时有效)
	 */
	public ServerSocketHandler(int listenPort, int timeOut) {
		init();
		this.listenPort = listenPort;
		this.sockTimeOut = timeOut;
		// 开启监听
		try {
			serverSocket = new ServerSocket(listenPort);
		} catch (IOException e) {
			System.err.println(notation + "Server start listen fail");
			e.printStackTrace();
		}
		//启动监听线程
		new SrvSockHandler().run();
	}

	/*
	 * 私有方法
	 */
	private void init() {
		serverSocket = null;
		listenPort = 0;
		sockTimeOut = 0;
	}
	
	/**
	 * 监听线程实现类，收到socket request后accept并派发。
	 * @author TS
	 *
	 */
	private class SrvSockHandler extends Thread {
		Socket socket;

		public SrvSockHandler() {
			socket = null;
			System.out.println(notation + "start listen on: " + listenPort);

		}

		@Override
		public void run() {
			super.run();
			/* dispatch handler */
			while (serverSocket.isClosed() == false) {
				try {
					socket = serverSocket.accept();
					if (sockTimeOut > 0) {
						socket.setSoTimeout(1000 * sockTimeOut);
					}
					System.out.println(notation + "Server accepted a socket");
				} catch (IOException e) {
					System.out.println(notation + "socket accept fail");
					e.printStackTrace();
				}
				
				// show content
				SocketDataHandler socketDataHandler = new SocketDataHandler(socket);
				socketDataHandler.run();
			}
		}
	}
}
