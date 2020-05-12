package dk.via.trmo.anticheat;

import dk.via.trmo.anticheat.model.AntiCheatModel;
import dk.via.trmo.anticheat.model.Model;
import dk.via.trmo.anticheat.view.AntiCheatController;
import dk.via.trmo.anticheat.view.AntiCheatVM;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AntiCheatApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AntiCheatModel model = new Model();
        AntiCheatVM vm = new AntiCheatVM(model);

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("view/AntiCheatView.fxml"));
        Parent root = loader.load();

        AntiCheatController view = loader.getController();
        view.init(vm);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
