
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.Serializable;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;

class TPSLStest {

//test if client is constructed correctly
	@Test
	void testClientConstr() {
		NetworkConn s =new RPSLSserver(5555,data -> {			
		});
		NetworkConn c =new RPSLSclient("127.0.0.1",5555,data -> {			
		});

		boolean a=c.isServer();
		
		assertEquals(a,false);	
	}

//test if server is constructed correctly
	@Test
	void testServerConstr() {
		NetworkConn s =new RPSLSserver(5555,data -> {			
		});

		boolean a=s.isServer();
		
		assertEquals(a,true);	
	}

//test if player returns the correct play (Rock)
	@Test
	void testPlayRock() {
		Player p1=new Player();
		p1.play(1);
		String a=p1.getPlay();
		assertEquals("Rock",a);	
	}

//test of hetPlay returns correctly if the player has not played anything
	@Test
	void testPlayNothing() {
		Player p1=new Player();
		String a=p1.getPlay();
		assertEquals("None",a);	
	}
//are points for player 1 and 2 properly displaced
	@Test
	void testPoints() {
		GameState game=new GameState();
		int one = game.p1.points;
		int two = game.p2.points;
		assertEquals(0,one+two);	
	}
	
//test if there are zero clients when server starts
	@Test
	void testServerOne() {
		NetworkConn s =new RPSLSserver(5555,data -> {			
		});
		
		int zero=s.clients.size();
		assertEquals(0,zero);
	}

	@Test
	void testServerTwo() {
		NetworkConn s =new RPSLSserver(5555,data -> {			
		});
		int a=0;
		try {
			s.startConn();
			}
		catch(Exception e)
			{
			}
		NetworkConn c =new RPSLSclient("127.0.0.1",5555,data -> {			
		});
		try {
			c.startConn();
			}
		catch(Exception e)
			{
			}
		
		a=s.numberP;
		try {
			s.closeConn();
			c.closeConn();
		}
		catch(Exception e)
		{
		}
		assertEquals(1,a);
	}
	
//test if port is assigned correctly to server
	@Test
	void testServerPort() {
		NetworkConn s =new RPSLSserver(5556,data -> {			
		});

		int x=11;
		try {
			s.startConn();
			
			}
		catch(Exception e)
			{
			}
		NetworkConn c =new RPSLSclient("127.0.0.1",5556,data -> {			
		});
		try {
			c.startConn();
			}
		catch(Exception e)
			{
			}
		x=s.getPort();
		try {
			s.closeConn();
			c.closeConn();
		}
		catch(Exception e)
		{
		}
		assertEquals(5556,x);
	}
//test if port is assigned correctly to client
	@Test
	void testClientPort() {
		NetworkConn s =new RPSLSserver(8080,data -> {			
		});

		int x=11;
		try {
			s.startConn();
			
			}
		catch(Exception e)
			{
			}
		NetworkConn c =new RPSLSclient("127.0.0.1",8080,data -> {			
		});
		try {
			c.startConn();
			}
		catch(Exception e)
			{
			}
		x=c.getPort();
		assertEquals(8080,x);
	}
	//test if IP is assigned correctly to client
		@Test
		void testClientIP() {
			NetworkConn s =new RPSLSserver(8081,data -> {			
			});

			String x="11";
			try {
				s.startConn();
				
				}
			catch(Exception e)
				{
				}
			NetworkConn c =new RPSLSclient("127.0.0.1",8081,data -> {			
			});
			try {
				c.startConn();
				}
			catch(Exception e)
				{
				}
			x=c.getIP();
			assertEquals("127.0.0.1",x);
		}
}
