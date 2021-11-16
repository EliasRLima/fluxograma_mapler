package figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Decisao {

	public AnchorPane criar_decisao() {
		  AnchorPane decisao;
		  try {
			  decisao = FXMLLoader.load(getClass().getResource("decisao.fxml"));
		  }catch(Exception e) {
			  decisao = new AnchorPane();
			  //System.out.println(e.getMessage());
		  }
		  return decisao;
	  }
	
	public Decisao() {
		// TODO Auto-generated constructor stub
	}
	
}
