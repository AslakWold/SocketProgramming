package com.company;

import java.net.*;
import java.io.*;
import java.io.IOException;

public class EchoUcaseServerTCP {

    public static void main(String[] args) throws IOException {
        int portNumber = 5555; //Default port to use

        if(args.length > 0 ) {
            if(args.length == 1) {
                portNumber = Integer.parseInt(args[0]);
            } else {
                System.err.println("Usage: java EchoUcaseServerTCP [<port number>]");
                System.exit(1);
            }
        }

        System.out.println("Hi, I am EchoUcase TCP server");

        //try () with resource makes sure that all the resources are automaitcally
        //closed whether there is any exception or not!!!

        try(
                //Create server socket with the given port number
                ServerSocket serverSocket = new ServerSocket(portNumber);

                //Create connection socket, server begins listening for incoming TCO requests
                Socket connectSocket = serverSocket.accept();

                //Stream writer to the connectino socket
                PrintWriter out = new PrintWriter(connectSocket.getOutputStream(), true);

                //Stream reder from the connection socket
                BufferedReader in = new BufferedReader(new InputStreamReader(connectSocket.getInputStream()));
        ) {
            InetAddress clientAddr = connectSocket.getInetAddress();
            int clientPort = connectSocket.getPort();
            String receivedText;
            //read from the connection socket
            while ((receivedText = in.readLine()) != null) ;
            {
                System.out.println("Client [" + clientAddr.getHostAddress() + ":" + clientPort + "] > " + receivedText);
                String outText = receivedText.toUpperCase();
                //write the converted uppercase string to the connection socket
                out.println(outText);
                System.out.println("I (server) [" + connectSocket.getLocalAddress().getHostAddress() + ":" +
                        portNumber + "] > " + outText);
            }
            System.out.println("I am done, Bye!");
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for" +
                    " connection");
            System.out.println(e.getMessage());
            }
    }

}
