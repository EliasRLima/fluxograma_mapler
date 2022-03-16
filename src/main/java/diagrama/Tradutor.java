package diagrama;

import java.util.ArrayList;

import figuras.Decisao;
import figuras.Entrada;
import figuras.Fim;
import figuras.Inicio;
import figuras.Processamento;
import figuras.Saida;
import javafx.scene.layout.AnchorPane;
import listas.Tipos;

public class Tradutor {
	
	public static String getTraducao2Portugol(Fluxograma fluxograma) {
		
		String blocoVariaveis = "variaveis\n  variavel: cadeia;\n";
		String blocoInicio = "\ninicio\n";
		
		AnchorPane inicio = fluxograma.getInicio();
		AnchorPane fim = fluxograma.getFim();
		
		if(inicio == null || fim == null) {
			return null;
		}
		
		return blocoVariaveis + blocoInicio + Tradutor.getTrechoCodigo(fluxograma, inicio) + "\nfim";
	}
	
	private static String getCodigoLoop(Fluxograma fluxo, AnchorPane inicial, AnchorPane atual) {
		String bool = "false";
		String aux = null;
		
		if(inicial.equals(atual)) {
			return "";
		}else if(atual.getId().contains("fim")) {
			return "false";
		}else if(atual.getId().contains("decisao")) {
			ArrayList<Associacao> lista = fluxo.getAssociacoesByPane1(atual);
			for(Associacao a : lista) {
				bool = getCodigoLoop(fluxo, inicial, a.getPane2());
				if(bool.equals("false"))
					continue;
				
				if(aux==null) {
					aux = bool;
					if(a.getLabel().getText().toLowerCase().equals("sim")) {
						aux = "se variavel = \"valor\" entao\n" + aux;
					}else {
						aux = "senao\n" + aux;
					}
					bool = "";
				}else {
					if(a.getLabel().getText().toLowerCase().equals("sim")) {
						bool = "se variavel = \"valor\"  entao\n" + bool;
					}else {
						bool = "senao\n" + bool;
					}
				}
				
			}
			if(aux != null) {
				if(bool.contains("senao")) {
					return aux + bool + "fim se;\n";
				}else {
					if(bool.contains("se variavel == \"valor\"  entao")) {
						return bool + aux + "fim se;\n";
					}else if(bool.equals("") && aux.contains("senao")) { //caso so tenha o caminho "nao" no se
						return aux.replace("= \"valor\" ", "nao \"valor\" ") + "fim se;\n"; 
					}else {
						return aux + "fim se;\n"; //caso so tenha o caminho "sim" no se
					}
				}
			}else {
				if(bool.contains("senao")) {
					return aux + bool + "fim se;\n";
				}else {
					return bool + "fim se;\n";
				}
			}
		}else {
			ArrayList<Associacao> lista = fluxo.getAssociacoesByPane1(atual);
			bool = getCodigoByPane(fluxo, atual) + getCodigoLoop(fluxo,inicial,lista.get(0).getPane2());
		}
		
		return bool;
	}
	
	private static String getTrechoCodigo(Fluxograma fluxo, AnchorPane ap) {
		
		String trecho = "";
		ArrayList<Associacao> lista = fluxo.getAssociacoesByPane(ap);
		int loop = 0;
		
		//buscar possivel loop 
		ArrayList<Associacao> two = fluxo.getAssociacoesByPane2(ap);
		if(two.size()>=2) {
			String isLoop = "";
			
			//partindo de ap, chega até ap?
			if(ap.getId().contains("decisao")) {
				for(Associacao prox : fluxo.getAssociacoesByPane1(ap)) {
					isLoop += getCodigoByPane(fluxo, ap) + getCodigoLoop(fluxo, ap, prox.getPane2()); //tratar quando iniciar com decisao
				}
			}else {
				for(Associacao prox : fluxo.getAssociacoesByPane1(ap)) {
					isLoop += getCodigoByPane(fluxo, ap) + getCodigoLoop(fluxo, ap, prox.getPane2());
				}
			}
			
			
			if(!isLoop.equals("false") || isLoop.equals("")) {
				loop = 1;
				trecho = "enquanto varivavel nao \"valor\" faca\n" + isLoop + "fim enquanto;";
			}
		}
		
		if(loop == 0) {
			for(Associacao a : lista) {
				if(a.getPane1().equals(ap)) {
					if(ap.getId().contains("decisao")) {
						if(a.getLabel().getText().equals("Sim")) {
							if(trecho.equals("")) {
								trecho = "se variavel = \"valor\" entao\n" + getTrechoCodigo(fluxo, a.getPane2());
							}else {
								trecho = "se variavel = \"valor\" entao\n" + getTrechoCodigo(fluxo, a.getPane2()) + trecho;
							}
						}else {
							if(trecho.equals("")) {
								trecho = "senao\n" + getTrechoCodigo(fluxo, a.getPane2());
							}else {
								trecho = trecho + "senao\n" + getTrechoCodigo(fluxo, a.getPane2());
							}
						}
					}else {
						trecho = getCodigoByPane(fluxo, ap) + getTrechoCodigo(fluxo, a.getPane2());
					}
				}
			}
			
			if(ap.getId().contains("decisao")) {
				trecho += "fim se;\n";
			}
		}
		
		return trecho;
	}
	
	
	
	private static String getCodigoByPane(Fluxograma flx,AnchorPane ap) {
		
		if(ap.getId().contains("inicio")) {
			return "";
		}else if(ap.getId().contains("fim")) {
			return "";
		}else if(ap.getId().contains("entrada")) {
			return "leia variavel;\n";
		}else if(ap.getId().contains("saida")) {
			return "escrever variavel;\n";
		}else if(ap.getId().contains("decisao")) {
			return "";
		}else if(ap.getId().contains("processamento")) {
			return "variavel <- \"valor\";\n";
		}
		
		return null;
	}
}
