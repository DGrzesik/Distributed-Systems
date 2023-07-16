package ice_example.locators;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ServantLocator;
import ice_example.objects.URDIOImpl;

public class URDIOLocator implements ServantLocator {

    private int id=1;

    public void finished(Current current, Object servant, java.lang.Object cookie) {
    }

    @Override
    public LocateResult locate(Current curr){
        URDIOImpl urdio = new URDIOImpl(id++);
        return new ServantLocator.LocateResult(urdio, null);
    }

    public void deactivate(String category) {}
}
