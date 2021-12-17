package diagrama;

import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;
import listas.Tipos;

public class Tradutor {
	
	public static String getTraducao2Portugol(Fluxograma fluxograma) {
		
		String blocoVariaveis = "variaveis";
		String blocoInicio = "\ninicio";
		
		AnchorPane inicio = fluxograma.getInicio();
		AnchorPane fim = fluxograma.getFim();
		
		if(inicio == null || fim == null) {
			return null;
		}
		
		Associacao as = fluxograma.getAssociacoesByPane(inicio).get(0);
		
		while(as.getTipo_pane2() != Tipos.FIM.getValue()) {
			
		}
		
		
		return blocoVariaveis + blocoInicio + "\nfim";
	}
	
	private String getTrechoCodigo(Associacao as) {
		
		
		return null;
	}
	
	private String getTrechoDecisao(Associacao a, Associacao b) {
		
		
		return null;
	}
}
