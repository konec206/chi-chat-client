package ServerApp;

/**
* ServerApp/ListenerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Server.idl
* jeudi 22 mars 2018 09 h 04 CET
*/

public final class ListenerHolder implements org.omg.CORBA.portable.Streamable
{
  public ServerApp.Listener value = null;

  public ListenerHolder ()
  {
  }

  public ListenerHolder (ServerApp.Listener initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ServerApp.ListenerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ServerApp.ListenerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ServerApp.ListenerHelper.type ();
  }

}
