import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class WriteTh extends Thread {
	private PrintWriter out;// 소켓에 출력 스트림
	private Scanner sc; // 키보드 입력

	public WriteTh(PrintWriter out, Scanner sc) {
		this.out = out;
		this.sc = sc;
	}

	@Override
	public void run() {
		System.out.print("사용할 닉네임 입력");
		String name = sc.nextLine();
		// 닉네임 서버 전송
		out.println(name);
		out.flush();

		while (true) {
			// 키보드 입력
			String str = sc.nextLine();
			// 소켓에 출력 => 상대방에게 전송
			out.println(str);
			out.flush();// 버퍼 강제 출력
			if (str.startsWith("/stop")) {// 종료 메시지
				break;// 쓰레드 종료
			}
		}
	}

}

//상대방 메시지 읽는 쓰레드
class ReadTh extends Thread {
	private BufferedReader in;// 소켓 읽기 스트림

	public ReadTh(BufferedReader in) {
		this.in = in;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 소켓에서 한줄 읽음
				String str = in.readLine();
				if (str.startsWith("/stop")) {// 메시지 내용이 /stop이면
					System.out.println("채팅을 종료합니다");
					break;// 쓰레드 종료
				}
				System.out.println(str);
			} catch (IOException e) {
				System.out.println("채팅을 종료합니다.");
				break;
			}
		}
	}

}

public class ChatClientMain {

	public static void main(String[] args) {
		try {
			// 서버연결
			Socket socket = new Socket("localhost", 5001);

			// 소켓 입출력 스트림 생성
			BufferedReader in = 
					new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream());

			// 상대방 메시지 읽는 쓰레드 생성
			ReadTh th1 = new ReadTh(in);

			// 키보드 입력받아서 소켓에 출력하는 쓰레드 생성
			WriteTh th2 = new WriteTh(out, new Scanner(System.in));

			// 쓰레드 실행
			th1.start();
			th2.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
