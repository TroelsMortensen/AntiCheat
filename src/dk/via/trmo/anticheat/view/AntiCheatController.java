package dk.via.trmo.anticheat.view;

import dk.via.trmo.anticheat.model.containers.PairResult;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class AntiCheatController {
    public TableView<PairResult> table;
    public TableColumn<PairResult, Double> similarityColumn;
    public TableColumn<PairResult, String> pathsColumn;

    public TextField pathTextField;
    public TextField suffixTextField;
    public TextField thresholdTextField;

    public Label classesRead;
    public ProgressBar progressBar;

    private AntiCheatVM vm;

    public void init(AntiCheatVM vm) {
        this.vm = vm;
        progressBar.progressProperty().bind(vm.progressProperty());
        pathTextField.textProperty().bindBidirectional(vm.pathProperty());
        suffixTextField.textProperty().bindBidirectional(vm.suffixProperty());

        Bindings.bindBidirectional(thresholdTextField.textProperty(), vm.thresholdProperty(), new StringConverter<Number>(){

            @Override
            public String toString(Number number) {
                return number.doubleValue() + "";
            }

            @Override
            public Number fromString(String s) {
                try {
                    return Double.parseDouble(s);
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }
        });

        classesRead.textProperty().bind(vm.classesReadProperty());

        table.setItems(vm.getItems());
        similarityColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().similarity));
        pathsColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(
                        cellData.getValue().cc1.absolutePath +
                        "\n" +
                        cellData.getValue().cc2.absolutePath
                )
        );
    }

    public void onCalculate() {
        vm.calculate();
    }
}
