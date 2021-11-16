package figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Processamento {

	public AnchorPane criar_processamento() {
		  AnchorPane processamento;
		  try {
			  processamento = FXMLLoader.load(getClass().getResource("processamento.fxml"));
		  }catch(Exception e) {
			  processamento = new AnchorPane();
		  }
		  return processamento;
	  }
	
	public Processamento() {
		// TODO Auto-generated constructor stub
	}
}
