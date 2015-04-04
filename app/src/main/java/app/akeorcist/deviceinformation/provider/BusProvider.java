package app.akeorcist.deviceinformation.provider;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Ake on 2/25/2015.
 */
public final class BusProvider {
    private static final Bus BUS_NETWORK = new Bus(ThreadEnforcer.ANY);
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
    public static Bus getNetworkInstance() {
        return BUS_NETWORK;
    }

    private BusProvider() { }
}