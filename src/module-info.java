module fluxograma_mapler {
	requires javafx.controls;
	requires com.jfoenix;
    requires javafx.fxml;
	requires fontawesomefx;
	
	opens application to javafx.graphics, javafx.fxml;
}