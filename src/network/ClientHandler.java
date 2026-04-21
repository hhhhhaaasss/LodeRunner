package network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import OldSetup.ServerOld;

public class ClientHandler implements Runnable{
    private volatile boolean isRunning = true;
    private Server server;

    protected Socket socket;

    // Protected so the Server class can access it
    protected BufferedInputStream readerStream;
    protected BufferedOutputStream writerStream;

    public ClientHandler(Socket socket, Server server){
        try{
            //Connection between server and client
            this.socket = socket;

            this.writerStream = new BufferedOutputStream(this.socket.getOutputStream());
            this.readerStream = new BufferedInputStream(this.socket.getInputStream());

            this.server = server;
        }catch(IOException e){
            closeEverything(this.socket, this.writerStream, this.readerStream);
        }

    }

    // When we write into the writeBuffer of CliendSide it end here
    @Override
    public void run() {
        byte[] bytes = new byte[1024];

        while(this.isRunning){
            try {
                this.readerStream.read(bytes);
                server.broadcastPacket(bytes);
            } catch (IOException e) {
                // System.out.println("\nERROR ClientHandler.java: thread run");
                closeEverything(this.socket, this.writerStream, this.readerStream);
                break;
            }
        }
    }

    protected void closeEverything(Socket socket, BufferedOutputStream writer, BufferedInputStream reader){
        if(isRunning){
            isRunning = false;
    
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
            }catch(IOException e){
                System.out.println("\nERROR GameInterface/LanConnection/ClientHandler.java: catched IOException while closing");
            }
        }
    }

    @Override
    public String toString(){
        return "ClientHandler[Is Running = " + this.isRunning + " // Server connected to = " + this.server.toString() + " // Socket = " + this.socket.toString() + "]\n";
    }
}