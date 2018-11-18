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

		// 서버 임을 알린다
		System.out.println("클라이언트 A를 실행시킵니다");
		System.out.println("서버와 접속합니다...");
		System.out.println("채팅을 입력해주세요.");

		while (true) {
			// 입력값을 받아서 진행시킨다
			Scanner sc = new Scanner(System.in);
			String chat = sc.next();
			// System.out.println(input);

			// 채팅한번 칠때마다 소켓연결
			startTCP_Client(chat);

		}

		// while true를 넣어서 데이터넣자

	}

	public static void startTCP_Client(String chat) {
		Thread startClientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 로컬 호스트 ip를 집어넣는다
					final InetAddress destIPAddress = InetAddress.getByName("127.0.0.1");

					// 서버ip와 포트로 연결
					clientSocket = new Socket(destIPAddress, severPort);

					// 소켓생성 완료
					System.out.println("Socket Created");

					// 채팅친걸 가져온다
					MessageInput = chat;

					dos = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
					dis = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

					// 서버로 채팅내역을 보낸다
					dos.writeUTF(MessageInput);
					dos.flush();

					// 서버로 부터 받아온 채팅내역을 읽는다.
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
