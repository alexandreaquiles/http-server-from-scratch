import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler implements Runnable {

  private final Socket clientSocket;

  public RequestHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    PrintStream printStreamClientSocketOutputStream = null;
    try (
        InputStream clientSocketInputStream = clientSocket.getInputStream();
        Scanner scannerClientSocketInputStream = new Scanner(clientSocketInputStream);
        OutputStream clientSocketOutputStream = clientSocket.getOutputStream();
    ) {

      printStreamClientSocketOutputStream = new PrintStream(clientSocketOutputStream);

      if (scannerClientSocketInputStream.hasNextLine()) {
        String startLine = scannerClientSocketInputStream.nextLine();
        String[] startLineTokens = startLine.split(" ");
        if ("GET".equals(startLineTokens[0])) {
          printStreamClientSocketOutputStream.println("HTTP/1.1 200 OK");
          printStreamClientSocketOutputStream.println("");
          printStreamClientSocketOutputStream.println("Oi!");
        }
      }

    } catch (IOException ex) {
      if (printStreamClientSocketOutputStream != null) {
        printStreamClientSocketOutputStream.println("HTTP/1.1 500 Internal Server Error");
        printStreamClientSocketOutputStream.println("");
        printStreamClientSocketOutputStream.println("Bad, bad server! Not donut for you! :(");
      }
    } finally {
      if (printStreamClientSocketOutputStream != null) printStreamClientSocketOutputStream.close();
    }
  }
}
