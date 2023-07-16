package ice_example.objects;

import Demo.URDIO;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Util;

public class URDIOImpl implements URDIO {
    private final int servantId;

    public URDIOImpl(int servantId) {
        this.servantId = servantId;
        System.out.println("New URDIO servant " +"["+ servantId+"]"+" created");

    }

    @Override
    public void updateDescription(String description, Current current) {
        System.out.println("updatedDescription: "+ description);
        //this is only a decoy so no actual description updating happens
        System.out.println("updateDescription method of"+" servant " +"["+ servantId+"]"+ " called - identity: "+ Util.identityToString(current.id));

    }
}
