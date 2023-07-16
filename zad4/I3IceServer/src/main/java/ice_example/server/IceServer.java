package ice_example.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import ice_example.locators.URDIOLocator;
import ice_example.objects.DUALSIOImpl;
import ice_example.objects.UALSIOImpl;

public class IceServer {
    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args);

            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z");


            // add UALSIO servant as singleton object to ASM
            UALSIOImpl ualsio = new UALSIOImpl(1);
            Identity identity = new Identity("get","shared");
            adapter.add(ualsio,identity);

            // add URDIO servant locator
            URDIOLocator servantLocator = new URDIOLocator();
            adapter.addServantLocator(servantLocator,"dedicated");

            // add default DUALSIO servant
            DUALSIOImpl dualsio = new DUALSIOImpl(1);
            adapter.addDefaultServant(dualsio, "default");

            // activate adapter - start serving client
            adapter.activate();

            System.out.println("Entering event processing loop...");

            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace(System.err);
            status = 1;
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                status = 1;
            }
        }
        System.exit(status);
    }
}