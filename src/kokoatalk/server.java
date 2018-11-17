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

		// ���� ���� �˸���
		System.out.println("server");

		// �����Ǹ� ��´�
		ServerIP = getIpAddress();
		
		// ���� ������ ǥ��
		System.out.println(ServerIP);

		// ������ �����带 �����Ѵ�
		Thread socketServerThread = new Thread(new socketServerThread());
		socketServerThread.start();

	}

	// ���� ������
	static class socketServerThread extends Thread {
		public void run() {
			try {

				// ���� ��Ĺ����
				serverSocket = new ServerSocket(serverPort);
				while (true) {

					// accept ����
					Socket socketAccept = serverSocket.accept();

					// ���� �����Ϸ�
					System.out.println("Socket Created");

					// dos : Ŭ���̾�Ʈ�κ��� ���°�
					// dis : Ŭ���̾�Ʈ�� �����°�
					dos = new DataOutputStream(new BufferedOutputStream(socketAccept.getOutputStream()));
					dis = new DataInputStream(new BufferedInputStream(socketAccept.getInputStream()));

					// Ŭ���̾�Ʈ���� �޼�������
					final String Msg = dis.readUTF();
					
					// Ŭ���̾�Ʈ���� ���� �޼��� ǥ��
					System.out.println("Client Message Received:" + Msg);
				
					
//					MainActivity.this.runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							
//						}
//					});
					
					// Ŭ���̾�Ʈ���� �޼��� ����
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
