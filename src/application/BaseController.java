package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import com.jfoenix.controls.JFXButton;

/**
 * Controller para fluxograma.fxml
 *
 */
public class BaseController implements Initializable {

  @FXML
  JFXButton btn_inicio, btn_fim, btn_decisao, btn_processamento, btn_entrada, btn_saida;
  
  @FXML
  AnchorPane root;
  
  Canvas canvas = new Canvas ( 600, 300 );
  GraphicsContext ctx = canvas.getGraphicsContext2D ( );
  double x = 0, y = 0;
  private ArrayList <AnchorPane> nosObjct;
  
  AnchorPane processamento;
  
  Ellipse elipse = new Ellipse ( 30, 30 );
  Circle circle = new Circle ( 20 );
  Rectangle quadr = new Rectangle ( 40, 40 );
  Rectangle rect = new Rectangle ( 100, 40 );

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
	  
	  try {
		  processamento = FXMLLoader.load(getClass().getResource("processamento.fxml"));
	  }catch(Exception e) {
		  processamento = new AnchorPane();
	  }
	  
	  cria_figuras ( );
      root.getChildren ( ).add ( canvas );
      root.getChildren ( ).addAll ( nosObjct );
  }
  
  public void arrastaItens ( final AnchorPane figuras ) {
      figuras.setOnMousePressed ( new EventHandler < MouseEvent > ( ) {
           @Override
           public void handle ( MouseEvent mouseEvent ) {
                x = figuras.getLayoutX ( ) - mouseEvent.getSceneX ( );
                y = figuras.getLayoutY ( ) - mouseEvent.getSceneY ( );
                figuras.setCursor ( Cursor.CROSSHAIR );
           }
      } );
      figuras.setOnMouseReleased ( new EventHandler < MouseEvent > ( ) {
           @Override
           public void handle ( MouseEvent mouseEvent ) {
                figuras.setCursor ( Cursor.HAND );
           }
      } );
      figuras.setOnMouseDragged ( new EventHandler < MouseEvent > ( ) {
           @Override
           public void handle ( MouseEvent mouseEvent ) {
                figuras.setLayoutX ( mouseEvent.getSceneX ( ) + x );
                figuras.setLayoutY ( mouseEvent.getSceneY ( ) + y );
           }
      } );
  }
  
  public void cria_figuras ( ) {
      nosObjct = new ArrayList <> ( );
      processamento.setLayoutX(0);
      processamento.setLayoutY(0);
      nosObjct.add(processamento);
      for ( AnchorPane moveFiguras : nosObjct ) {
           arrastaItens ( moveFiguras );
      }
  }

}
