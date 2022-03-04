package Services;

import java.util.ArrayList;

import diagrama.Associacao;
import diagrama.Fluxograma;
import figuras.Seta;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import util.Center;

public class FigurasService {
  //essa classe representa serviços padroes para as figuras
	
	private double x = 0, y = 0;
	
	//controlar uniao entre dois elementos
	private AnchorPane associarPane = null;
	private int associarTipo = 0;
	
	//esta variavel representa a acao que o mouse ira fazer ao tocar uma figura: 1 = mover, 2 = remover, 3 = associar, 4 - alterar decisao
	private int mouse_status = 1;
	
	public FigurasService() {
		// TODO Auto-generated constructor stub
	}

	public AnchorPane getAssociarPane() {
		return associarPane;
	}

	public void setAssociarPane(AnchorPane associarPane) {
		this.associarPane = associarPane;
	}

	public int getAssociarTipo() {
		return associarTipo;
	}

	public void setAssociarTipo(int associarTipo) {
		this.associarTipo = associarTipo;
	}
	
	public int getMouse_status() {
		return mouse_status;
	}

	public void setMouse_status(int mouse_status) {
		this.mouse_status = mouse_status;
	}

	public void arrastaItens (AnchorPane root, final AnchorPane figuras, int tipo, Fluxograma fluxograma) {
		
	      figuras.setOnMousePressed ( new EventHandler < MouseEvent > ( ) {
	           @Override
	           public void handle ( MouseEvent mouseEvent ) {
	        	  if(mouse_status == 1) {
	        		  x = figuras.getLayoutX ( ) - mouseEvent.getSceneX ( );
	                  y = figuras.getLayoutY ( ) - mouseEvent.getSceneY ( );
	                  
	                  
	                  //verificar se nao esta em associacao para refazer a linha
	                  ArrayList<Associacao> ascc = fluxograma.getAssociacoesByPane(figuras);
	                  if(ascc.size() > 0) {
	                	  for(Associacao a : ascc) {
	                		  criar_linha(root, fluxograma, a);
	                	  }
	                  }
	        	  }
	           }
	      } );
	      figuras.setOnMouseReleased ( new EventHandler < MouseEvent > ( ) {
	           @Override
	           public void handle ( MouseEvent mouseEvent ) {
	        	   if(mouse_status == 1) {
	        		   
	        		 //verificar se nao esta em associacao para refazer a linha
	                   ArrayList<Associacao> ascc = fluxograma.getAssociacoesByPane(figuras);
	                   if(ascc.size() > 0) {
	                 	  for(Associacao a : ascc) {
	                 		 criar_linha(root, fluxograma, a);
	                 	  }
	                   }
	                   
	         	  }else if(mouse_status == 2) {
	         		  root.getChildren().remove(figuras);
	         		  
	         		  if(tipo == 4) {
	         			  fluxograma.setFim(null); //resetando o fim
	         		  }else if(tipo == 5) {
	         			  fluxograma.setInicio(null); //resetando o inicio
	         		  }
	         		  
	         		//verificar se nao esta em associacao para desfazer a linha
	                  ArrayList<Associacao> ascc = fluxograma.getAssociacoesByPane(figuras);
	                  if(ascc.size() > 0) {
	                	  for(Associacao a : ascc) {
	                		  
	                		  Line la = a.getLinha();
	                		  if (la != null) {
	                			  root.getChildren().remove(la);
	                		  }
	                		  a.setLine(null);
	                		  
	                		  Label lb = a.getLabel();
	                		  if(lb != null) {
	                			  root.getChildren().remove(lb);
	                		  }
	                		  a.setLabel(null);
	                		  
	                		  Seta st = a.getSeta();
	                		  if(st != null) {
	                			  root.getChildren().remove(st);
	                		  }
	                		  a.setSeta(null);
	                		  
	                		  fluxograma.desfazerAssociacao(a);
	                	  }
	                  }
	         		  
	         	  }else if(mouse_status == 3) {
	         		  if(associarPane != null) {
	         			 Associacao as;
	         			  if(associarTipo == 4 || tipo == 5) {
	         				 as = new Associacao(figuras, tipo, associarPane, associarTipo);
	         			  }else {
	         				 as = new Associacao(associarPane, associarTipo, figuras, tipo);
	         			  }
	         			  
	         			  boolean bloquear = fluxograma.bloquearAssociacao(as);
	         			  if(bloquear) {
	         				  //sendMsgConsole("associacao bloqueada.");
	         				  associarPane = null;
	         				  associarTipo = 0;
	         			  }else {
	         				  fluxograma.novaAssociacao(as);
	         				  criar_linha(root, fluxograma, as);
	            			  associarPane = null;
	            			  associarTipo = 0;
	         			  }
	         			  
	         		  }else {
	         			  associarPane = figuras;
	         			  associarTipo = tipo;
	         		  }
	         	  }
	        	   
	               
	           }
	      } );
	      figuras.setOnMouseDragged ( new EventHandler < MouseEvent > ( ) {
	           @Override
	           public void handle ( MouseEvent mouseEvent ) {
	        	   if(mouse_status == 1) {
	        		   figuras.setLayoutX ( mouseEvent.getSceneX ( ) + x );
	                   figuras.setLayoutY ( mouseEvent.getSceneY ( ) + y );
	                   
	                 //verificar se nao esta em associacao para refazer a linha
	                   ArrayList<Associacao> ascc = fluxograma.getAssociacoesByPane(figuras);
	                   if(ascc.size() > 0) {
	                 	  for(Associacao a : ascc) {
	                 		  criar_linha(root, fluxograma, a);
	                 	  }
	                   }
	         	  }
	                
	           }
	      } );
	  }
	
