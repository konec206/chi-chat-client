/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 *
 * @author thibault
 */
public interface MessageInterface extends Remote {
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public UserInterface getSender() throws RemoteException;
    
    /**
     * 
     * @param sender
     * @throws RemoteException 
     */
    public void setSender(UserInterface sender) throws RemoteException;
    
    /**
     * 
     * @param date
     * @throws RemoteException 
     */
    public void setDate(Date date) throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public Date getDate() throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public String getContent() throws RemoteException;
    
    /**
     * 
     * @param content
     * @throws RemoteException 
     */
    public void setContent(String content) throws RemoteException;
}
