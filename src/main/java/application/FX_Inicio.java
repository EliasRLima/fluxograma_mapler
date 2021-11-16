package application;

import java.net.URL;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Classe principal para executar a aplicação
 */

/*
 * <!-- --module-path "C:\javafx-sdk-17.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media,javafx.web -Djava.library.path="C:\javafx-sdk-17.0.1\lib" -->
*/
public class FX_Inicio extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		//InicioService.iniciarClasse(stage);
		//stage.initStyle(StageStyle.UNDECORATED); // removendo botoes padrao
		URL resources = getClass().getResource("");
		//System.out.println(resources.getPath());
		Parent root = FXMLLoader.load(getClass().getResource("fluxograma.fxml"));
		Scene scene = new Scene(root, 960, 720); // resolucao inicial
		stage.setScene(scene);
		stage.setTitle("MAPLER STUDIO - FLUXOGRAMA");
		stage.setMinHeight(500);
		stage.setMinWidth(600);

		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(javafx.beans.value.ObservableValue<? extends Boolean> arg0, Boolean arg1,
					Boolean arg2) {
				// ajustar cobrir barra
			}
		});

		//new ResizeListener().aplicarAoStage(stage);// adiciona resize listener ao stage
		stage.show();

		// inicial.maximizar();// iniciar maximizado
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		//BaseService.getInstancia().terminarTodosTerminaveis();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
