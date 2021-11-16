package diagrama;

import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;

public class Fluxograma {
	
	private static Fluxograma instancia;
	
	private AnchorPane inicio;
	private AnchorPane fim;
	private ArrayList<Associacao> fluxo;
	
	public Fluxograma() {
		// TODO Auto-generated constructor stub
	}
	
	public static Fluxograma getInstancia() {
		if (instancia == null)
			instancia = new Fluxograma();
		return instancia;
	}
	
	public boolean existeInicio() {
		if(inicio==null)
			return false;
		return true;
	}
	
	public boolean existeFim() {
		if(fim==null)
			return false;
		return true;
	}
	
	public void iniciaAssociacoes() {
		fluxo = new ArrayList<Associacao>();
	}
	
	public void novaAssociacao(Associacao as) {
		fluxo.add(as);
	}
	
	public Associacao buscaAssociacao(AnchorPane ap) {
		return null;
	}
	
}
