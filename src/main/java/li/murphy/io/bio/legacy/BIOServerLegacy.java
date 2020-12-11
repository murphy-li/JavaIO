package li.murphy.io.bio.legacy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static li.murphy.io.bio.ServerPort.SERVER_PORT;

public class BIOServerLegacy {
    public static void main(String[] args) throws IOException {
        // 绑定本地端口
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        while(true) {
            // accept阻塞方法，接收新的socket连接
            final Socket clientSocket = serverSocket.accept();
            // 开启新的线程处理业务逻辑（不开启线程，服务器一次最多只能处理一个请求）
            new BusinessLogic(clientSocket).start();
        }
    }


    /**
     * 业务逻辑类
     */
    static class BusinessLogic extends Thread{

        // 持有的socket
        private final Socket clientSocket;

        public BusinessLogic(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                // 从socket获取reader
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // 从socket获取writer
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                // 不间断从reader中获取新的输入并返回对应输出。
                String input = null;
                while(true){
                    if((input = reader.readLine()) != null){
                        System.err.println("客户端说：" + input);
                        writer.println("收到！内容是《" + input + "》");
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                // 错误处理
            } finally {
                // 实际使用记得关闭对应的资源
            }
        }
    }
}
