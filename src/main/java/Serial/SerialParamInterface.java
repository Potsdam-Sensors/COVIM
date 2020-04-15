package Serial;

import jssc.SerialPort;

public interface SerialParamInterface {
    String getPort();
    void setPort(String port);

    default int getBaudRate(){
        return SerialPort.BAUDRATE_9600;
    }
    void setBaudRate(int baudRate);

    default int getDataBits(){
        return SerialPort.DATABITS_8;
    }
    void setDataBits(int dataBits);

    default int getStopBits(){
        return SerialPort.STOPBITS_1;
    }
    void setStopBits(int stopBits);

    default int getParity(){
        return SerialPort.PARITY_NONE;
    }
    void setParity(int partiy);

}
