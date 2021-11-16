package application;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;

class Center {
    private ReadOnlyDoubleWrapper centerX = new ReadOnlyDoubleWrapper();
    private ReadOnlyDoubleWrapper centerY = new ReadOnlyDoubleWrapper();

    public Center(Node node) {
        calcCenter(node.getBoundsInParent());
        node.boundsInParentProperty().addListener((ChangeListener<? super Bounds>) new ChangeListener<Bounds>() {
            @Override public void changed(
                   ObservableValue<? extends Bounds> observableValue, 
                   Bounds oldBounds, 
                   Bounds bounds
            ) {
                calcCenter(bounds);
            }
        });
    }

    private void calcCenter(Bounds bounds) {
        centerX.set(bounds.getMinX() + bounds.getWidth()  / 2);
        centerY.set(bounds.getMinY() + bounds.getHeight() / 2);
    }

    ReadOnlyDoubleProperty centerXProperty() {
        return centerX.getReadOnlyProperty();
    }

    ReadOnlyDoubleProperty centerYProperty() {
        return centerY.getReadOnlyProperty();
    }
}
