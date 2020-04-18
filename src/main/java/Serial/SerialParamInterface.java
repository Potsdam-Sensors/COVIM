package Serial;


import com.fazecast.jSerialComm.SerialPort;

public interface SerialParamInterface {
    String getPort();
    void setPort(String port);

    default int getBaudRate(){
        return 9600;
    }
    void setBaudRate(int baudRate);

    default int getDataBits(){
        return 8;
    }
    void setDataBits(int dataBits);

    default int getStopBits(){
        return 1;
    }
    void setStopBits(int stopBits);

    default int getParity(){
        return SerialPort.NO_PARITY;
    }
    void setParity(int partiy);

}
