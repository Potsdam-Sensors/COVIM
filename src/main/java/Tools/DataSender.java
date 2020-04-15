package Tools;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.Date;
import java.util.TimerTask;

public class DataSender extends TimerTask {
    private double x = Math.PI;

    private double a1= 2;
    private double a2 = 4;

    private double b = .3;
    private SerialPort port;
    private Date startTime;
    public DataSender(SerialPort port)
    {
        this.port = port;
        this.startTime = new Date();

    }


    public String genRandomData(){
        // 13 values sent over. See data_format
        String temp = "";
        Date now = new Date();
        long milli = now.getTime() - startTime.getTime();
        temp += milli + ",";
        temp += (a1 * Math.sin(x)) / (b * x) + ",";
        temp += (a2 * Math.sin(x) / (b * x));
        x += .1;
        return temp;
    }

    @Override
    public void run() {

        try {
            this.port.writeString(genRandomData());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

    }
}
