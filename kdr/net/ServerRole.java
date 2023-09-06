package kdr.net;

import java.io.*;
import java.net.*;

class ServerRole extends Thread {
    static int PORT_NO = 8000;
    MainPanel mainWnd;
    Socket clientChatSocket;
    Socket figureInputSocket;
    Socket figureOutputSocket;

    InputStream in;
    OutputStream out;
    ObjectInputStream figureInputStream;
    ObjectOutputStream figureOutputStream;

    ServerRole(MainPanel mainWnd) {
        this.mainWnd = mainWnd;
    }

    ObjectInputStream getFigureInputStream() {
        return figureInputStream;
    }

    ObjectOutputStream getFigureOutputStream() {
        return figureOutputStream;
    }

    public void run() {
        try {
            ServerSocket listenerSocket = new ServerSocket(PORT_NO);
            ServerSocket secondServerSocket = new ServerSocket(PORT_NO + 1);
            ServerSocket thirdServerSocket = new ServerSocket(PORT_NO + 2);
            mainWnd.informText("Server started...");
            mainWnd.informText(mainWnd.getMyIP() + " on port: " + PORT_NO);

            while (true) {
                clientChatSocket = listenerSocket.accept();

                String ip = clientChatSocket.getInetAddress().getHostAddress();
                mainWnd.informText(ip + " is connected...");
                mainWnd.setEditable(true);
                mainWnd.setIPAddress(ip);
                mainWnd.setActionCommand("Disconnect");
                mainWnd.mainFrame.communicationEstablished();

                figureInputSocket = secondServerSocket.accept();
                in = figureInputSocket.getInputStream();
                figureInputStream = new ObjectInputStream(in);

                figureOutputSocket = thirdServerSocket.accept();
                out = figureOutputSocket.getOutputStream();
                figureOutputStream = new ObjectOutputStream(out);

                Thread receiver = new Thread() {
                    public void run() {
                        while (true) {
                            String tag = "";
                            try {
                                tag = (String) figureInputStream.readObject();
                            } catch (Exception ex) {
                            }
                            System.out.println("Server: " + tag);
                            // do not remove this if statement
                            if (tag.isEmpty()) break;
                            if (tag.equals("SendMe")) {
                                mainWnd.mainFrame.enableSendButton(true);
                            } else if (tag.equals("Cancel")) {
                                mainWnd.mainFrame.enableSendButton(false);
                            } else if (tag.equals("RealTimeBegin")) {
                                mainWnd.mainFrame.setRTButton(true);
                                mainWnd.mainFrame.enableSendButton(false);
                                mainWnd.mainFrame.enableSendMeButton(false);
                                mainWnd.mainFrame.getCanvas().setRealTimeExchangeFlag(true);
                            } else if (tag.equals("RealTimeEnd")) {
                                mainWnd.mainFrame.setRTButton(false);
                                mainWnd.mainFrame.enableSendButton(false);
                                mainWnd.mainFrame.enableSendMeButton(true);
                                mainWnd.mainFrame.getCanvas().setRealTimeExchangeFlag(false);
                            } else if (tag.equals("####")) {
                                mainWnd.mainFrame.communicationClosed();
                                break;
                            } else { // "Figures"
                                mainWnd.mainFrame.getCanvas().doReceive(figureInputStream);
                            }
                        }
                        System.out.println("HERE2-0");
                        try {
                            figureInputStream.close();
                            figureInputSocket.close();

                            figureOutputStream.close();
                            figureOutputSocket.close();
                        } catch (IOException ex) {
                        }
                    }
                };
                receiver.start();

                BufferedReader fromChatClient
                        = new BufferedReader(new InputStreamReader(clientChatSocket.getInputStream()));
                PrintWriter toChatClient = new PrintWriter(clientChatSocket.getOutputStream());

                Thread fromClientThread = new Thread() {
                    public void run() {
                        try {
                            String msg;
                            while (true) {
                                msg = fromChatClient.readLine();
                                Message m = new Message("U", msg);
                                if (msg == null || msg.equals("####")) {
                                    mainWnd.mainFrame.communicationClosed();
                                    break;
                                }
                                mainWnd.writeText(m);
                            }
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                        System.out.println("HERE2-1");
                        try {
                            clientChatSocket.close();
                            fromChatClient.close();
                            toChatClient.close();

                        } catch (IOException ex) {
                        }
                        mainWnd.setEditable(false);
                        mainWnd.setActionCommand("Connect");
                    }
                };
                mainWnd.setClientInfo(fromChatClient, toChatClient, fromClientThread);
                fromClientThread.start();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
