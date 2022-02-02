package diagrama;


import figuras.Decisao;
import figuras.Entrada;
import figuras.Fim;
import figuras.Inicio;
import figuras.Processamento;
import figuras.Saida;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class FMX {
   //FLUXOGRAMA MAPLER X
	
	private String texto;
	
	public String getString() {
		return this.texto;
	}
	
	public void fluxograma2String(AnchorPane area_figuras, Fluxograma fluxograma) {
		String conteudo = "";
		String associacao = "";
		
		//salvar elementos e suas posicoes
		conteudo = "//elementos";
		for(Node a : area_figuras.getChildren() ) {
			if(a instanceof AnchorPane) {
				conteudo += "\n" + a.getId() +";"+ a.getLayoutX() + ";" + a.getLayoutY();
				
				if(fluxograma.getAssociacoesByPane((AnchorPane)a).size() == 0){
					continue;
				}
				
				for(Associacao ap : fluxograma.getAssociacoesByPane((AnchorPane)a)) {
					if(ap.getPane1() == a) {
						associacao += "\n"+ap.getPane1().getId()+";";
						if(ap.getTipo_pane1()==1) {
							associacao += ap.getLabel().getText() + ";";
						}else {
							associacao += "null;";
						}
						associacao += ap.getPane2().getId();
					}
				}
			}
			
		}
		
		//salvar as ligacoes entre os elementos
		conteudo += "\n//associacao";
		conteudo += associacao;
		
		this.texto = conteudo;

	}
	
	public Fluxograma string2Fluxograma(String conteudo) {
		//lembrar de verificar inicio e fim
				int i = 1;
				int lerfiguras = 0;
				int lerassociacao = 0;
				AnchorPane root = new AnchorPane();
				
				for(String linha : conteudo.split("\n")) {
					if(linha.equals("//elementos")) {
						lerfiguras = 1;
						lerassociacao = 0;
						continue;
					}
					
					if(linha.equals("//associacao")) {
						lerfiguras = 0;
						lerassociacao = 1;
						continue;
					}
					
					if(lerfiguras == 1) {
						continue;
					}
					
					if(lerassociacao == 1) {
						
					}
						
				}
				return null;
	}
	
	public AnchorPane string2Pane(String conteudo) {
		//lembrar de verificar inicio e fim
		int i = 1;
		int lerfiguras = 0;
		int lerassociacao = 0;
		AnchorPane root = new AnchorPane();
		
		for(String linha : conteudo.split("\n")) {
			if(linha.equals("//elementos")) {
				lerfiguras = 1;
				lerassociacao = 0;
				continue;
			}
			
			if(linha.equals("//associacao")) {
				lerfiguras = 0;
				lerassociacao = 1;
				continue;
			}
			
			if(lerfiguras == 1) {
				String[] elementos = linha.split(";");
				String id = elementos[0];
				String x = elementos[1];
				String y = elementos[2];
				
				if(id.contains("inicio")) {
					AnchorPane ini = new Inicio().criar_inicio(id);
					ini.setLayoutX(Double.parseDouble(x));
					ini.setLayoutY(Double.parseDouble(y));
					root.getChildren().add(ini);
				}else if(id.contains("fim")) {
					AnchorPane fim = new Fim().criar_fim(id);
					fim.setLayoutX(Double.parseDouble(x));
					fim.setLayoutY(Double.parseDouble(y));
					root.getChildren().add(fim);
				}else if(id.contains("entrada")) {
					AnchorPane entrada = new Entrada().criar_entrada(id);
					entrada.setLayoutX(Double.parseDouble(x));
					entrada.setLayoutY(Double.parseDouble(y));
					root.getChildren().add(entrada);
				}else if(id.contains("saida")) {
					AnchorPane saida = new Saida().criar_saida(id);
					saida.setLayoutX(Double.parseDouble(x));
					saida.setLayoutY(Double.parseDouble(y));
					root.getChildren().add(saida);
				}else if(id.contains("decisao")) {
					AnchorPane decisao = new Decisao().criar_decisao(id);
					decisao.setLayoutX(Double.parseDouble(x));
					decisao.setLayoutY(Double.parseDouble(y));
					root.getChildren().add(decisao);
				}else if(id.contains("processamento")) {
					AnchorPane processamento = new Processamento().criar_processamento(id);
					processamento.setLayoutX(Double.parseDouble(x));
					processamento.setLayoutY(Double.parseDouble(y));
					root.getChildren().add(processamento);
				}
			}
			
			if(lerassociacao == 1) {
				continue;
			}
				
		}
		return root;
	}
}
