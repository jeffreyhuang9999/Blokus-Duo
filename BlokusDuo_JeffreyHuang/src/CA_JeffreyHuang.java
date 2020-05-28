import display.Frame;
import display.Game;

//import display.Game.Frame;
import java.awt.EventQueue;

public class CA_JeffreyHuang {

	public static void main(String[] args) {
		// TODO Auto-generated method stub			
			//Frame bf = new Frame();					
		
		EventQueue.invokeLater(new Runnable() {
				public void run() {
										
					try {
						Game game = new Game();
						//game.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});			
			
	}

}
