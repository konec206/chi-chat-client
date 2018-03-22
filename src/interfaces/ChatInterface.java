/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author thibault
 */
public interface ChatInterface extends Remote {
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public ArrayList<UserInterface> getUsers() throws RemoteException;
    
    /**
     * 
     * @param user
     * @throws RemoteException 
     */
    public void addUser(UserInterface user) throws RemoteException;
    
    /**
     * 
     * @param user
     * @throws RemoteException 
     */
    public void removeUser(UserInterface user) throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public ArrayList<MessageInterface> getMessages() throws RemoteException;
    
    /**
     * 
     * @param message
     * @throws RemoteException 
     */
    public void addMessage(MessageInterface message) throws RemoteException;
    
    /**
     * 
     * @param user
     * @return
     * @throws RemoteException 
     */
    public boolean join(UserInterface user) throws RemoteException;
    
    /**
     * 
     * @param user
     * @return
     * @throws RemoteException 
     */
    public boolean quit(UserInterface user) throws RemoteException;
}
