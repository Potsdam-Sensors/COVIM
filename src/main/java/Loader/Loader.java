package Loader;

import UI.Controller.DeviceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Loader extends Application {

    public static boolean SHUTDOWN_ACTIVE = false;
    @Override
    public void start(Stage stage) throws Exception{
        Thread.setDefaultUncaughtExceptionHandler(Loader::showErr);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("root.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        SHUTDOWN_ACTIVE = true;
        System.out.println("Starting Shut Down");
        try {
            if(DeviceController.currentReader != null){
                DeviceController.currentReader.closePort();
            }
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Shut Down...");
    }

    private static void showErr(Thread t, Throwable e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Error Handler");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        System.exit(-1);
    }

    public static void main(String[] args) {

        launch();
    }

}