package thrift.service.impl;
import org.apache.thrift.TException;
import thrift.service.tutorial.*;
public
class MultiplicationHandler implements Service1.Iface {
  public
  int multiply(int n1, int n2)
  throws TException {
    System.out.println("Multiplication in MultiplicationHandler for " + n1 + ", " + n2);
    return n1 * n2;
  }
}
