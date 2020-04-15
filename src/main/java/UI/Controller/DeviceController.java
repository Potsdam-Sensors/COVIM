package UI.Controller;

import Serial.DefaultSerialParams;
import Serial.SerialDataReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.util.List;

public class DeviceController {

    @FXML
    private ChoiceBox deviceChoiceBox;

    private String[] ports;
    private SerialDataReader reader;

    public static SerialDataReader currentReader;

    public DeviceController(ChoiceBox deviceChoiceBox){
        ports = SerialPortList.getPortNames();
        this.deviceChoiceBox = deviceChoiceBox;
        initialize();
    }

    public SerialDataReader getReader(){
        return this.getReader();
    }
    public void initialize(){
        if(ports.length == 0){
            if(ports.length == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Device Controller");
                alert.setHeaderText("Unable to find serial device");
                alert.setContentText("Make sure the device is plugged in and recognized by your operating system");
                alert.showAndWait();
                System.exit(-1);
            }

        }

        ObservableList<String> serialPorts = FXCollections.observableArrayList(ports);
        deviceChoiceBox.setValue(ports[0]);
        deviceChoiceBox.setItems(serialPorts);

        try {
            reader = new SerialDataReader(new DefaultSerialParams((String)deviceChoiceBox.getValue()));
        } catch (SerialPortException e) {
            System.err.println(e);
            // First port is being used. Try another.
            int i = 1;
            while(i < ports.length){
                try {
                    String newPort = (String)deviceChoiceBox.getItems().get(i);
                    reader = new SerialDataReader(new DefaultSerialParams(newPort));
                    currentReader = reader;
                    if(reader.isOpen()){
                        System.out.println("Opened: " + newPort);
                        // Data may have been buffered we may begin a read at the wrong offset.
                        deviceChoiceBox.setValue(newPort);
                        break;
                    }
                } catch (SerialPortException ex) {
                    System.err.println(ex);
                    System.out.println(String.format("Device %s in use", deviceChoiceBox.getItems().get(i)));
                }
                i++;
            }

        }

        deviceChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> {
                    try {
                        if(reader != null){
                            reader.closePort();
                        }
                        reader = new SerialDataReader(new DefaultSerialParams((String)deviceChoiceBox.getValue()));
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }

                }
        );


    }
}
