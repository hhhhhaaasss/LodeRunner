package network;

import java.nio.charset.StandardCharsets;

public class ByteBuffer {
    // Public for easier access via the in/out streams
    public byte[] bytes;
    private int cursor;
    private int currentUsedSize;

    public ByteBuffer(int initialSize) {
        this.cursor = 0;
        this.currentUsedSize = 0;
        this.malloc(initialSize);
    }
    public ByteBuffer() {
        this.cursor = 0;
        this.currentUsedSize = 0;
    }

    public void malloc(int sizeInByte){
        this.bytes = new byte[sizeInByte];
    }

    public int readInt(){
        int current = this.cursor;
        this.cursor += 4;
        
        return ((this.bytes[current] & 0xFF) << 24) |
                ((this.bytes[current + 1] & 0xFF) << 16) |
                ((this.bytes[current + 2] & 0xFF) << 8) |
                ((this.bytes[current + 3] & 0xFF));
    }
    public void writeInt(int toWrite){
        // Verify if bytes have enough space for a int
        if((this.currentUsedSize + 4) > this.bytes.length){
            System.out.println(
                "\nERROR GameInterface/LanConnection/Packets/ByteHandler/ByteBuffer.java: ByteBuffer doesn't have enough allocated space for another int"
            );
            return;
        }
        int endOfCurrentBytes = this.currentUsedSize;
        
        int movedBy = 24;
        for (int i = endOfCurrentBytes; i < (endOfCurrentBytes + 4); i++) {
            this.bytes[i] = (byte) (toWrite >> movedBy);
            movedBy -= 8; 
        }
        this.currentUsedSize += 4;
    }

    public String readString(int sizeToRead){
        if((this.cursor + sizeToRead) > this.bytes.length){
            System.out.println(
                "\nERROR GameInterface/LanConnection/Packets/ByteHandler/ByteBuffer.java: ByteBuffer cursor is going out of bounds while reading String"
            );
            return null;
        }

        byte[] temp = new byte[sizeToRead];
        for (int i = 0; i < sizeToRead; i++) {
            temp[i] = this.bytes[this.cursor++];
        }
        return new String(temp, StandardCharsets.UTF_8);
    }
    public void writeString(String toAdd){
        int sizeOfString = toAdd.length();

        if((this.currentUsedSize + sizeOfString) > this.bytes.length){
            System.out.println(
                "\nERROR GameInterface/LanConnection/Packets/ByteHandler/ByteBuffer.java: ByteBuffer doesn't have enough allocated space for another String"
            );
            return;
        }

        byte[] toAddByte = toAdd.getBytes();
        int counter = 0;
        for (int i = this.currentUsedSize; i < (this.currentUsedSize + sizeOfString); i++) {
            this.bytes[i] = toAddByte[counter++];
        }

        this.currentUsedSize += sizeOfString;

    }
    public char readChar(){
        if((this.cursor + 1) > this.bytes.length){
            System.out.println(
                "\nERROR GameInterface/LanConnection/Packets/ByteHandler/ByteBuffer.java: ByteBuffer cursor is going out of bounds while reading String"
            );
            return 0;
        }
        return (char) this.bytes[this.cursor++];
    }
    public void writeChar(char toAdd){
        if((this.currentUsedSize + 1) > this.bytes.length){
            System.out.println(
                "\nERROR GameInterface/LanConnection/Packets/ByteHandler/ByteBuffer.java: ByteBuffer cursor is going out of bounds while reading String"
            );
            return;
        }

        this.bytes[this.currentUsedSize++] = (byte) toAdd;
    }


    public void clear(){
        this.bytes = new byte[1024];
        this.cursor = 0;
        this.currentUsedSize = 0;
    }

    public byte[] getBytesList() {
        return this.bytes;
    }

    public void setBytesList(byte[] bytes) {
        this.bytes = bytes;
    }
    

    public void resetCursor(){
        this.cursor = 0;
    }
    public void setCurrentUsedSize(int currentUsedSize) {
        this.currentUsedSize = currentUsedSize;
    }
}
