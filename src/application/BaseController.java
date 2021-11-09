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
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
	  
	  btns ();
	  root.getChildren ( ).add ( canvas );
      
  }
  
  private void btns () {
	  
	  btn_processamento.setOnAction(e -> {
		  cria_figura(criar_processamento());
	  });
  };
  
  
  private void arrastaItens ( final AnchorPane figuras ) {
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
  
  private void cria_figura (AnchorPane ap) {
      ap.setLayoutX(0);
      ap.setLayoutY(0);
      arrastaItens ( ap );
      root.getChildren ( ).add (ap);
  }
  
  private AnchorPane criar_processamento() {
	  AnchorPane processamento;
	  try {
		  processamento = FXMLLoader.load(getClass().getResource("processamento.fxml"));
	  }catch(Exception e) {
		  processamento = new AnchorPane();
	  }
	  return processamento;
  }

}
