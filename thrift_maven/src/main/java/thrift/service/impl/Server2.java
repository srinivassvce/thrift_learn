package thrift.service.impl;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import thrift.service.tutorial.Service1;
import thrift.service.tutorial.Service2;

public
class Server2 {
  public static
  void main(String[] args)
  throws TException {
    TTransport transport = new TSocket("localhost", 9090);
    TProtocol protocol = new TBinaryProtocol(transport);
    transport.open();
    Service1.Client multiplierClient = new Service1.Client(protocol);
    Runnable runnable = new RequestNumbers(multiplierClient);
    Thread t1 = new Thread(runnable);
    t1.start();

    createAddService();
  }

  private static
  void createAddService()
  throws TTransportException {
    AddHandler handler = new AddHandler();
    Service2.Processor processor = new Service2.Processor(handler);
    TServerTransport transport = new TServerSocket(9091);
    final TServer server = new TSimpleServer(new Args(transport).processor(processor));

    Thread serverThread = new Thread(new Runnable() {
      public
      void run() {
        System.out.println("Starting server for 2");
        server.serve();
      }
    });
    serverThread.start();
  }
}
