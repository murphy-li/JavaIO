package li.murphy.io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static li.murphy.io.nio.NIOServerPort.SERVER_PORT;

public class BIOClient {
    public static void main(String[] args) {
        try {
            // 创建一个到 127.0.0.1:port的socket连接
            Socket socket = new Socket("127.0.0.1", SERVER_PORT);
            // 从socket获取reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 从socket获取writer
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            // 不间断发送数据给服务器，然后从服务器读取对应的数据。
            Scanner scanner = new Scanner(System.in);
            while(true) {
                // 从控制台获取输入并发送给服务器
                writer.println(scanner.nextLine());
                writer.flush();
                // 从服务器获取输出并打印出来
                String answer = reader.readLine();  //没有内容会阻塞
                System.out.println("服务器说：" + answer);
            }
        } catch (IOException e) {
            // 错误处理
        } finally {
            // 实际使用记得关闭对应的资源
        }
    }
}
