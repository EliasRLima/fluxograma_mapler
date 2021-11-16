package figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Entrada extends Figura{
	
	public AnchorPane criar_entrada() {
		  AnchorPane entrada;
		  try {
			  entrada = FXMLLoader.load(getClass().getResource("entrada.fxml"));
		  }catch(Exception e) {
			  entrada = new AnchorPane();
			  //System.out.println(e.getMessage());
		  }
		  return entrada;
	}

	public Entrada() {
		// TODO Auto-generated constructor stub
	}
	
}
