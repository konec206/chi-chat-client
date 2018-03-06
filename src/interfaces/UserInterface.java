/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.ArrayList;

/**
 *
 * @author thibault
 */
public interface UserInterface {
    /**
     * @return String
     */
    public String getUsername();
    
    /**
     * @return String
     */
    public String getName();
    
    /**
     * @return String
     */
    public String getFirstName();
    
    /**
     * @return 
     */
    public String getPassword();
    
    /**
     * @param username
     * @param plainPassword
     * @return boolean
     */
    public boolean authenticate(String username, String plainPassword);
    
    /**
     * @return ArrayList
     */
    public ArrayList<UserInterface> getContacts();
    
    /**
     * @param contact 
     */
    public void addContact(UserInterface contact);
    
    /**
     * @param contact 
     */
    public void removeContact(UserInterface contact);
}
