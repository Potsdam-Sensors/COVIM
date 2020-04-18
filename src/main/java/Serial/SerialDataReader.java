package Serial;


import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.Arrays;

public class SerialDataReader implements SerialPortDataListener {
    private SerialParamInterface params;
    private SerialPort port;
    private String lastReading;
    private boolean isDataFresh;

    public SerialDataReader(SerialParamInterface params){
        this.params = params;
        this.port = SerialPort.getCommPort(this.params.getPort());
        boolean portOpen = this.port.openPort();
        System.out.println("Port Avail: " + portOpen);
        this.port.setComPortParameters(
                params.getBaudRate(),
                params.getDataBits(),
                params.getStopBits(),
                params.getParity()
        );
        this.port.addDataListener(this);
        this.lastReading = null;
        this.isDataFresh = false;
    }



    public void closePort(){
        //this.port.closePort();
    }


    public boolean isOpen(){
        return port.isOpen();
    }

    public String getLastReading(){
        return this.lastReading;
    }

    public boolean isDataFresh(){
        return this.isDataFresh;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        this.isDataFresh = false;
        System.out.println("Event: " + serialPortEvent);

        if(this.port == null){
            throw new NullPointerException("Port cannot be null");
        }
        if(serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;
        byte[] data = new byte[this.port.bytesAvailable()];
        this.port.readBytes(data, data.length);
        System.out.println(Arrays.toString(data));
        String reading = new String(data);
        if(reading != null){
            this.lastReading = reading;
            this.isDataFresh = true;
            System.out.println("SerialDataReader::59 Last Reading: " + this.lastReading);
        }

    }


}
