package Services;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Service para facilitar a comunicação do stage principal 
 * com outros controllers e encapsular a logica de resize do stage (maximizar, minimizar...)
 */
public final class InicioService {

  private static InicioService instancia; // singleton | instanca unica
  private Stage janela; // janela principal
  private Rectangle2D bounds;

  private InicioService(Stage janela) {
    this.janela = janela;
    // pegando o tamanho da tela
    Screen screen = Screen.getPrimary();
    bounds = screen.getVisualBounds();
    
    janela.setMaxWidth(bounds.getMaxX());
    janela.setMaxWidth(bounds.getMaxY());
  }

  /**
   * Inicia a classe com o stage principal
   * 
   * @param janela - principal stage da aplicação
   * @return - instancia criada da classe
   */
  public static InicioService iniciarClasse(Stage janela) {
    instancia = new InicioService(janela);
    return instancia;
  }

  /**
   * 
   * @return - a instancia unica da classe
   * @throws Exception
   */
  public static InicioService getInstancia() throws Exception {
    if (instancia == null)
      throw new Exception("Instancia da classe InicialService não foi criada!");
    return instancia;
  }

  /**
   * Minimiza o stage principal
   */
  public void minimizar() {
    this.janela.setIconified(true);
  }

  /**
   * maximiza o stage principal
   * 
   * @return - 0: stage não maximizado 1: stage maximizado
   */
  public int maximizar() {
    if (this.janela.isMaximized()) {
      janela.setMaximized(false);
      return 0;
    } else {
      janela.setMaximized(true);
      janela.setX(bounds.getMinX());
      janela.setY(bounds.getMinY());
      janela.setWidth(bounds.getWidth());
      janela.setHeight(bounds.getHeight());
      return 1;
    }
  }
  

}
