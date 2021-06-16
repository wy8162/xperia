package y.w.concurrency.jcp;

// Tue Nov  2 18:34:53 EST 2004
//
// Written by Sean R. Owens, sean at guild dot net, released to the
// public domain.  Share and enjoy.  Since some people argue that it is
// impossible to release software to the public domain, you are also free
// to use this code under any version of the GPL, LPGL, or BSD licenses,
// or contact me for use of another license.
// http://darksleep.com/player

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

class VerySimpleClient {
    private String serverHostname = null;
    private int serverPort = 0;
    private byte[] data = null;
    private Socket sock = null;
    private InputStream sockInput = null;
    private OutputStream sockOutput = null;

    public VerySimpleClient(String serverHostname, int serverPort, byte[] data){
	this.serverHostname =  serverHostname;
	this.serverPort = serverPort;
	this.data = data;
    }
    
    public void sendSomeMessages(int iterations) {
	System.err.println("Opening connection to "+serverHostname+" port "+serverPort);
	try {
	    sock = new Socket(serverHostname, serverPort);
	    sockInput = sock.getInputStream();
	    sockOutput = sock.getOutputStream();
	}
	catch (IOException e){
	    e.printStackTrace(System.err);
	    return;
	}

	System.err.println("About to start reading/writing to/from socket.");

	byte[] buf = new byte[data.length];
	int bytes_read = 0;
	for(int loopi = 1; loopi <= iterations; loopi++) {
	    try {
		sockOutput.write(data, 0, data.length);	
		bytes_read = sockInput.read(buf, 0, buf.length);
	    }
	    catch (IOException e){
		e.printStackTrace(System.err);
	    }
	    if(bytes_read < data.length) {
		System.err.println("run: Sent "+data.length+" bytes, server should have sent them back, read "+bytes_read+" bytes, not the same number of bytes.");
	    }
	    else {
		System.err.println("Sent "+bytes_read+" bytes to server and received them back again, msg = "+(new String(data)));
	    }

	    // Sleep for a bit so the action doesn't happen to fast - this is purely for reasons of demonstration, and not required technically.
	    try { Thread.sleep(50);} catch (Exception e) {}; 
	}
	System.err.println("Done reading/writing to/from socket, closing socket.");

	try {
	    sock.close();
	}
	catch (IOException e){
	    System.err.println("Exception closing socket.");
	    e.printStackTrace(System.err);
	}
	System.err.println("Exiting.");
    }

    public static void main(String argv[]) {
	String hostname = "localhost";
	int port = 54321;
	byte[] data = "Hello World".getBytes();
	
	VerySimpleClient client = new VerySimpleClient(hostname, port, data);
	client.sendSomeMessages(100);
    }
}
