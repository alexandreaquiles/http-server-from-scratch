import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

  public static void main(String[] args) throws IOException {

    ExecutorService executorService = Executors.newFixedThreadPool(100);

    ServerSocket serverSocket = new ServerSocket(8080);

    while (true) {
      Socket clientSocket = serverSocket.accept();
      RequestHandler requestHandler = new RequestHandler(clientSocket);
      executorService.execute(requestHandler);
    }

  }

}
