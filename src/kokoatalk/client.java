package kokoatalk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class client {

	private static int severPort = 15555;
	private static Socket clientSocket;
	private static String MessageInput;
	private static DataOutputStream dos;
	private static DataInputStream dis;

	public static void main(String[] args) throws IOException {

		// ���� ���� �˸���
		System.out.println("Ŭ���̾�Ʈ A�� �����ŵ�ϴ�");
		System.out.println("������ �����մϴ�...");
		System.out.println("ä���� �Է����ּ���.");

		while (true) {
			// �Է°��� �޾Ƽ� �����Ų��
			Scanner sc = new Scanner(System.in);
			String chat = sc.next();
			// System.out.println(input);

			// ä���ѹ� ĥ������ ���Ͽ���
			startTCP_Client(chat);

		}

		// while true�� �־ �����ͳ���

	}

	public static void startTCP_Client(String chat) {
		Thread startClientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// ���� ȣ��Ʈ ip�� ����ִ´�
					final InetAddress destIPAddress = InetAddress.getByName("127.0.0.1");

					// ����ip�� ��Ʈ�� ����
					clientSocket = new Socket(destIPAddress, severPort);

					// ���ϻ��� �Ϸ�
					System.out.println("Socket Created");

					// ä��ģ�� �����´�
					MessageInput = chat;

					dos = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
					dis = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

					// ������ ä�ó����� ������
					dos.writeUTF(MessageInput);
					dos.flush();

					// ������ ���� �޾ƿ� ä�ó����� �д´�.
					final String echoMsg = dis.readUTF();
					System.out.println(echoMsg);

					System.out.println("Packet Send");
				} catch (SocketException e) {
					System.out.println("Sender SocketException");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		startClientThread.start();
	}

}

// editDSTIP = (EditText)findViewById(R.id.editDSTIP);
// editMsg = (EditText)findViewById(R.id.editMsg);
// buttonSend = (Button)findViewById(R.id.buttonSend);
// TextResponse = (TextView)findViewById(R.id.TextResponse);
// buttonSend.setOnClickListener(startSend);
//
