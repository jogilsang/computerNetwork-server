package kokoatalk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Enumeration;

public class server {

	public static String ServerIP, ClientMessage;
	public static ServerSocket serverSocket;
	public static int serverPort = 18888;
	public static DataOutputStream dos;
	public static DataInputStream dis;

	public static void main(String[] args) {

		// 서버 임을 알린다
		System.out.println("server");

		// 아이피를 얻는다
		ServerIP = getIpAddress();
		
		// 서버 아이피 표시
		System.out.println(ServerIP);

		// 서버의 쓰레드를 시작한다
		Thread socketServerThread = new Thread(new socketServerThread());
		socketServerThread.start();

	}

	// 서버 쓰레드
	static class socketServerThread extends Thread {
		public void run() {
			try {

				// 서버 소캣생성
				serverSocket = new ServerSocket(serverPort);
				while (true) {

					// accept 실행
					Socket socketAccept = serverSocket.accept();

					// 소켓 생성완료
					System.out.println("Socket Created");

					// dos : 클라이언트로부터 오는것
					// dis : 클라이언트로 보내는것
					dos = new DataOutputStream(new BufferedOutputStream(socketAccept.getOutputStream()));
					dis = new DataInputStream(new BufferedInputStream(socketAccept.getInputStream()));

					// 클라이언트에게 메세지받음
					final String Msg = dis.readUTF();
					
					// 클라이언트에게 받은 메세지 표시
					System.out.println("Client Message Received:" + Msg);
				
					
//					MainActivity.this.runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							
//						}
//					});
					
					// 클라이언트한테 메세지 보냄
					dos.writeUTF(getIpAddress() + " sends echo message " + "'" + Msg + "'");
					dos.flush();
				}
			} catch (SocketTimeoutException e) {
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static String getIpAddress() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();
					if (inetAddress.isSiteLocalAddress()) {
						ip += "IP address: " + inetAddress.getHostAddress() + "\n";
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}
		return ip;
	}

}
