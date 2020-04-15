package Serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialDataReader implements SerialPortEventListener {
    private SerialParamInterface params;
    private SerialPort port;
    private String lastReading;
    private boolean isDataFresh;

    public SerialDataReader(SerialParamInterface params) throws SerialPortException {
        this.params = params;
        this.port = new SerialPort(this.params.getPort());
        this.port.openPort();
        this.port.setParams(
                params.getBaudRate(),
                params.getDataBits(),
                params.getStopBits(),
                params.getParity()
        );
        this.port.purgePort(SerialPort.PURGE_RXABORT | SerialPort.PURGE_RXCLEAR);
        this.port.addEventListener(this);
        this.lastReading = null;
        this.isDataFresh = false;
    }

    public void openPort() throws SerialPortException {
        this.port.openPort();
    }

    public void closePort() throws SerialPortException {
        this.port.closePort();
    }

    //@TODO
    public void readPacket(){

    }

    public boolean isOpen(){
        return port.isOpened();
    }

    public String getLastReading(){
        return this.lastReading;
    }

    public boolean isDataFresh(){
        return this.isDataFresh;
    }
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        this.isDataFresh = false;
        System.out.println("Read: " + serialPortEvent.getEventValue());
        try {
            String reading = this.port.readString();
            this.lastReading = reading;
            this.isDataFresh = true;
            this.port.purgePort(SerialPort.PURGE_RXABORT | SerialPort.PURGE_RXCLEAR);
            System.out.println(reading);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }


}
