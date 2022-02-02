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
		  processamento.setId("#processamento"+SequenceIdFigura.getNextID());
		  return processamento;
	  }
	
	public AnchorPane criar_processamento(String id) {
		  AnchorPane processamento;
		  try {
			  processamento = FXMLLoader.load(getClass().getResource("processamento.fxml"));
		  }catch(Exception e) {
			  processamento = new AnchorPane();
		  }
		  processamento.setId(id);
		  return processamento;
	  }
	
	public Processamento() {
		// TODO Auto-generated constructor stub
	}
}
