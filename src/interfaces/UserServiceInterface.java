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
public interface UserServiceInterface extends Remote {
    
    /**
     * 
     * @param users
     * @throws RemoteException 
     */
    public void initUserRepository(ArrayList<UserInterface> users) throws RemoteException;

    /**
     * 
     * @param username
     * @param plainPassword
     * @return
     * @throws RemoteException 
     */
    public boolean authenticateUser(String username, String plainPassword) throws RemoteException;
    
    /**
     * 
     * @param request 
     * @throws RemoteException
     * @throws Exception 
     */
    public void sendContactRequest(ContactRequestInterface request) throws RemoteException, Exception;
    
    /**
     * 
     * @param userName
     * @return 
     * @throws java.rmi.RemoteException 
     */
    public UserInterface getUser(String userName) throws RemoteException, Exception;
    
    /**
     * 
     * @param answer
     * @param request
     * @return 
     * @throws RemoteException 
     */
    public UserInterface answerToContactRequest(boolean answer, ContactRequestInterface request) throws RemoteException;
    
    /**
     * 
     * @param user
     * @return
     * @throws RemoteException 
     */
    public UserInterface createUser(UserInterface user) throws RemoteException, Exception;
}
