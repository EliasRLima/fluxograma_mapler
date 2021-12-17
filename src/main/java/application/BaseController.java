package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import util.Center;

import com.jfoenix.controls.JFXButton;

import diagrama.Associacao;
import diagrama.Fluxograma;
import figuras.Decisao;
import figuras.Entrada;
import figuras.Fim;
import figuras.Inicio;
import figuras.Processamento;
import figuras.Saida;

/**
 * Controller para fluxograma.fxml
 *
 */
public class BaseController implements Initializable {

  @FXML
  JFXButton btn_inicio, btn_fim, btn_decisao, btn_processamento, btn_entrada, btn_saida;
  
  @FXML
  JFXButton btn_move, btn_associate, btn_remove;
  
  @FXML
  AnchorPane root;
  
  Canvas canvas = new Canvas ( 600, 300 );
  GraphicsContext ctx = canvas.getGraphicsContext2D ( );
  double x = 0, y = 0;
  
  //esta variavel representa a acao que o mouse ira fazer ao tocar uma figura: 1 = mover, 2 = remover, 3 = associar, 4 - alterar decisao
  private int mouse_status = 1; //mover
  
  //controlar uniao entre dois elementos
  private AnchorPane associarPane = null;
  private int associarTipo = 0;
  
  //estrutura dos dados
  private Fluxograma fluxograma;
  
  public BaseController() throws Exception{
	  this.fluxograma = Fluxograma.getInstancia();
	  this.fluxograma.iniciaAssociacoes();
  }
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
	  
	  btns ();
	  root.getChildren ( ).add ( canvas );
	  root.setCursor ( Cursor.CLOSED_HAND );
	  
  }
  
  private void btns () {
	  
	  btn_processamento.setOnAction(e -> {
		  Processamento pt = new Processamento();
		  cria_figura(pt.criar_processamento(), 6);
	  });
	  
	  btn_saida.setOnAction(e -> {
		  Saida sd = new Saida();
		  cria_figura(sd.criar_saida(), 3);
	  });
	  
	  btn_entrada.setOnAction(e -> {
		  Entrada et = new Entrada();
		  cria_figura(et.criar_entrada(), 2);
	  });
	  
	  btn_inicio.setOnAction(e -> {
		  
		  if(!this.fluxograma.existeInicio()) {
			  Inicio in = new Inicio();
			  AnchorPane ap = in.criar_inicio();
			  cria_figura(ap, 5);
			  this.fluxograma.setInicio(ap);
		  }
		  
	  });
	  
	  btn_fim.setOnAction(e -> {
		  
		  if(!this.fluxograma.existeFim()) {
			  Fim fm = new Fim();
			  AnchorPane ap = fm.criar_fim();
			  cria_figura(ap, 4);
			  this.fluxograma.setFim(ap);
		  }
	  });
	  
	  btn_decisao.setOnAction(e -> {
		  Decisao dc = new Decisao();
		  cria_figura(dc.criar_decisao(), 1);
	  });
	  
	  //style="-fx-border-color: #790b77;"
	  btn_move.setOnAction(e->{
		  btn_move.setStyle("-fx-border-color: #790b77;");
		  btn_associate.setStyle("");
		  btn_remove.setStyle("");
		  
		  mouse_status = 1;//mover
		  root.setCursor ( Cursor.CLOSED_HAND );
		  associarPane = null;
		  associarTipo = 0;
	  });
	  
	  btn_remove.setOnAction(e->{
		  btn_remove.setStyle("-fx-border-color: #790b77;");
		  btn_associate.setStyle("");
		  btn_move.setStyle("");
		  
		  mouse_status = 2;//remover
		  root.setCursor ( Cursor.CROSSHAIR );
		  associarPane = null;
		  associarTipo = 0;
	  });
	  
	  btn_associate.setOnAction(e->{
		  btn_associate.setStyle("-fx-border-color: #790b77;");
		  btn_move.setStyle("");
		  btn_remove.setStyle("");
		  
		  mouse_status = 3;//ligacoes
		  root.setCursor ( Cursor.HAND );
	  });
	  
  };
  
  
  private void arrastaItens ( final AnchorPane figuras, int tipo ) {
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
                		  criar_linha(a);
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
                 		  criar_linha(a);
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
         				  System.out.println("associacao bloqueada.");
         				  associarPane = null;
         				  associarTipo = 0;
         			  }else {
         				  fluxograma.novaAssociacao(as);
            			  criar_linha(as);
            			 
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
                 		  criar_linha(a);
                 	  }
                   }
         	  }
                
           }
      } );
  }
  
  private void cria_figura (AnchorPane ap, int tipo) {
      ap.setLayoutX(0);
      ap.setLayoutY(0);
      arrastaItens ( ap , tipo );
      root.getChildren ( ).add (ap);
  }
  
  
  private void criar_linha(Associacao as) {
	  
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
