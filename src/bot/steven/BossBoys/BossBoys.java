package bot.steven.BossBoys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.TreeMap;

import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

/*
 * BossBoys:
 * living the dream. a fully automated bot with interface to take , receive, and command Jug interaction.
 */
@ScriptManifest(author = "Steven Ventura", info = "My Water Boys", logo = "", name = "BossBoys", version = 0)
public class BossBoys extends Script{
	
	enum MASTERSTATES {
		Idle,
		Travelling,
		Distributing,
		Collecting,
		
	};
	
	//TODO: in the UI, prevent him from distributing and collecting if he is not in falador.
	enum DISTRIBUTING {
		PopulatingCustomerArray,
		 SendingTradeRequestToEach,
		 Done
	}
	DISTRIBUTING distributingState;
	
	enum COLLECTING {
		PopulatingCustomerArray,
		 SendingTradeRequestToEach,
		 Done
	}
	
	COLLECTING collectingState;
	
	MASTERSTATES master = MASTERSTATES.Idle;
	
	class Travelling {
		
		TravelNode currentDestination;
		
		class TravelNode {
			TravelNode towardsFalador=null, towardsGE=null;
			private int x, y;
			boolean finalDestination;
			public TravelNode(int x, int y) {
				this.x=x;this.y=y;
				finalDestination=false;
			}
		}
		CITY destination;
		//TODO: put things in path thing manually. maybe use arraylist idk whatever works for easiest inputing w helper function
		public Travelling(CITY destination) {
			this.destination=destination;
			defineTree();
		}
		private void defineTree() {
			//TODO: populate the tree so its usable
			
		}
		
		
	}
	
	Travelling traveller = null;
	
	TreeMap<String,Integer> playersToGiveJugsTo;
	TreeMap<String,String> playersToTakeJugsFrom;
	
	private void travelToCity(CITY city) {
		traveller = new Travelling(city);
		master = MASTERSTATES.Travelling;
		
		
	}
	enum CITY {
		Falador,
		GrandExchange
	};
	
	//the city of this bot
	enum CURRENTCITY {
		Unknown,
		Falador,
		GrandExchange,
		Transit
	}
	CURRENTCITY currentCity = CURRENTCITY.Unknown;
	
	private void printPlayerDataToFile() {
		//TODO: location,numEmptyJugs,numFullJugs
		
		try{
			File f = new File(getDirectoryData() + getParameters() + ".bossData");
			
			PrintWriter p = new PrintWriter(f);
			p.println(""+new Date());
			//DateFormat.parse( );
			p.println(""+myPlayer().getX());
			p.println(""+myPlayer().getY());
			p.println(""+numEmptyJugs);
			p.println(""+numFullJugs);
			p.close();
			
			
		}catch(Exception e) {e.printStackTrace();}
		
	}
	
	public void onMessage(Message message)
	{
		final int CLANCHAT = 9, WHISPER = 3;
		String text = message.getMessage();
		
		if (message.getTypeId() == CLANCHAT 
				|| message.getTypeId() == WHISPER) {
			
			String phrase = text.split(" ")[0];
			
			if (phrase == null)
				return;
			
			switch (phrase) {
			case "Travelfalador":
				break;
			case "Travelge":
				
				break;
			}
			
			
			
		}
		
		
		
		
	}
	@Override
	public void onExit() {
		
	}
	
	public void onPaint(Graphics2D g) {
		g.setPaint(Color.ORANGE);
		
		switch (master) {
		case Collecting:
			g.drawString("JugBoy=" + collectingState,10,60);
			break;
		case Distributing:
			g.drawString("JugBoy=" + distributingState,10,60);
			break;
		default:
			g.drawString("JugBoy=" + master,10,60);
			break;
		
		}
		
		g.drawString(""+numEmptyJugs,10,80);
		g.drawString(""+numFullJugs,10,100);
		
	}
	int numEmptyJugs=-1,numFullJugs=-1;
	private long CT=System.currentTimeMillis(),LT=System.currentTimeMillis();
	@Override
	public int onLoop() throws InterruptedException {
		CT = System.currentTimeMillis();
		if (CT - LT > 30 * 1000) {
			LT = CT;
			printPlayerDataToFile();
		}
		
		switch (master) {
		case Idle:
			//do nothing
			break;
		case Travelling:
			//TODO: travel to next node until done
			//TODO: output the done signal
			break;
		case Distributing:
			stateMachineDistributing();
			break;
		case Collecting:
			stateMachineCollecting();
			break;
		
		
		}
		
		
		return 150;
	}
	
	private void stateMachineCollecting() {
		
		switch(collectingState) {
		case PopulatingCustomerArray:
			//TODO: read from file to see who has stuff ready for me right now
			playersToTakeJugsFrom = new TreeMap<>();
			
			break;
		case SendingTradeRequestToEach:
			//TODO: cycle through each person and go through with trade request
			
			
			break;
			case Done:
				master = MASTERSTATES.Idle;
			break;
		}
		
		
	}
	private void stateMachineDistributing() {
		switch(distributingState) {
		case PopulatingCustomerArray:
			playersToGiveJugsTo = new TreeMap<>();
			//TODO: read from file to see who is available , and who needs jugs, and how many to give
			
			break;
		case SendingTradeRequestToEach:
			//TODO: cycle through each person and go through with trade request
			
			
			break;
		case Done:
				master = MASTERSTATES.Idle;
			break;
		}
		
		
	}
	
	
	
	
	
}
