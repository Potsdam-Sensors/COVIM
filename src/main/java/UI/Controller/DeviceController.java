package UI.Controller;

import Serial.DefaultSerialParams;
import Serial.SerialDataReader;
import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;


import java.util.Arrays;

import static java.lang.String.*;

public class DeviceController {

    @FXML
    private ChoiceBox<String> deviceChoiceBox;

    private String[] portNames;
    private SerialDataReader reader;

    public static SerialDataReader currentReader;

    public DeviceController(ChoiceBox<String> deviceChoiceBox){
        SerialPort[] portList = SerialPort.getCommPorts();
        portNames =  new String[portList.length];
        for(int i = 0; i < portList.length; i++){
            portNames[i] = portList[i].getSystemPortName();
        }
        System.out.println(Arrays.toString(portNames));
        this.deviceChoiceBox = deviceChoiceBox;
        initialize();
    }

    public void initialize(){
        if(portNames.length == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Device Controller");
            alert.setHeaderText("Unable to find serial device");
            alert.setContentText("Make sure the device is plugged in and recognized by your operating system");
            alert.showAndWait();
            System.exit(-1);
        }

        ObservableList<String> serialPorts = FXCollections.observableArrayList(portNames);
        deviceChoiceBox.setValue(portNames[1]);
        deviceChoiceBox.setItems(serialPorts);


        reader = new SerialDataReader(new DefaultSerialParams(deviceChoiceBox.getValue()));
        currentReader = reader;

        System.out.println("Opened: " + deviceChoiceBox.getValue());
        // First port is being used. Try another.
//       if(!currentReader.isOpen()) {
//           int i = 1;
//           while (i < portNames.length && this.currentReader != null) {
//               String newPort = (String) deviceChoiceBox.getItems().get(i);
//               reader = new SerialDataReader(new DefaultSerialParams(newPort));
//               currentReader = reader;
//               if (reader.isOpen()) {
//                   System.out.println("Opened: " + newPort);
//                   deviceChoiceBox.setValue(newPort);
//                   System.out.println("Found reader");
//                   break;
//               } else {
//                   System.out.println(format("Device %s in use", deviceChoiceBox.getItems().get(i)));
//                   currentReader = null;
//               }
//               i++;
//           }
//
//           if (i == portNames.length && !reader.isOpen()) {
//               Alert alert = new Alert(Alert.AlertType.ERROR);
//               alert.setTitle("Device Controller");
//               alert.setHeaderText("Unable to open serial device");
//               alert.setContentText("Make sure the device is plugged in and is not currently in use.");
//               alert.showAndWait();
//               System.exit(-1);
//
//           }
//       }
        deviceChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> {
                    if(reader != null){
                        reader.closePort();
                    }
                    System.out.println("Opening: " + deviceChoiceBox.getValue());
                    reader = new SerialDataReader(new DefaultSerialParams(deviceChoiceBox.getValue()));
                });
    }

}
