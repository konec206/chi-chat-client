package ServerApp;


/**
* ServerApp/ListenerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Server.idl
* jeudi 22 mars 2018 09 h 04 CET
*/

public interface ListenerOperations 
{
  void onMessageReceived (String message);
  void onUserDisconnected (String message);
} // interface ListenerOperations
