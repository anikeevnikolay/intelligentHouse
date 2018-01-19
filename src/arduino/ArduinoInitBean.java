package arduino;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;

/**
 * Created by antiz_000 on 3/19/2016.
 */
@Singleton
@Startup
public class ArduinoInitBean {

    @PostConstruct
    private void initArduino() {
        try {
            ArduinoInitialiser.getInstance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        }
    }
}
