/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ServerApp.ChatService;
import ServerApp.Listener;
import ServerApp.ListenerHelper;
import ServerApp.ListenerImpl;
import ServerApp.UserService;
import chichatclient.ChiChatClient;
import interfaces.UserInterface;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/**
 *
 * @author thibault
 */
public class ClientService implements Runnable {

    private UserService userService;
    private ChatService chatService;

    private String usernameConnected = "";
    private String chatId = "";

    private UserInterface userConnected = null;

    private Listener listener;

    private ORB orb;

    public ClientService(UserService userService, ChatService chatService, ORB orb) {
        this.userService = userService;
        this.chatService = chatService;
        this.orb = orb;
    }

    @Override
    public void run() {
        try {
            //Instantiate Servant and create reference
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            ListenerImpl listenerImpl = new ListenerImpl();
            Listener ref = null;
            try {
                rootPOA.activate_object(listenerImpl);
                try {
                    ref = ListenerHelper.narrow(rootPOA.servant_to_reference(listenerImpl));

                    rootPOA.the_POAManager().activate();

                    //The listener of the client is active
                    this.listener = ref;

                    orb.run();

                } catch (ServantNotActive ex) {
                    Logger.getLogger(ChiChatClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AdapterInactive ex) {
                    Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ServantAlreadyActive | WrongPolicy ex) {
                Logger.getLogger(ChiChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (InvalidName ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void menu() {
        String str = "";
        String quitCmd = "quit";

        System.out.println("####################");
        System.out.println("Welcome to the chat!");
        System.out.println("####################");

        System.out.println("####################");
        System.out.println("To get some help type /help");
        System.out.println("####################");

        do {
            this.parseCommand();

        } while (!str.toLowerCase().equals(quitCmd.toLowerCase()));
    }

    private void parseCommand() {
        Scanner sc = new Scanner(System.in);
        System.out.print(">> ");
        String command = sc.nextLine();

        switch (command.toLowerCase()) {
            case "/help":
                displayHelp();
                break;
            case "/private":
                if (!usernameConnected.equals("")) {
                    chat(false, true);
                } else {
                    System.out.println("You must be connected : type /connect or /register");
                }
                break;
            case "/public":
                if (!usernameConnected.equals("")) {
                    chat(true, true);
                } else {
                    System.out.println("You must be connected : type /connect or /register");
                }
                break;
            case "/contactsrequest":
                if (!usernameConnected.equals("")) {
                    answerToContactRequests();
                } else {
                    System.out.println("You must be connected : type /connect or /register");
                }
                break;
            case "/contact":
                if (!usernameConnected.equals("")) {
                    contacts();
                } else {
                    System.out.println("You must be connected : type /connect or /register");
                }
                break;
            case "/connect":
                if (usernameConnected.equals("")) {
                    connect();
                } else {
                    System.out.println("You are already connected : type /disconnect or /quit");
                }
                break;
            case "/disconnect":
                if (!usernameConnected.equals("")) {
                    usernameConnected = "";
                    System.out.println("Successfully disconnected");
                } else {
                    System.out.println("You are not connected : type /connect or /register");
                }
                break;
            case "/register":
                if (usernameConnected.equals("")) {
                    register();
                } else {
                    System.out.println("You are already connected : type /disconnect or /quit");
                }
                break;
            case "/quit":
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Unrecognized command : " + command);
                break;
        }
    }

    public void chat(boolean isPublic, boolean join) {
        Scanner sc = new Scanner(System.in);

        if (join) {
            System.out.println("\n####################");
            System.out.println("New chat! To quit tape /quit");
            System.out.println("####################");

            this.chooseChat(isPublic);

            if (chatId != "") {
                String message = "";
                do {
                    System.out.print("Enter your message\n>> ");
                    message = sc.nextLine();

                    chatService.sendMessage(chatId, usernameConnected, message);
                } while (!message.toLowerCase().equals("/quit"));

                this.chatService.disconnect(usernameConnected, chatId, this.listener);

                if (!isPublic)
                    System.out.println("You disconnected from the chat");
            }
        } else {
            chatId = chatService.newChat(usernameConnected, "", isPublic, this.listener);
        }
    }

    private void contacts() {
        System.out.println("Contacts :");
        String display = this.userService.getContactsDisplay(usernameConnected);
        if (display.equals("")) {
            System.out.println("You don't have any contact");
        } else {
            System.out.println(display);
        }
    }

    private void answerToContactRequests() {
        Scanner sc = new Scanner(System.in);

        String answer = "";

        String requString = "";

        do {
            requString = this.userService.getLastContactRequests(usernameConnected);

            if (requString.equals("")) {
                System.out.println("You don't have any contact request");
                return;
            }

            System.out.println("You have a new request :");
            System.out.println(requString);
            do {
                System.out.print("(accept/decline/skip)\n>> ");

                answer = sc.nextLine();
            } while (!answer.toLowerCase().equals("accept") && !answer.toLowerCase().equals("decline") && !answer.toLowerCase().equals("skip"));

            if (answer.toLowerCase().equals("accept")) {
                this.userService.answerToContactRequest(usernameConnected, true);
            } else if (answer.toLowerCase().equals("decline")) {
                this.userService.answerToContactRequest(usernameConnected, false);
            }

        } while (!requString.equals(""));

    }

    private void chooseChat(boolean isPublic) {
        Scanner sc = new Scanner(System.in);

        String username;
        do {
            System.out.print("Who do you want to chat with? \n>> ");
            username = sc.nextLine();
        } while (username.equals(""));

        chatId = chatService.newChat(usernameConnected, username, isPublic, this.listener);

        if (chatId.equals("UserNotFound")) {
            chatId = "";
            System.out.println(username + " is not in your contact list");
            chooseSendRequest(username);
            System.out.println("Exiting...");
            return;
        }

        if (chatId.equals("")) {
            System.out.println("User not found");
        }
    }

    private void chooseSendRequest(String username) {
        Scanner sc = new Scanner(System.in);

        String yesAnswer = "yes";
        String noAnswer = "no";

        System.out.println("Do you want to send a contact request ?");

        String response = "";

        do {
            System.out.print("(yes/no)\n>> ");
            response = sc.nextLine();
            if (!response.toLowerCase().equals(yesAnswer) && !response.toLowerCase().equals(noAnswer)) {
                response = "";
            }
        } while (response.equals(""));

        if (response.toLowerCase().equals(yesAnswer)) {
            this.userService.newContactRequest(usernameConnected, username);
            System.out.println("Request sent!");
        }
    }

    private void displayHelp() {
        System.out.println("\n####################");
        System.out.println("Here are the chat commands :");
        System.out.println("/private -> Create or join a chat with only one user");
        System.out.println("/public -> Join the general chat of the user you want to talk");
        System.out.println("/contactsrequest -> Let you answer to contact requests");
        System.out.println("/contacts -> Display all your contacts");
        System.out.println("/connect -> Connect to the chat service");
        System.out.println("/register -> Register to the chat service");
        System.out.println("/disconnect -> Disconnect from the chat service");
        System.out.println("/quit -> Shutdown the chat service");
        System.out.println("####################");

    }

    public void connect() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n####################");
        System.out.println("Connection :");
        System.out.println("####################");

        String username = "";
        String password = "";

        do {
            if (!username.equals("")) {
                System.out.println("Wrong credentials");
            }
            System.out.print("Username : \n> ");
            username = sc.nextLine();
            System.out.print("Password : \n> ");
            password = sc.nextLine();
        } while (!userService.authenticate(username, password));

        usernameConnected = username;

        Thread thread = new Thread(this);
        thread.start();

        System.out.println("Connecting...");
        while (this.listener == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.chat(true, false);

        System.out.println("Successfully connected!");
    }

    public void register() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n####################");
        System.out.println("Registration :");
        System.out.println("####################");

        String username = "";
        String password = "";
        String name = "";
        String firstname = "";

        String result = " ";
        
        do {
            if (result.equals("")) {
                System.out.println("This username is already taken");
            }
            System.out.print("Username : \n> ");
            username = sc.nextLine();
            System.out.print("Password : \n> ");
            password = sc.nextLine();
            System.out.print("Name : \n> ");
            name = sc.nextLine();
            System.out.print("Firstname : \n> ");
            firstname = sc.nextLine();
            
            result = userService.register(username, password, name, firstname);
        } while (result.equals(""));

        usernameConnected = username;

        Thread thread = new Thread(this);
        thread.start();

        System.out.println("Connecting...");
        while (this.listener == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.chat(true, false);

        System.out.println("Successfully registered!");
    }
}
