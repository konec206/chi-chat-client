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
public interface ContactRequestInterface extends Remote {
    
    //Status
    public static int CONTACT_REQUEST_STATUS_SENT = 0;
    public static int CONTACT_REQUEST_STATUS_ACCEPTED = 1;
    public static int CONTACT_REQUEST_STATUS_REFUSED = 2;

    
    /**
     * 
     * @return 
     * @throws java.rmi.RemoteException 
     */
    public Date getDateCreated() throws RemoteException;
    
    /**
     * 
     * @param dateCreated
     * @throws java.rmi.RemoteException
     */
    public void setDateCreated(Date dateCreated) throws RemoteException;
    
    
    /**
     * 
     * @return 
     * @throws java.rmi.RemoteException 
     */
    public UserInterface getSender() throws RemoteException;
    
    /**
     * 
     * @return 
     * @throws java.rmi.RemoteException 
     */
    public UserInterface getReceiver()  throws RemoteException;
    
    /**
     * 
     * @param sender
     * @throws java.rmi.RemoteException
     */
    public void setSender(UserInterface sender)  throws RemoteException;
    
    /**
     * 
     * @param receiver
     * @throws java.rmi.RemoteException
     */
    public void setReceiver(UserInterface receiver)  throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public int getStatus() throws RemoteException;
    
    /**
     * 
     * @param status
     * @throws RemoteException 
     */
    public void setStatus(int status) throws RemoteException;
}
