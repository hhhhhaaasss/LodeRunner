package OldSetup;

import java.net.*;

import Main.PlayerInput;
import network.InputPacket;

public class ClientOld implements Runnable {

    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    public PlayerInput localInput = new PlayerInput();

    public PlayerState remoteP1 = new PlayerState();
    public PlayerState remoteP2 = new PlayerState();

    public int playerId; // 1 or 2
    public boolean receivedFirstState = false;
    public boolean connected = false;

    public ClientOld(String ip, int port, int playerId) {
        try {
            this.socket = new DatagramSocket();
            this.serverAddress = InetAddress.getByName(ip);
            this.serverPort = port;
            this.playerId = playerId;

            sendInput();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendInput() {
        try {
            InputPacket packet = new InputPacket();
            packet.up = localInput.up;
            packet.down = localInput.down;
            packet.left = localInput.left;
            packet.right = localInput.right;
            packet.rope = localInput.rope;

            String msg = playerId + "|" + packet.encode();
            byte[] data = msg.getBytes();

            DatagramPacket dp = new DatagramPacket(
                    data, data.length,
                    serverAddress, serverPort
            );

            socket.send(dp);
            System.out.println("CLIENT " + playerId + " SENT INPUT");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendConnect() {
        try {
            String msg = playerId + "|CONNECT";
            byte[] data = msg.getBytes();

            DatagramPacket dp = new DatagramPacket(
                data, data.length,
                serverAddress, serverPort
            );

            socket.send(dp);

            System.out.println("CLIENT " + playerId + " SENT CONNECT");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public void run() {

        long lastConnectTime = 0;

        while (true) {
            try {

                // 🔥 SEND CONNECT EVERY 500ms UNTIL CONNECTED
                if (!receivedFirstState && System.currentTimeMillis() - lastConnectTime > 500) {
                    sendConnect();
                    lastConnectTime = System.currentTimeMillis();
                }

                // 🔥 ALWAYS LISTEN FOR SERVER
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("CLIENT RECEIVED: " + msg);

                String[] parts = msg.split("\\|");

                if (parts.length < 2) continue;

                String[] p1 = parts[0].split(",");
                String[] p2 = parts[1].split(",");

                remoteP1.x = Integer.parseInt(p1[0]);
                remoteP1.y = Integer.parseInt(p1[1]);

                remoteP2.x = Integer.parseInt(p2[0]);
                remoteP2.y = Integer.parseInt(p2[1]);

                receivedFirstState = true; // 🔥 THIS WILL NOW TRIGGER

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}