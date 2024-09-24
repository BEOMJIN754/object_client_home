package mainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

import valueObject.VUser;

public class Main {
    // 둘 다 자식임
    private PLoginDialog pLoginDialog;
    private PMainFrame pMainFrame;
    
    private Socket socket; // 소켓 추가
    private static final String SERVER_ADDRESS = "localhost"; // 서버 주소
    private static final int PORT = 12345; // 서버 포트 번호

    public Main() {
        this.pLoginDialog = new PLoginDialog(new ActionHandler());
    }

    private void intialize() {
        this.pLoginDialog.initialize();
    }

    private void validateUser(Object source) throws ClassNotFoundException {
        VUser vUser = this.pLoginDialog.validateUser(source);
        if (vUser != null) {
            try {
                // 서버에 연결 시도
                this.socket = new Socket(SERVER_ADDRESS, PORT);
                System.out.println("서버와 연결되었습니다."); // 서버와 연결되었을 때 콘솔 메시지

                // 서버로부터 데이터 수신
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Vector<String> data = (Vector<String>) in.readObject();
                //print client data
                for(int i=0;i<data.size();i++) {
                System.out.println("client::"+data.get(i));
                }
                this.pMainFrame = new PMainFrame();
                this.pMainFrame.initialize(vUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.pLoginDialog.dispose();
    }

    // LoginDialog "OK" and "Cancel" Button Event Handler
    public class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
				validateUser(event.getSource());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.intialize();
    }
}
