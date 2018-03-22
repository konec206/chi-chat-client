/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chichatclient;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import ServerApp.UserService;
import ServerApp.UserServiceHelper;
import ServerApp.ChatService;
import ServerApp.ChatServiceHelper;
import ServerApp.ListenerImpl;
import ServerApp.Listener;
import ServerApp.ListenerHelper;
import interfaces.UserInterface;
import java.util.Scanner;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import services.ClientService;

/**
 *
 * @author thibault
 */
public class ChiChatClient {

    private static UserService userService;
    private static ChatService chatService;

    private static String chatId = "";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AdapterInactive {

        try {
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "2000");
            props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
            // Création de la couche ORB 
            // pour communiquer via un bus CORBA
            ORB orb = ORB.init((String[]) null, props);

            org.omg.CORBA.Object serviceNommageReference;
            serviceNommageReference = orb.resolve_initial_references("NameService");

            NamingContextExt serviceNommage = NamingContextExtHelper.narrow(serviceNommageReference);

            try {
                org.omg.CORBA.Object chatServiceRef;
                chatServiceRef = serviceNommage.resolve_str("ChatService");
                chatService = ChatServiceHelper.narrow(chatServiceRef);

                org.omg.CORBA.Object userServiceRef;
                userServiceRef = serviceNommage.resolve_str("UserService");
                userService = UserServiceHelper.narrow(userServiceRef);

                ClientService client = new ClientService(userService, chatService, orb);
                                
                client.menu();
                
            } catch (NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName ex) {
                Logger.getLogger(ChiChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (InvalidName ex) {
            Logger.getLogger(ChiChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
