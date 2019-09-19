import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
public abstract class NetworkConn {
	ConnThread connthread = new ConnThread();
	Consumer<Serializable> callback;
	static int numberss=0;
	Integer val  = 0;
	static int numberP=0;
	public ArrayList<ThreadEx> clients;
	//ArrayList<ConnThread> sockColl=new ArrayList<ConnThread>();
	 
	public NetworkConn(Consumer<Serializable> callback) {
		this.callback = callback;
		connthread.setDaemon(true);
		
		//sockColl.add(connthread);
		//System.out.println("length = "+sockColl.size());
	}
	
	public void startConn() throws Exception{
		connthread.start();
	}
	
	public void send(Serializable data) throws Exception{
		if(isServer())
		{
			for(int i=0; i<this.clients.size(); i++)
			{
				this.clients.get(i).getOut().writeObject(data);
			}
		}
		else
		{
			connthread.out.writeObject(data);
		}
	}
	
	public void closeConn() throws Exception{
		if(!(isServer()))
			connthread.socket.close();
	}
	
	abstract protected boolean isServer();
	abstract protected String getIP();
	abstract protected int getPort();
	
	class ConnThread extends Thread{
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		
		int clientNum;
		ConnThread(){
			if(isServer())
			{
				clients=new ArrayList<ThreadEx>();
				numberP=1;
				clientNum=0;
			}
			else
			{
				clientNum=numberP;
			}
		}
		ConnThread(Socket s)
		{
			socket=s;
		}
		
		
		public void run() {
			
			if(isServer())
			{
				
				try(
						ServerSocket server = new ServerSocket(getPort());
						
					){
					while(true) {
						ThreadEx t1 = new ThreadEx(server.accept(),numberP);
						
						numberP++;
						t1.start();
						clients.add(t1);
						if(clients.size()==1)
						{
							String wait="wait";
							
							clients.get(0).getOut().writeObject(wait);
						}
						if(clients.size()==2)
						{
							String go="go";
							clients.get(0).getOut().writeObject(go);
						}
					}
					
				}
				catch(Exception e) {
					System.out.println(e);
					//callback.accept("connection Closed");
				}
			}
			
			else
			{
				
				try(
						Socket socket = new Socket(getIP(), getPort());
						ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream());
						ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
						){
						
						
					this.socket = socket;
					this.out = out;
					this.in = in;
					socket.setTcpNoDelay(true);
					while(true) {
						Serializable data = (Serializable) in.readObject();
						callback.accept(data);
						

					}
					
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
					//callback.accept("connection Closed");
				}
			}

		}
	}
	class ThreadEx extends Thread{
		
		Socket connection;
		ObjectOutputStream out;
		ObjectInputStream in;
		int playerNum;
		ThreadEx(Socket s, int n){
			this.connection = s;
			playerNum=n;
			
			try {
			out = new ObjectOutputStream( connection.getOutputStream());}
					catch(Exception e) {
						System.out.println("cant make output");}
			
					
		}
		public ObjectOutputStream getOut()
		{
			return out;
		}
		public void run() {
			try(
				//ObjectOutputStream out = new ObjectOutputStream( connection.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(connection.getInputStream())){
				//this.out = out;
				this.in = in;
				
				while(true) {
					
					Serializable data = (Serializable) in.readObject();
					String tmp=data.toString();
					String number=String.valueOf(playerNum);
					String finalstr=number+" "+tmp;
					//System.out.println(finalstr);
					callback.accept(finalstr);
					
					
					}
				}
			catch(Exception e) {
				System.out.println(e);
					
			}
		}
	}
}
