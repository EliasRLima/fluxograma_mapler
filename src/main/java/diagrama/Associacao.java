package diagrama;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class Associacao {

	private AnchorPane pane1;
	private AnchorPane pane2;

	public Associacao(AnchorPane pane1, AnchorPane pane2) {
		super();
		this.pane1 = pane1;
		this.pane2 = pane2;
	}

	public AnchorPane getPane1() {
		return pane1;
	}

	private void setPane1(AnchorPane pane1) {
		this.pane1 = pane1;
	}

	public AnchorPane getPane2() {
		return pane2;
	}

	private void setPane2(AnchorPane pane2) {
		this.pane2 = pane2;
	}
	
}
