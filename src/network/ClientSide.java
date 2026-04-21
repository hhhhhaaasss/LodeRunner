package network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Main.GamePanel;

public class ClientSide extends Thread{
	private GamePanel gp;
	
    private volatile boolean isRunning = true;

    private boolean isVersus;
    
    public String username;

    private Socket socket;

    private BufferedInputStream readStream;
    public BufferedOutputStream writeStream;

    /** Used to decode byte[] into predetermined packets */
    // private InputPacket packetDecoder;

    private ByteBuffer buffer;

    public ClientSide(Socket socket, GamePanel gp) {
        try{
        	this.gp = gp;
        	
            this.socket = socket;
    
            this.readStream = new BufferedInputStream(this.socket.getInputStream());
            this.writeStream = new BufferedOutputStream(this.socket.getOutputStream());

            this.buffer = new ByteBuffer(1024);

        }catch(IOException e){
            // If the client is force started, it don't goes in the loop
            isRunning = false;
            closeEverything(this.socket, this.writeStream, this.readStream);
        }
    }

    // "Event Listener" that react when Server.java broadcastPacket give out a packet
    @Override
    public void run(){
    	int[] unpackedPacket = new int[4];
    	
        while(isRunning){
            try{
                readStream.read(buffer.bytes);

                buffer.resetCursor();
                switch(buffer.readInt()) {
                case 1:
                	unpackedPacket = InputPacket.decode(buffer.getBytesList());
                	this.gp.handlePlayerInformations(unpackedPacket);
                	// System.out.println(unpackedPacket[0] + " : " + unpackedPacket[2] + " " + unpackedPacket[3]);
                	break;
                case 2:
                	gp.gameState = gp.playState;
                	System.out.println("Got the packet id 2");
                	break;
                }
            } catch(IOException e) {
            	e.printStackTrace();
            }
        }
    }

    protected void closeEverything(Socket socket, BufferedOutputStream writer, BufferedInputStream reader) {
        try{
            if(socket != null){
                socket.close();
            }
            if(writer != null){
                writer.close();
            }
            if(reader != null){
                reader.close();
            }
        }catch(IOException e){}
    }
    public void closeEverything() {
                try{
            if(this.socket != null){
                this.socket.close();
            }
            if(this.writeStream != null){
                this.writeStream.close();
            }
            if(this.readStream != null){
                this.readStream.close();
            }
        }catch(IOException e){}
    }
}