package ServerApp;

/**
* ServerApp/UserServiceHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Server.idl
* jeudi 22 mars 2018 09 h 04 CET
*/

public final class UserServiceHolder implements org.omg.CORBA.portable.Streamable
{
  public ServerApp.UserService value = null;

  public UserServiceHolder ()
  {
  }

  public UserServiceHolder (ServerApp.UserService initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ServerApp.UserServiceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ServerApp.UserServiceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ServerApp.UserServiceHelper.type ();
  }

}