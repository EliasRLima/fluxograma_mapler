package figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Saida {

	public AnchorPane criar_saida() {
		  AnchorPane saida;
		  try {
			  saida = FXMLLoader.load(getClass().getResource("saida.fxml"));
		  }catch(Exception e) {
			  saida = new AnchorPane();
		  }
		  return saida;
	  }
	
	public Saida() {
		// TODO Auto-generated constructor stub
	}
	
}
