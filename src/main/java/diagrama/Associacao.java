package diagrama;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import listas.Tipos;

public class Associacao {

	private AnchorPane pane1;
	private AnchorPane pane2;
	private int tipo_pane1, tipo_pane2;

	public Associacao(AnchorPane pane1, int tp_pane1, AnchorPane pane2, int tp_pane2) {
		super();
		this.pane1 = pane1;
		this.pane2 = pane2;
		this.tipo_pane1 = tp_pane1;
		this.tipo_pane2 = tp_pane2;
		
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

	public int getTipo_pane1() {
		return tipo_pane1;
	}

	private void setTipo_pane1(int tipo_pane1) {
		this.tipo_pane1 = tipo_pane1;
	}

	public int getTipo_pane2() {
		return tipo_pane2;
	}

	private void setTipo_pane2(int tipo_pane2) {
		this.tipo_pane2 = tipo_pane2;
	}
	
	public boolean partindoDeDecisao() {
		return this.tipo_pane1==1?true:false;
	}
	
	
	
}
