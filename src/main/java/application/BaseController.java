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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
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
  
  //esta variavel representa a acao que o mouse ira fazer ao tocar uma figura: 1 = mover, 2 = remover, 3 = associar
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
	  });
	  
	  btn_remove.setOnAction(e->{
		  btn_remove.setStyle("-fx-border-color: #790b77;");
		  btn_associate.setStyle("");
		  btn_move.setStyle("");
		  
		  mouse_status = 2;//remover
	  });
	  
	  btn_associate.setOnAction(e->{
		  btn_associate.setStyle("-fx-border-color: #790b77;");
		  btn_move.setStyle("");
		  btn_remove.setStyle("");
		  
		  mouse_status = 3;//ligacoes
	  });
  };
  
  
  private void arrastaItens ( final AnchorPane figuras, int tipo ) {
      figuras.setOnMousePressed ( new EventHandler < MouseEvent > ( ) {
           @Override
           public void handle ( MouseEvent mouseEvent ) {
        	  if(mouse_status == 1) {
        		  x = figuras.getLayoutX ( ) - mouseEvent.getSceneX ( );
                  y = figuras.getLayoutY ( ) - mouseEvent.getSceneY ( );
                  figuras.setCursor ( Cursor.CROSSHAIR );
                  
                  //verificar se nao esta em associacao para refazer a linha
        	  }
           }
      } );
      figuras.setOnMouseReleased ( new EventHandler < MouseEvent > ( ) {
           @Override
           public void handle ( MouseEvent mouseEvent ) {
        	   if(mouse_status == 1) {
        		   figuras.setCursor ( Cursor.HAND );
         	  }else if(mouse_status == 2) {
         		  root.getChildren().remove(figuras);
         	  }else if(mouse_status == 3) {
         		  if(associarPane != null) {
         			  Associacao as = new Associacao(associarPane, associarTipo, figuras, tipo);
         			  fluxograma.novaAssociacao(as);
         			  criar_linha(as);
         			 
         			  associarPane = null;
        			  associarTipo = 0;
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
         	  }
                
           }
      } );
  }
  
  private void cria_figura (AnchorPane ap, int tipo) {
      ap.setLayoutX(0);
      ap.setLayoutY(0);
      arrastaItens ( ap , tipo );
      
      ap.setOnMouseClicked(e->{
    	  
      });
      
      root.getChildren ( ).add (ap);
  }
  
  
  private void criar_linha(Associacao as) {
	  
	  Center startCenter = new Center(as.getPane1());
	  Center endCenter   = new Center(as.getPane2());
	  
	  Line line = new Line(startCenter.centerXProperty().intValue(),
	          			   startCenter.centerYProperty().intValue(),
	          			   endCenter.centerXProperty().intValue(),
	          			   endCenter.centerYProperty().intValue());
	  
	  Line la = as.getLinha();
	  if (la != null) {
		  root.getChildren().remove(la);
	  }
	  as.setLine(line);
	  root.getChildren().add(line);
	  line.toBack();
	  
	  line.setOnMouseReleased(e -> {
		  if(mouse_status == 2) {
			  root.getChildren().remove(line);
		  }
	  });
  }

}
