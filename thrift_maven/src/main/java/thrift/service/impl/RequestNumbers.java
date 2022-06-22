package thrift.service.impl;

import org.apache.thrift.TException;
import thrift.service.tutorial.Service1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public
class RequestNumbers implements Runnable {
  Service1.Client client;

  public
  RequestNumbers(Service1.Client client) {
    this.client = client;
  }

  public
  void run() {
    while (true) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      System.out.println("Enter X: ");
      try {
        int number1 = Integer.parseInt(reader.readLine());
        System.out.println("Enter Y: ");
        int number2 = Integer.parseInt(reader.readLine());
        int result = client.multiply(number1, number2);
        System.out.println("Result is : " + result);
      } catch (IOException e) {
        System.err.println("Unable to read from command prompt");
      } catch (TException e) {
        System.err.println("Unable to communicate to server");
      }
    }
  }
}
