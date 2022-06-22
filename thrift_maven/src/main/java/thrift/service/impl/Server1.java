package thrift.service.impl;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import thrift.service.tutorial.Message;
import thrift.service.tutorial.Service1;
import thrift.service.tutorial.Service2;
import thrift.service.tutorial.Service2.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public
class Server1 {
  public static
  void main(String[] args)
  throws TTransportException {
    MultiplicationHandler handler = new MultiplicationHandler();
    Service1.Processor processor = new Service1.Processor(handler);
    TServerTransport serverTransport = new TServerSocket(9090);
    final TServer server =
        new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

    requestAddition();
    Thread serverThread = new Thread(new Runnable() {
      public
      void run() {
        System.out.println("Starting server for 1");
        server.serve();
      }
    });
    serverThread.start();
  }

  private static void requestAddition()
  throws TTransportException {
    TTransport transport = new TSocket("localhost", 9091);
    TProtocol protocol = new TBinaryProtocol(transport);
    final Service2.Client client = new Client(protocol);
    transport.open();
    Runnable r = new Runnable() {
      public
      void run() {
        while(true) {
          BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

          System.out.println("Enter X: ");
          try {
            int number1 = Integer.parseInt(reader.readLine());
            System.out.println("Enter Y: ");
            int number2 = Integer.parseInt(reader.readLine());
            int result = client.add(number1, number2);
            System.out.println("Result is : " + result);
          } catch (IOException e) {
            System.err.println("Unable to read from command prompt");
          } catch (TException e) {
            System.err.println("Unable to communicate to server");
          }
        }
      }
    };
    Thread requesterThread = new Thread(r);
    requesterThread.start();
  }
}

class Server1Handler implements Message.Iface {
  private static int count = 0;
  public String motd() {
    return "From service 1, message" + count++ ;
  }
}
