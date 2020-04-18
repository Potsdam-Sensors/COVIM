package UI.Controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ChoiceBox;

public class MainController {
    @FXML
    private ChoiceBox<String> deviceChoiceBox;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    public static DeviceController deviceController;
    public static GraphController graphController;

    public void initialize(){
        deviceController = new DeviceController(deviceChoiceBox);
        graphController  = new GraphController(lineChart);
    }
}
