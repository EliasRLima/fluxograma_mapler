package figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Console extends Figura{
	
	public AnchorPane criar_entrada() {
		  AnchorPane console;
		  try {
			  console = FXMLLoader.load(getClass().getResource("console.fxml"));
		  }catch(Exception e) {
			  console = new AnchorPane();
			  //System.out.println(e.getMessage());
		  }
		  console.setId("#console"+SequenceIdFigura.getNextID());
		  return console;
	}

	public Console() {
		// TODO Auto-generated constructor stub
	}
	
}
