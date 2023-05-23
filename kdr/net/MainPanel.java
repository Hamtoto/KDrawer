package kdr.net;

import kdr.gui.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.time.*;
import java.time.format.*;

class MainPanel extends JPanel implements ActionListener
{
	static int SCROLL_DELTA = 100;

    boolean amIServer = true;
    String myIP;
    String ipAddress = "localhost";
    ButtonPanel buttonPanel;
    ChatPanel chatPanel;
    InputPanel inputPanel;
    JScrollPane chatPane;

    LocalTime now;
    DateTimeFormatter formatter;
    String formattedNow;

    Socket chatClient;
	Socket secondSocket;
	Socket thirdSocket;

    BufferedReader fromChatServer;
    PrintWriter toChatServer;
    Thread fromServerThread;

	ObjectInputStream figureInputStream;
	ObjectOutputStream figureOutputStream;

    private BufferedReader fromChatClient;
    private PrintWriter toChatClient;
    private Thread fromClientThread;

	ServerRole server;
	DrawerFrame mainFrame;

	public void sendFigures() {
		sendString("Figures");
		if (amIServer == true) {
			mainFrame.getCanvas().doSend(
				server.getFigureOutputStream());
		} else {
			mainFrame.getCanvas().doSend(figureOutputStream);
		}
	}
	public void sendString(String s) {
		try
		{
			if (amIServer == true) {
				server.getFigureOutputStream().writeObject(s);
			} else {
				figureOutputStream.writeObject(s);
			}
		}
		catch (IOException ex)
		{
		}
	}
    void setClientInfo(BufferedReader fromChatClient, PrintWriter toChatClient, Thread fromClientThread) {
        this.fromChatClient = fromChatClient;
        this.toChatClient = toChatClient;
        this.fromClientThread = fromClientThread;
    }
    void sendLine(String msg) {
        if (amIServer)
        {
            toChatClient.println(msg);
            toChatClient.flush();
        } else {
            toChatServer.println(msg);
            toChatServer.flush();
        }
    }
    void setIPAddress(String address) {
        ipAddress = address;
        buttonPanel.setIPAddress(address);
    }
    MainPanel(DrawerFrame mainFrame) {
        setLayout(new BorderLayout());

		this.mainFrame = mainFrame;

        buttonPanel = new ButtonPanel(this);

        chatPanel = new ChatPanel(this);
        chatPane = new JScrollPane(chatPanel);
        chatPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        inputPanel = new InputPanel(this);
        inputPanel.setEditable(false);

        add(buttonPanel, BorderLayout.NORTH);
        add(chatPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        try
        {
            InetAddress address = InetAddress.getLocalHost();
            myIP = address.getHostAddress();
        }
        catch (Exception ex)
        {
        }

        server = new ServerRole(this);
        server.start();
    }
    void scrollDown() {
		int max = chatPane.getVerticalScrollBar().getMaximum();
		chatPane.getVerticalScrollBar().setValue(max);
		chatPane.updateUI();
    }
    void writeText(Message msg) {
        setCurrentTime();
		chatPanel.writeText(msg,formattedNow);
    }
    void writeText(String s) {
        setCurrentTime();
        Message m = new Message("U", s);
		chatPanel.writeText(m,formattedNow);
    }
    void informText(String s) {
        setCurrentTime();
        Message m = new Message("Info", s);
		chatPanel.writeText(m,formattedNow);
    }
    void setCurrentTime() {
        now = LocalTime.now();
		int hr = now.getHour();
		int min = now.getMinute();
		if (hr > 12) {
			formattedNow = "pm " + (hr-12) + ":" + min;
		} else {
			formattedNow = "am " + hr + ":" + min;
		}
    }
	String getMyIP() {
        return myIP;
    }
    void setActionCommand(String s) {
        buttonPanel.setActionCommand(s);
    }
    void setEditable(boolean flag) {
        inputPanel.setEditable(flag);
    }
    public void actionPerformed(ActionEvent ev) {
        String cmd = ev.getActionCommand();
        String ipAddress = buttonPanel.getIpAddress();
        if (cmd.equals("Connect"))
        {
            amIServer = false;
            setEditable(true);
            setActionCommand("Disconnect");
            try
            {
                chatClient = new Socket(ipAddress, ServerRole.PORT_NO);
                informText("Connected...");
				mainFrame.communicationEstablished();
                fromChatServer = new BufferedReader(new InputStreamReader(chatClient.getInputStream()));
                toChatServer = new PrintWriter(chatClient.getOutputStream());

				secondSocket = new Socket(ipAddress, ServerRole.PORT_NO+1);
				figureOutputStream = 
					new ObjectOutputStream(secondSocket.getOutputStream());

				thirdSocket = new Socket(ipAddress, ServerRole.PORT_NO+2);
				figureInputStream = new ObjectInputStream(thirdSocket.getInputStream());

				Thread receiver = new Thread() {
					public void run() {
						while(true) {
							String tag = "";
							try
							{
								tag = (String)figureInputStream.readObject();
							}
							catch (Exception ex)
							{
							}
							System.out.println("Client: " + tag);
							// do not remove this if statement
							if (tag.length() == 0) break;
							if (tag.equals("SendMe")) {
								mainFrame.enableSendButton(true);
							} else if (tag.equals("Cancel")) {
								mainFrame.enableSendButton(false);
							} else if (tag.equals("RealTimeBegin")) {
								mainFrame.setRTButton(true);
								mainFrame.enableSendButton(false);
								mainFrame.enableSendMeButton(false);
								mainFrame.getCanvas().setRealTimeExchangeFlag(true);
							} else if (tag.equals("RealTimeEnd")) {
								mainFrame.setRTButton(false);
								mainFrame.enableSendButton(false);
								mainFrame.enableSendMeButton(true);
								mainFrame.getCanvas().setRealTimeExchangeFlag(false);
							} else if (tag.equals("####")) {
								mainFrame.communicationClosed();
                                break;
							} else { // "Figures"
								mainFrame.getCanvas().doReceive(figureInputStream);
							}
						}
System.out.println("HERE1-0");
						try
						{
							figureOutputStream.close();
							secondSocket.close();

							figureInputStream.close();
							thirdSocket.close();
						}
						catch (IOException ex)
						{
						}
					}
				};
				receiver.start();

				fromServerThread = new Thread() {
                    public void run() {
                        try
                        {
                            String msg;
                            while (true)
                            {
                                msg = fromChatServer.readLine();
                                Message newMessage = new Message("U", msg);
                                if (msg == null || msg.equals("####"))
                                {
									mainFrame.communicationClosed();
                                    break;
                                }
                                writeText(newMessage);
                            }
System.out.println("HERE1-1");
							try
							{
								fromChatServer.close();
								toChatServer.close();
								chatClient.close();
							}
							catch (IOException ex)
							{
							}
                        }
                        catch (IOException ex)
                        {
                            System.out.println(ex);
                        }
                        amIServer = true;
                        setEditable(false);
                        setActionCommand("Connect");
                    }
                };
                fromServerThread.start();
            }
            catch (IOException ex)
            {
                System.out.println(ex);
            }
        } else if (cmd.equals("Disconnect"))
        {
            sendLine("####");
			mainFrame.communicationClosed();
            if (amIServer)
            {
                try
                {
					server.getFigureOutputStream().writeObject("####");

                    fromChatClient.close();
                    toChatClient.close();

					server.getFigureInputStream().close();
					server.getFigureOutputStream().close();
                }
                catch (IOException ex)
                {
                }
            } else {
                try
                {
					figureOutputStream.writeObject("####");

                    fromChatServer.close();
                    toChatServer.close();
                    chatClient.close();

					figureOutputStream.close();
					secondSocket.close();

					figureInputStream.close();
					thirdSocket.close();
                }
                catch (IOException ex)
                {
                }
            }
            amIServer = true;
            setEditable(false);
            setActionCommand("Connect");
        }
    }
}
