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
	
	private static String getTrechoCodigo(Fluxograma fluxo, AnchorPane ap) {
		
		String trecho = "";
		ArrayList<Associacao> lista = fluxo.getAssociacoesByPane(ap);
		int loop = 0;
		
		//buscar possivel loop
		for(Associacao a : lista) {
			if(a.getPane2().equals(ap)) {
				//loop
				loop = 0; //decidir como fazer loops futuramente
			}
		}
		
		if(loop == 0) {
			for(Associacao a : lista) {
				if(a.getPane1().equals(ap)) {
					if(ap.getId().contains("decisao")) {
						if(a.getLabel().getText().equals("Sim")) {
							if(trecho.equals("")) {
								trecho = "se variavel == VERDADEIRO entao\n" + getTrechoCodigo(fluxo, a.getPane2());
							}else {
								trecho = "se variavel == VERDADEIRO entao\n" + getTrechoCodigo(fluxo, a.getPane2()) + trecho;
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
