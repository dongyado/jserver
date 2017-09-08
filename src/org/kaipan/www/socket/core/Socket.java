package org.kaipan.www.socket.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.kaipan.www.socket.ssl.SslEngine;

public class Socket
{
	private long socketId;
	
	private SslEngine 		   sslEngine = null;
	
    private MessageReader messageReader  = null;
    private MessageWriter  messageWriter = null;
    private SocketChannel  socketChannel = null;
    
    public boolean endOfStreamReached = false;
    public boolean closeAfterResponse = false;
    
    public Socket() 
    {
    	
    }
    
    public Socket(SocketChannel sockChannel) 
    {
        this.socketChannel = sockChannel;
    }
    
    public int read(ByteBuffer byteBuffer) throws IOException 
    {
        int bytesRead = this.socketChannel.read(byteBuffer);
        int totalBytesRead = bytesRead;

        while ( bytesRead > 0 ) {
            bytesRead = this.socketChannel.read(byteBuffer);
            totalBytesRead += bytesRead;
        }
        
        if ( bytesRead == -1 ) {
            // the socket closed, error occurred, or terminated unexpectedly
        	endOfStreamReached = true;
        }

        return totalBytesRead;
    }

    public int write(ByteBuffer byteBuffer) throws IOException 
    {
        int bytesWritten 	  = this.socketChannel.write(byteBuffer);
        int totalBytesWritten = bytesWritten; 

        /**
         * ByteBuffer method: remaining
         *    return limit(length) = opacity - position
         */
        while ( bytesWritten > 0 && byteBuffer.hasRemaining() ) {
            bytesWritten = this.socketChannel.write(byteBuffer);
            totalBytesWritten += bytesWritten;
        }

        return totalBytesWritten;
    }
    
    public void setSocketId(long socketId) 
    {
    	this.socketId = socketId;
    }
    
    public void setSslEngine(SslEngine sslEngine) 
    {
    	this.sslEngine = sslEngine;
    }
    
    public void setSocketChannel(SocketChannel sockChannel) 
    {
    	this.socketChannel = sockChannel;
    }
    
    public void setMessageReader(MessageReader messageReader) 
    {
    	this.messageReader = messageReader;
    }
    
    public void setMessageWriter(MessageWriter messageWriter) 
    {
    	this.messageWriter = messageWriter;
    }
    
    public long getSocketId() 
    {
    	return socketId;
    }
    
    public SslEngine getSslEngine() 
    {
    	return sslEngine;
    }

    public MessageReader getMessageReader() 
    {
    	return messageReader;
    }
    
    public MessageWriter getMessageWriter() 
    {
    	return messageWriter;
    }
    
    public SocketChannel getSocketChannel() 
    {
        return socketChannel;
    }
}
