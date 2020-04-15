package Tools;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.Timer;

public class Emulator {

    /**
     * Generate data over a parbola.
     * @return
     */
    public static int[] xGens = new int[13];

    public static void main(String args[]){

        SerialPort serialPort = new SerialPort("COM1");

        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }

        Timer timer = new Timer();
        timer.schedule(new DataSender(serialPort), 0, 5000);

    }
}
