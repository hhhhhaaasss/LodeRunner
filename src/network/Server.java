package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Server extends Thread{
    private ServerSocket serverSocket;

    private ByteBuffer buffer = new ByteBuffer(1024);

    private volatile boolean isRunning = true;

    int currentAmmOfPlayers = 0;
    public ClientHandler[] clientHandlersInServer = new ClientHandler[2];
    // public ArrayList<Player> playerList = new ArrayList<>();

    // Variables for the maps to not be local
    private int currentLevel = 1;
    private ArrayList<char[][]> maps = new ArrayList<>();

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run(){
        while(isRunning){
            try{
                // Wait here until a client connect
                Socket socket = this.serverSocket.accept();
                
                ClientHandler clientHandler = new ClientHandler(socket, this);

                clientHandlersInServer[currentAmmOfPlayers++] = clientHandler;
                Thread threadForClientHandler = new Thread(clientHandler);
                threadForClientHandler.start();
                System.out.println("Got a connection!");
                
                if(currentAmmOfPlayers == 2) {
                	this.buffer.clear();
                	this.buffer.writeInt(2);
                	broadcastPacket(this.buffer.getBytesList());
                }
            }catch(IOException e){
                System.out.println("\nERROR Server.java: catched IOException in startServer");
                if(!isRunning){
                    break;
                }
            }
        }
    }

    // Send to each client. Received via ClientSide.java listenForPackets function
    protected synchronized void broadcastPacket(byte[] bytes){
        // if interceptPacket return false, it dont send the current packet
        if(!interceptPacket(bytes)){
            System.out.println("A packet was denied for broadcast");
            return;
        }

        for (ClientHandler client : clientHandlersInServer) {
            try {
                if(!client.socket.isClosed()){
                    // Send packet to each clients
                    client.writerStream.write(bytes);
                    client.writerStream.flush();
                }
            } catch (IOException e) {
                System.out.println("\nERROR ClientHandler.java : Catched in broadcastMessage. About to terminate connection with client.");
                client.closeEverything(client.socket, client.writerStream, client.readerStream);
            }
        }
    }

    // Verify the packet that was send by the users
    // If the packet is only only for the server or have invalid informations return false
    private boolean interceptPacket(byte[] bytes){
        //this.buffer.clear();
        //this.buffer.bytes = bytes;
        
        

        return true;
    }

    private void loadMapsOnServer(ByteBuffer buffer){
        // Just in case start from the beginning of the buffer
        buffer.resetCursor();

        // Skip the id of the packet
        buffer.readInt();

        int ammountOfLevels = buffer.readInt();

        for(int i = 0; i < ammountOfLevels; i++) {
            char[][] currentMap = new char[buffer.readInt()][buffer.readInt()];
            
            for(int y = 0; y < currentMap.length; y++) {
                for(int x = 0; x < currentMap[y].length; x++) {
                    currentMap[y][x] = buffer.readChar();
                }
            }    

            this.maps.add(currentMap);
        }

        System.out.println("The server saved all the maps from the host side!");
        // this.debugPrintLoadedMaps();
    }
    private void loadNextMapInBuffer(ByteBuffer buffer){
        char[][] nextLevelMap = this.maps.get(this.currentLevel++);
        
        buffer.resetCursor();

        buffer.writeInt(10);
        
        buffer.writeInt(nextLevelMap.length);
        buffer.writeInt(nextLevelMap[0].length);

        for (int y = 0; y < nextLevelMap.length; y++) {
            for (int x = 0; x < nextLevelMap[y].length; x++) {
                buffer.writeChar(nextLevelMap[y][x]);
            }
        }
    }

    public synchronized void closeServerProperly(){
        try {
        ByteBuffer buffer = new ByteBuffer(4);
        buffer.writeInt(4);

        byte[] packet = Arrays.copyOf(buffer.getBytesList(), buffer.getBytesList().length);
        broadcastPacket(packet);

        // buffer.clear();

        this.isRunning = false;

        if(this.serverSocket != null && !this.serverSocket.isClosed()){
            this.serverSocket.close();
        }

        for (ClientHandler client : clientHandlersInServer) {
            client.closeEverything(client.socket, client.writerStream, client.readerStream);
        }

        clientHandlersInServer = null;

        } catch (IOException e) {
            System.out.println("\nERROR GameInterface/LanConnection/Server.java: While closing server");
        }
    }

    private void debugPrintLoadedMaps(){
        int count = 1;
        for (char[][] map : this.maps) {
            System.out.println("Map number " + count + " :");
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    System.out.print(map[y][x] + ", ");
                }
                System.out.println();
            }
            System.out.println("End Map number " + count++);
        }
    }

    @Override
    public String toString(){
        return "Server[Is Running = " + this.isRunning + "]";
    }
}