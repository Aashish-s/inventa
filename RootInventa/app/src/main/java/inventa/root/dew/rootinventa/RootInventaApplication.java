package inventa.root.dew.rootinventa;

import android.app.Application;

import inventa.root.dew.rootinventa.receiver.DiscoveryBroadcastReceiver;
import proto.inventa.cct.com.inventalibrary.InventaSdk;
import proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper;

public class RootInventaApplication extends Application {
    @Override
    public void onCreate() {
        InventaSdk.sdkInitialize(getApplicationContext());
        DiscoveryHelper.setDiscoveryTransitionsReceiver(new DiscoveryBroadcastReceiver());
    }
}
