package Serial;

public class DefaultSerialParams implements SerialParamInterface {
    private int baudRate;
    private int dataBits;
    private int stopBits;
    private int partity;
    private String port;


    public DefaultSerialParams(String port){
        baudRate = this.getBaudRate();
        dataBits = this.getDataBits();
        stopBits = this.getStopBits();
        partity = this.getParity();
        this.port = port;
    }

    public void setBaudRate(int baudRate){
        this.baudRate = baudRate;
    }

    public void setDataBits(int dataBits){
        this.dataBits = dataBits;
    }
    public void setStopBits(int stopBits){
        this.stopBits = stopBits;
    }
    public void setParity(int parity){
        this.partity = parity;
    }

    public String getPort(){
        return this.port;
    }
    public void setPort(String port){
        this.port = port;
    }

}
