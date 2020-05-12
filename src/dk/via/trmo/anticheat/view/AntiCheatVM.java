package dk.via.trmo.anticheat.view;

import dk.via.trmo.anticheat.model.AntiCheatModel;
import dk.via.trmo.anticheat.model.Model;
import dk.via.trmo.anticheat.model.containers.PairResult;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.Collections;

public class AntiCheatVM {

    private DoubleProperty progressProperty = new SimpleDoubleProperty(0.0);
    private StringProperty pathProperty = new SimpleStringProperty();
    private StringProperty suffixProperty = new SimpleStringProperty();
    private DoubleProperty thresholdProperty = new SimpleDoubleProperty();
    private StringProperty classesReadProperty = new SimpleStringProperty();
    private ObservableList<PairResult> list = FXCollections.observableArrayList();

    private AntiCheatModel model;

    public AntiCheatVM(AntiCheatModel model) {
        this.model = model;
        model.addListener("OnClassesRead", this::updateClassesRead);
        model.addListener("OnProgressChanged", this::updateProgress);
        model.addListener("OnResult", this::updateResults);
    }

    private void updateResults(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            PairResult value = (PairResult) evt.getNewValue();
            list.add(value);
            list.sort((a, b) -> {
                double v = b.similarity - a.similarity;
                if (v < 0) return -1;
                if (v == 0) return 0;
                return 1;
            });
        });
    }

    private void updateProgress(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            Double d = (Double) evt.getNewValue();
            progressProperty.set(d);
        });
    }

    private void updateClassesRead(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            Integer result = (Integer) evt.getNewValue();
            classesReadProperty.set(result + "");
        });
    }

    void calculate() {
        model.calculate(pathProperty.get(), suffixProperty.get(), thresholdProperty.get());
    }

    DoubleProperty progressProperty() {
        return progressProperty;
    }

    StringProperty pathProperty() {
        return pathProperty;
    }

    StringProperty suffixProperty() {
        return suffixProperty;
    }

    DoubleProperty thresholdProperty() {
        return thresholdProperty;
    }

    StringProperty classesReadProperty() {
        return classesReadProperty;
    }

    ObservableList<PairResult> getItems() {
        return list;
    }
}
