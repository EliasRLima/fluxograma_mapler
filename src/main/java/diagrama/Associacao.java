package diagrama;

import figuras.Tipos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class Associacao {

	private AnchorPane pane1;
	private AnchorPane pane2;
	private Tipos tipo_pane1, tipo_pane2;

	public Associacao(AnchorPane pane1,Tipos tp_pane1, AnchorPane pane2, Tipos tp_pane2) {
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

	public Tipos getTipo_pane1() {
		return tipo_pane1;
	}

	private void setTipo_pane1(Tipos tipo_pane1) {
		this.tipo_pane1 = tipo_pane1;
	}

	public Tipos getTipo_pane2() {
		return tipo_pane2;
	}

	private void setTipo_pane2(Tipos tipo_pane2) {
		this.tipo_pane2 = tipo_pane2;
	}
	
	public boolean temDecisao() {
		return this.tipo_pane1.isDecisao() || this.tipo_pane2.isDecisao();
	}
	
	
	
}
