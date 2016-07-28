package tcpserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketDataHandler extends Thread {
	private final String notation = "[DataHandler]: ";
	
	private Socket socket;
	private InputStream in = null;
	
	public SocketDataHandler() {
		// TODO Auto-generated constructor stub
	}

	public SocketDataHandler(Socket socket) {
		this.socket = socket;
		try {
			in= socket.getInputStream();
		} catch (IOException e) {
			System.err.println(notation + "Get socket inputStream Failed.");
		}
	}
	
	@Override
	public void run() {
		super.run();
		
		int newData;
		
		try {
			while (((newData = in.read()) != -1)) {
				System.out.print((char)newData);
				
			}
		} catch (IOException e) {
			System.out.println(notation + this.getName() + " Exit with Exception/tiemout.");
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// 线程退出提示
		System.out.println(notation +" Socket " + this.getName() + " Exit.");
		try {
			// 对方主动停止会引起包装流close socket
			if (socket.isClosed() == false)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//end of run
}