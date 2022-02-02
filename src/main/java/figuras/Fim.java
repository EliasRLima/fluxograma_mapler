package figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Fim extends Figura {

	public AnchorPane criar_fim() {
		  AnchorPane fim;
		  try {
			  fim = FXMLLoader.load(getClass().getResource("fim.fxml"));
		  }catch(Exception e) {
			  fim = new AnchorPane();
			  //System.out.println(e.getMessage());
		  }
		  fim.setId("#fim"+SequenceIdFigura.getNextID());
		  return fim;
	  }
	
	public AnchorPane criar_fim(String id) {
		  AnchorPane fim;
		  try {
			  fim = FXMLLoader.load(getClass().getResource("fim.fxml"));
		  }catch(Exception e) {
			  fim = new AnchorPane();
			  //System.out.println(e.getMessage());
		  }
		  fim.setId(id);
		  return fim;
	  }
	
	  public Fim() {
		// TODO Auto-generated constructor stub
	  }
}
