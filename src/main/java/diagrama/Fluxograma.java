package diagrama;

import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import listas.Tipos;

public class Fluxograma {
	
	private static Fluxograma instancia; //singleton
	
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
	
	public ArrayList<Associacao> getAssociacoes() {
		return fluxo;
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
	
	public void setInicio(AnchorPane i) {
		this.inicio = i;
	}
	
	public void setFim(AnchorPane f) {
		this.fim = f;
	}
	
	public AnchorPane getInicio() {
		return inicio;
	}

	public AnchorPane getFim() {
		return fim;
	}

	public void iniciaAssociacoes() {
		fluxo = new ArrayList<Associacao>();
	}
	
	public void novaAssociacao(Associacao as) {
		fluxo.add(as);
	}
	
	public boolean ligacaoCompleta() { //errado, tem de verificar se partindo de inicio chega em fim. //se tiver decisao tem de chegar em fim por ambos os fluxos
		int inicio = 0;
		int fim = 0;
		
		for(Associacao a : fluxo) {
			if(a.getTipo_pane1() == Tipos.INICIO.getValue()) {
				inicio = 1;
			}
			if(a.getTipo_pane2() == Tipos.FIM.getValue()) {
				fim = 1;
			}
			
			if(inicio == 1 && fim == 1) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public ArrayList<Associacao> getAssociacoesByPane(AnchorPane ap) {
		ArrayList<Associacao> associacoes = new ArrayList<Associacao>();
		for(Associacao as : fluxo) {
			if(as.getPane1().equals(ap) || as.getPane2().equals(ap)) {
				associacoes.add(as);
			}
		}
		return associacoes;
	}
	
	public void desfazerAssociacao(Associacao as) {
		this.fluxo.remove(as);
	}
	
	public boolean bloquearAssociacao(Associacao as) {
		
		//serie de verificacoes com os 2 panes, caso passe em todas é liberado adicionar a associacao
		
		//nao pode ser pane1 para mais de uma associacao //exceto decisao, mas este se limita a duas ligacoes
		if(as.getTipo_pane1() == Tipos.DECISAO.getValue()) {
			int cont = 0;
			for(Associacao a : fluxo) {
				if(a.getPane1().equals(as.getPane1())) {
					cont++;
					if(cont >= 2) {
						return true;
					}
				}
			}
		}else {
			for(Associacao a : fluxo) {
				if(a.getPane1().equals(as.getPane1())) {
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	public String nextTextoDecisao(Associacao as) { //busca se a decisao já tem associacao e retorna o texto inverso para nova associacao
		
		for(Associacao a : fluxo) {
			if(a.getPane1().equals(as.getPane1()) && !a.getPane2().equals(as.getPane2())) {
				if(a.getLabel().getText().equals("Sim")) {
					return "Nao";
				}else {
					return "Sim";
				}
			}
		}
		
		return "Sim";
	}
	
	public void inverterTextoDecisao(Associacao as) { //busca associacao a mesma decisao e inverte o texto
		for(Associacao a : fluxo) {
			if(a.getPane1().equals(as.getPane1()) && !a.getPane2().equals(as.getPane2())) {
				if(as.getLabel().getText().equals("Sim")) {
					a.getLabel().setText("Nao");
					a.getLabel().setTextFill(Color.RED);
				}else {
					a.getLabel().setText("Sim");
					a.getLabel().setTextFill(Color.GREEN);
				}
			}
		}
	}
	
}