	/*public void criar_linha(AnchorPane root, Fluxograma fluxograma, Associacao as) {
		  
		  Seta line = new Seta();
		  
		  Center pane1 = new Center(as.getPane1());
		  Center pane2 = new Center(as.getPane2());
		  
		  line.setStartX(pane1.centerXProperty().intValue());
		  line.setStartY(pane1.centerYProperty().intValue());
		  
		  if(pane1.centerXProperty().intValue() > pane2.centerXProperty().intValue()) {
			  int x = (pane1.centerXProperty().intValue() - pane2.centerXProperty().intValue())%10;
			  line.setEndX(pane2.centerXProperty().intValue() + x);
		  }else {
			  int x = (pane2.centerXProperty().intValue() - pane1.centerXProperty().intValue())%10;
			  line.setEndX(pane2.centerXProperty().intValue() - x);
		  }
		  
		  if(pane1.centerYProperty().intValue() > pane2.centerYProperty().intValue()) {
			  int y = (pane1.centerYProperty().intValue() - pane2.centerYProperty().intValue())%10;
			  line.setEndY(pane2.centerYProperty().intValue() + y);
		  }else {
			  int y = (pane2.centerYProperty().intValue() - pane1.centerYProperty().intValue())%10;
			  line.setEndY(pane2.centerYProperty().intValue() - y);
		  }
		  
		  
		  
		  line.setOnMouseClicked(e -> {
			  if(mouse_status == 2) {
				  root.getChildren().remove(line);
				  as.setLine(null);
				  fluxograma.desfazerAssociacao(as);
			  }
		  });
		  
		  Seta la = as.getLinha();
		  if (la != null) {
			  root.getChildren().remove(la);
		  }
		  as.setLine(line);
		  
		  root.getChildren().add(line);
		  as.getPane1().toFront();
		  as.getPane2().toFront();
		  
	  }*/
	
	public void criar_linha(AnchorPane root, Fluxograma fluxograma, Associacao as) {
		  
		  Center startCenter = new Center(as.getPane1());
		  Center endCenter   = new Center(as.getPane2());
		  
		  Line line = new Line(startCenter.centerXProperty().intValue(),
		          			   startCenter.centerYProperty().intValue(),
		          			   endCenter.centerXProperty().intValue(),
		          			   endCenter.centerYProperty().intValue());
		  

		  Label lb = as.getLabel();
		  if (lb != null) {
			  root.getChildren().remove(lb);
		  }else {
			  as.setLabel(new Label(fluxograma.nextTextoDecisao(as)));
			  if(as.getLabel().getText().equals("Sim")) {
				  as.getLabel().setTextFill(Color.GREEN);
			  }else {
				  as.getLabel().setTextFill(Color.RED);
			  }
		  }
		  Label lab = as.getLabel();
		  
		  lab.setOnMouseClicked(e -> {
			  if(lab.getText().equals("Sim")) {
				  lab.setText("Nao");
				  lab.setTextFill(Color.RED);
			  }else {
				  lab.setText("Sim");
				  lab.setTextFill(Color.GREEN);
			  }
			  fluxograma.inverterTextoDecisao(as);
		  });
		  
		  int x = (endCenter.centerXProperty().intValue() + startCenter.centerXProperty().intValue())/2;
		  int y = (endCenter.centerYProperty().intValue() + startCenter.centerYProperty().intValue())/2;
		  lab.setLayoutX(x+3);
		  lab.setLayoutY(y+3);
		  
		  Seta seta = new Seta();
		  seta.setStartX(startCenter.centerXProperty().intValue());
		  seta.setStartY(startCenter.centerYProperty().intValue()); //25% position
		  seta.setEndX((endCenter.centerXProperty().intValue() + startCenter.centerXProperty().intValue())/2);
		  seta.setEndY((endCenter.centerYProperty().intValue() + startCenter.centerYProperty().intValue())/2); 
		  Seta st = as.getSeta();
		  if(st != null) {
			  root.getChildren().remove(st);
		  }
		  as.setSeta(seta);
		  root.getChildren().add(seta);
		  
		  if(as.getTipo_pane1() == 1) {
			  //line.setStyle("-fx-stroke-width: 3;-fx-stroke: lime");
			  line.setCursor(Cursor.CLOSED_HAND);
			  lab.setCursor(Cursor.CLOSED_HAND);
			  as.setLabel(lab);
			  root.getChildren().add(lab);
			  lab.toFront();
		  }
		  
		  line.setOnMouseClicked(e -> {
			  if(mouse_status == 2) {
				  root.getChildren().remove(line);
				  as.setLine(null);
				  fluxograma.desfazerAssociacao(as);
			  }
		  });
		  
		  Line la = as.getLinha();
		  if (la != null) {
			  root.getChildren().remove(la);
		  }
		  as.setLine(line);
		  root.getChildren().add(line);
		  as.getPane1().toFront();
		  as.getPane2().toFront();
		  
	  }
	
	
}
