/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerApp;

/**
 *
 * @author thibault
 */
public class ListenerImpl extends ListenerPOA {

    @Override
    public void onMessageReceived(String message) {
        System.out.println("[ALERT] " + message);
    }

    @Override
    public void onUserDisconnected(String message) {
        System.out.println("[ALERT] " + message);
    }
}
