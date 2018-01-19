package arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import helpers.PropertiesHelper;
import helpers.PropertyConsts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by antiz_000 on 3/17/2016.
 */

public class ArduinoInitialiser {

    private static ArduinoInitialiser instance;

    private ArduinoInitialiser() throws InterruptedException, UnsupportedCommOperationException, PortInUseException, IOException {
        init();
    }

    public static ArduinoInitialiser getInstance() throws InterruptedException, IOException, UnsupportedCommOperationException, PortInUseException {
        if (instance != null)
            return instance;
        instance = new ArduinoInitialiser();
        return instance;
    }

    Enumeration portList;
    CommPortIdentifier portId;
    SerialPort serialPort;
    OutputStream outputStream;
    InputStream inputStream;
    ArduinoRequestAdapter arduinoRequestAdapter;

    public ArduinoRequestAdapter getArduinoRequestAdapter() throws InterruptedException, UnsupportedCommOperationException, PortInUseException, IOException {
        if (arduinoRequestAdapter != null)
            return arduinoRequestAdapter;
        init();
        return arduinoRequestAdapter;
    }

    public void init() throws InterruptedException, PortInUseException, IOException, UnsupportedCommOperationException {

        try {
            outputStream.close();
        } catch (Exception e){}
        try {
            inputStream.close();
        } catch (Exception e){}
        try {
            serialPort.close();
        } catch (Exception e){}

        portList = CommPortIdentifier.getPortIdentifiers();
        Properties pr = PropertiesHelper.getProperty(PropertyConsts.ARDUINO_PROPERTIES);
        String specifiedPort = pr.getProperty("port");
        while (portList.hasMoreElements()) {

            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                if (portId.getName().equals(specifiedPort)) {

                    serialPort = (SerialPort) portId.open(this.getClass().getName(), 2000);
                    Thread.sleep(3000);

                    outputStream = serialPort.getOutputStream();
                    serialPort.setSerialPortParams(9600,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    inputStream = serialPort.getInputStream();
                    Thread t = new Thread(new ArduinoListener(inputStream));
                    t.setDaemon(true);
                    t.start();
                    arduinoRequestAdapter = new ArduinoRequestAdapter(outputStream);
                }
            }
        }
    }
}
