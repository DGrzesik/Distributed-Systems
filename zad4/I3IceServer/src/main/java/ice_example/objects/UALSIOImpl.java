package ice_example.objects;
import Demo.UALSIO;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Util;
public class UALSIOImpl implements UALSIO {
    private final int servantId;
    private int value = 0;

    public UALSIOImpl(int id) {
        servantId = id;
        System.out.println("New UALSIO servant " +"["+ servantId+"]"+" created");
    }
    @Override
    public int addGet(Current current) {
        value+=1;
        System.out.println("addGet method of"+" servant " +"["+ servantId+"]"+ " called - identity: "+Util.identityToString(current.id));
        return value;
    }
}
