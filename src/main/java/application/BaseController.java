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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import util.Center;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import Services.ArquivoService;
import Services.FigurasService;
import Services.InicioService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import diagrama.Associacao;
import diagrama.FMX;
import diagrama.Fluxograma;
import diagrama.Tradutor;
import figuras.Console;
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
  JFXButton btn_minus, btn_max, btn_close;	
  
  @FXML
  JFXButton btn_inicio, btn_fim, btn_decisao, btn_processamento, btn_entrada, btn_saida;
  
  @FXML
  JFXButton btn_move, btn_associate, btn_remove;
  
  @FXML
  MenuItem mn_novo, mn_abrir, mn_salvar, mn_salvarcomo, mn_sair, mn_traduzir_pt, mn_sb_portugol, mn_sb_fluxogramas, mn_sobre;
  
  @FXML
  AnchorPane root;
  
  
  Canvas canvas = new Canvas ( 600, 300 );
  GraphicsContext ctx = canvas.getGraphicsContext2D ( );
  double x = 0, y = 0;
  
  private AnchorPane area_console;
  private FigurasService figurasService = new FigurasService();
  private InicioService inicialService;
  
  //estrutura dos dados
  private Fluxograma fluxograma;
  
  //console
  private JFXTextArea texto;
  
  public BaseController() throws Exception{
	  this.fluxograma = Fluxograma.getInstancia();
	  this.fluxograma.iniciaAssociacoes();
	  this.inicialService = InicioService.getInstancia();
  }
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
	  
	  btns ();
	  root.getChildren ( ).add ( canvas );
	  root.setCursor ( Cursor.CLOSED_HAND );
	  addConsole();
	  btn_move.setStyle("-fx-border-color: #fff;");
	  btn_associate.setStyle("");
	  btn_remove.setStyle("");
	  
  }
  
  private FMX carregarFluxograma() {
	  FMX processador_fluxograma =  new FMX();
	  processador_fluxograma.fluxograma2String(root,fluxograma);
	  return processador_fluxograma;
  }
  
  private void addConsole() {
	  area_console = new AnchorPane();
	  area_console.setPrefSize(200, 100);
	  
	  root.getChildren().add(area_console);
	  root.setBottomAnchor(area_console, 0.0);
	  root.setRightAnchor(area_console, 0.0);
	  
	  texto = new JFXTextArea();
	  texto.setEditable(false);
	  texto.setText("<< Console >>");
	  texto.setStyle("-fx-background-color: #a7aabe;");
	  
	  JFXButton btn_clear_console = new JFXButton(" ");
	  btn_clear_console.setStyle("-fx-background-color: #AAA;");
	  FontAwesomeIcon icon = new FontAwesomeIcon();
	  icon.setGlyphName("FILE_EXCEL_ALT");
	  btn_clear_console.setGraphic(icon);
	  btn_clear_console.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	  
	  area_console.getChildren().add(texto);
	  area_console.setBottomAnchor(texto, 2.0);
	  area_console.setLeftAnchor(texto, 10.0);
	  area_console.setRightAnchor(texto, 10.0);
	  area_console.setTopAnchor(texto, 2.0);
	  
	  area_console.getChildren().add(btn_clear_console);
	  area_console.setRightAnchor(btn_clear_console, 11.0);
	  area_console.setTopAnchor(btn_clear_console, 3.0);
	  
	  btn_clear_console.setOnAction(e -> {
		  texto.clear();
		  texto.setText("<< Console >>");
	  });  
  }
  
  private void btns () {
	  
	  btn_minus.setOnAction(e -> { // minimizar aplicacao
	      this.inicialService.minimizar();
	    });

	    btn_minus.setOnMouseEntered(e -> {
	      btn_minus.setStyle("-fx-background-color: #1b1b1b;");
	    });

	    btn_minus.setOnMouseExited(e -> {
	      btn_minus.setStyle("");
	    }); 
	  
	  btn_max.setOnAction(e -> { // maximizar aplicacao
	      int i = this.inicialService.maximizar();

	      if (i == 1) { // maximized
	        FontAwesomeIcon icon = new FontAwesomeIcon();
	        icon.setFill(Paint.valueOf("#ccc4c4"));
	        icon.setIcon(de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.COMPRESS);
	        btn_max.setGraphic(icon);
	      } else { // !maximized
	        FontAwesomeIcon icon = new FontAwesomeIcon();
	        icon.setFill(Paint.valueOf("#ccc4c4"));
	        icon.setIcon(de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.SQUARE_ALT);
	        btn_max.setGraphic(icon);
	      }
	    });

	    btn_max.setOnMouseEntered(e -> {
	      btn_max.setStyle("-fx-background-color: #1b1b1b;");
	    });

	    btn_max.setOnMouseExited(e -> {
	      btn_max.setStyle("");
	    });
	  
	  btn_close.setOnMouseEntered(e -> {
	      btn_close.setStyle("-fx-background-color: #1b1b1b;");
	    });

	    btn_close.setOnMouseExited(e -> {
	      btn_close.setStyle("");
	    });

	    btn_close.setOnAction(e -> { // fechar aplicacao
	    	if(ArquivoService.getInstance().checkAlteracoesNaoSalvas()) {
	    		System.exit(0);
	    	}
	    });
	  
	  mn_novo.setOnAction(e -> {
		  if (ArquivoService.getInstance().checkAlteracoesNaoSalvas()) {
			  ArquivoService.getInstance().fechar();
			  root.getChildren().clear();
			  addConsole();
			  fluxograma.reiniciar();
			  fluxograma = Fluxograma.getInstancia();
		  }
	  });
	  
	  mn_abrir.setOnAction(e -> {
		  boolean boo = ArquivoService.getInstance().abrir();
		  if (boo) {
			  String aberto = ArquivoService.getInstance().getConteudo();
			  root.getChildren().clear();
			  fluxograma.iniciaAssociacoes();
			  fluxograma.setFim(null);
			  fluxograma.setInicio(null);
			  root.getChildren().setAll(new FMX().string2Pane(aberto, fluxograma, figurasService).getChildren());
			  for(Associacao a : fluxograma.getAssociacoes()) {
				 figurasService.arrastaItens(root, a.getPane1(), a.getTipo_pane1(), fluxograma);
				 figurasService.arrastaItens(root, a.getPane2(), a.getTipo_pane2(), fluxograma);
				 figurasService.criar_linha(root, fluxograma, a);
			  }
			  addConsole();
		  }
	  });
	  
	  mn_salvar.setOnAction(e -> {
		  ArquivoService.getInstance().salvar(carregarFluxograma());
	  });
	  
	  mn_salvarcomo.setOnAction(e -> {
		  FMX aux = new FMX();
		  aux.fluxograma2String(root, fluxograma);
		  ArquivoService.getInstance().salvarComo(aux.getString());
	  });
	  
	  mn_traduzir_pt.setOnAction(e -> {
		  String portugol = Tradutor.getTraducao2Portugol(fluxograma);
		  System.out.println("Traducao:\n" + portugol);
	  });
	  
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
		  btn_move.setStyle("-fx-border-color: #fff;");
		  btn_associate.setStyle("");
		  btn_remove.setStyle("");
		  
		  //mouse_status = 1;//mover
		  figurasService.setMouse_status(1);
		  root.setCursor ( Cursor.CLOSED_HAND );
		  figurasService.setAssociarPane(null);
		  figurasService.setAssociarTipo(0);
	  });
	  
	  btn_remove.setOnAction(e->{
		  btn_remove.setStyle("-fx-border-color: #fff;");
		  btn_associate.setStyle("");
		  btn_move.setStyle("");
		  
		  //mouse_status = 2;//remover
		  figurasService.setMouse_status(2);
		  root.setCursor ( Cursor.CROSSHAIR );
		  figurasService.setAssociarPane(null);
		  figurasService.setAssociarTipo(0);
		  //System.out.println(btn_remove.getWidth() + " " + btn_remove.getHeight());
	  });
	  
	  btn_associate.setOnAction(e->{
		  btn_associate.setStyle("-fx-border-color: #fff;");
		  btn_move.setStyle("");
		  btn_remove.setStyle("");
		  
		  //mouse_status = 3;//ligacoes
		  figurasService.setMouse_status(3);
		  root.setCursor ( Cursor.HAND );
	  });
	  
  };
  
  private void cria_figura (AnchorPane ap, int tipo) {
      ap.setLayoutX(0);
      ap.setLayoutY(0);
      //arrastaItens ( ap , tipo );
      figurasService.arrastaItens(root, ap, tipo, fluxograma);
      root.getChildren ( ).add (ap);
  }
  
  private void sendMsgConsole(String msg) {
	  texto.appendText("\n" + msg);
  }

}
