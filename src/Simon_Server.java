import java.net.*;
import java.io.*;

public class Simon_Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;

        // Default port number
        int portNumber = 8080;

        if(args.length >= 1)
        {
            portNumber = Integer.parseInt(args[0]);
        }

        try {
            server = new ServerSocket(portNumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket." + ie);
        }
        System.out.println("Serversocket successfully created\n" + server);

        while (true)
        {
            // Wait for the data from the client and reply
            try{
                /* Listens for a connection to be made to the socket and accepts it
                This method blocks until a connection is made */
                System.out.println("Waiting for connect request...");
                client = server.accept();

                System.out.println("Connect request is accepted...");
                String clientHost = client.getInetAddress().getHostAddress();

                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + "\nClient port = " + clientPort);

                // Read data from the client
                InputStream clientIn = client.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientIn));

                String messageFromClient = bufferedReader.readLine();
                System.out.println("Message received from client = " + messageFromClient);



                // Send response to the client
                if (messageFromClient != null && !messageFromClient.equalsIgnoreCase("bye"))
                {
                    // Determine if the client message is valid

                    Integer firstOccuranceIndex = null;
                    String answerMessage = "";

                    // Remove white space from client message

                    for (int i = 0; i < messageFromClient.length(); i++) {
                        char character = messageFromClient.charAt(i);
                        if(character == '*'
                          || character == '+'
                          || character == '-'
                          || character == '/')
                        {
                            if(firstOccuranceIndex == null)
                            {
                                firstOccuranceIndex = i;
                            }
                            else
                            {
                                answerMessage = "Too many operators, try again";
                                break;
                            }
                        }
                    }

                    if(firstOccuranceIndex == null)
                    {
                        answerMessage = "No operators found, try again";
                    }
                    else
                    {
                        String
                    }

                    // Send return message
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(clientOut, true);
                    printWriter.println(answerMessage);

                    // Close streams
                    clientOut.close();
                    printWriter.close();
                }
                // Close sockets
                else if (messageFromClient.equalsIgnoreCase("bye"))
                {
                    clientIn.close();
                    server.close();
                    bufferedReader.close();
                    client.close();

                    break;
                }
            } catch (IOException ie) {
                System.out.println("Unable to connect to client");
            }
        }
    }
}