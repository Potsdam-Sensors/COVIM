package Tools;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Timer;

public class Emulator {

    /**
     * Generate data over a parbola.
     * @return
     */
    public static int[] xGens = new int[13];

    public static void main(String args[]){

        SerialPort[] ports = SerialPort.getCommPorts();
        SerialPort port1 = null;
        SerialPort port2 = null;
        for (SerialPort port: ports) {
            if(port.getSystemPortName().equals("COM1")) port1 = port;
            if(port.getSystemPortName().equals("COM6")) port2 = port;
        }
        port1.openPort();
      //  port2.openPort();



        Timer timer = new Timer();
        timer.schedule(new DataSender(port1), 0, 5000);

    }
}
