package Loader;

import UI.Controller.DeviceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPortException;

public class Loader extends Application {

    public static boolean SHUTDOWN_ACTIVE = false;
    @Override
    public void start(Stage stage) throws Exception{
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
            DeviceController.currentReader.closePort();
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        System.out.println("Shut Down...");


    }

    public static void main(String[] args) {

        launch();
    }

}