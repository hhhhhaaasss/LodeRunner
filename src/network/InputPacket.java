package network;

public class InputPacket {
	private ByteBuffer buffer = new ByteBuffer(1024);
    public boolean up, down, left, right, rope;

	public static synchronized byte[] encodeMovement(int isHost, int animationId, int movementX, int movementY) {
        ByteBuffer buffer = new ByteBuffer(1024);
		
		buffer.writeInt(1);
		
		buffer.writeInt(isHost);
		buffer.writeInt(animationId);
		buffer.writeInt(movementX);
		buffer.writeInt(movementY);
		
		return buffer.getBytesList();
        // return (up ? 1 : 0) + ";" + (down ? 1 : 0) + ";" + (left ? 1 : 0) + ";" + (right ? 1 : 0) + ";" + (rope ? 1 : 0);
    }

    public static synchronized int[] decode(byte[] toDecode) {
        ByteBuffer buffer = new ByteBuffer(1024);
        
        int[] toReturn = new int[4];
        
        buffer.setBytesList(toDecode);
        
        if(buffer.readInt() == 1) {
        	toReturn[0] = buffer.readInt();
        	toReturn[1] = buffer.readInt();
        	toReturn[2] = buffer.readInt();
        	toReturn[3] = buffer.readInt();
        }
        
        return toReturn;
        
    }
}
