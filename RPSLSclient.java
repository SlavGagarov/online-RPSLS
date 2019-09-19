import java.io.Serializable;
import java.util.function.Consumer;
public class RPSLSclient extends NetworkConn{
	 int port;
	 String ip;
	
	public RPSLSclient(String ip, int port, Consumer<Serializable> callback) {
		super(callback);
		this.ip = ip;
		this.port = port;
	}


	@Override
	protected boolean isServer() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	protected String getIP() {
		// TODO Auto-generated method stub
		return ip;
	}


	@Override
	protected int getPort() {
		// TODO Auto-generated method stub
		return this.port;
	}
}
