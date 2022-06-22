package thrift.service.impl;

import org.apache.thrift.TException;
import thrift.service.tutorial.Message;

public
class MesssageHandler1 implements Message.Iface{
  private static int count;
  public
  String motd()
  throws TException {
    return "For server 1, message count: " + count++;
  }
}
