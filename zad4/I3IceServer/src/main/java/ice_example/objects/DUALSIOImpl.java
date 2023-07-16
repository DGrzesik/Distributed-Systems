package ice_example.objects;

import Demo.DUALSIO;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Util;

public class DUALSIOImpl implements DUALSIO {
    private final int servantId;

    public DUALSIOImpl(int id) {
        this.servantId = id;
        System.out.println("New DUALSIO servant " +"["+ id+"]"+" created");

    }

    @Override
    public long subtract(long a, long b, Current current) {
        System.out.println("subtract method of"+" servant " +"["+ servantId+"]"+ " called - identity: "+Util.identityToString(current.id));
        return a-b;
    }
}
