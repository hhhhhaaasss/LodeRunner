package OldSetup;

import java.net.*;

import Main.GamePanel;
import network.InputPacket;

public class ServerOld implements Runnable {

    private DatagramSocket socket;
    private byte[] buffer = new byte[1024];

    public PlayerState p1 = new PlayerState();
    public PlayerState p2 = new PlayerState();
    
    GamePanel gp;
    private boolean started = false;


    public ServerOld(int port, GamePanel gp) {
        try {
            this.gp = gp; // IMPORTANT
            socket = new DatagramSocket(port);
            System.out.println("Server started on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());

                String[] parts = msg.split("\\|");

                if (parts.length < 1) {
                    continue; // ignore bad packet
                }

                int id = Integer.parseInt(parts[0]);

                
                if (parts.length < 2) {
                    System.out.println("INVALID PACKET FROM PLAYER " + id);
                    continue;
                }

                String data = parts[1];

               
                if (!data.contains(",")) {
                    System.out.println("PLAYER " + id + " CONNECTED");

                    sendState(packet.getAddress(), packet.getPort());
                    continue;
                }

               
                InputPacket input = InputPacket.decode(data);

                if (id == 1) applyInput(p1, input);
                if (id == 2) applyInput(p2, input);

                // send updated state
                sendState(packet.getAddress(), packet.getPort());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void applyInput(PlayerState p, InputPacket in) {

        int speed = 2;

        if (in.up)    p.y -= speed;
        if (in.down)  p.y += speed;
        if (in.left)  p.x -= speed;
        if (in.right) p.x += speed;
    }
    

    private void sendState(InetAddress addr, int port) throws Exception {

        int p1x = 0, p1y = 0;
        int p2x = 0, p2y = 0;

        if (gp.player != null) {
            p1x = gp.player.x;
            p1y = gp.player.y;
        }

        if (gp.remotePlayer != null) {
            p2x = gp.remotePlayer.x;
            p2y = gp.remotePlayer.y;
        }

        String state = p1x + "," + p1y + "|" + p2x + "," + p2y;

        byte[] data = state.getBytes();

        DatagramPacket packet = new DatagramPacket(
            data, data.length, addr, port
        );

        socket.send(packet);

        System.out.println("SERVER SEND: " + state);
    }
}