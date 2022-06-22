package thrift.service.impl;

import thrift.service.tutorial.Service2;

public
class AddHandler implements Service2.Iface {
  public int add(int n1, int n2) {
    System.out.println("Calling add on service 2");
    return n1 + n2;
  }
}
