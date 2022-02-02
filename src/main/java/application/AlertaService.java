package application;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
//import mapler.model.resource.Estilos;
//import mapler.util.CarregadorRecursos;

/**
 * Exibe alertas da IDE
 */
public final class AlertaService {

	private AlertaService() {
	}

	private static Alert criarJanela(Alert.AlertType alertType, String aviso, String titulo) {
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
		DialogPane dialogPane = dialogoInfo.getDialogPane();
		//dialogPane.getStylesheets().add(CarregadorRecursos.get().getResourceExternalForm(Estilos.ARLETAS.getUrl()));
		dialogPane.getStyleClass().add("myDialog");
		dialogoInfo.setTitle(titulo);
		dialogoInfo.setHeaderText(aviso);
		dialogoInfo.setContentText(" ");

		return dialogoInfo;
	}

	public static void showAviso(String aviso) {
		criarJanela(Alert.AlertType.INFORMATION, aviso, "Aviso").showAndWait();
	}

	public static int showConfirm(String aviso) {
		Alert dialogoInfo = criarJanela(Alert.AlertType.CONFIRMATION, aviso, "Confirmação");
		ButtonType btnSim = new ButtonType("Sim");
		ButtonType btnNao = new ButtonType("Não");
		ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

		dialogoInfo.getButtonTypes().setAll(btnSim, btnNao, btnCancelar);
		Optional<ButtonType> result = dialogoInfo.showAndWait();

		if (result.isPresent() && result.get() == btnSim) {
			return 1;
		} else if (result.isPresent() && result.get() == btnNao) {
			return 0;
		} else {
			return -1;
		}
	}
}
