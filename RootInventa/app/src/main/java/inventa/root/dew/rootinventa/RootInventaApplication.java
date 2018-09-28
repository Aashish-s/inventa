package inventa.root.dew.rootinventa;

import android.support.multidex.MultiDexApplication;


import inventa.root.dew.rootinventa.receiver.DiscoveryBroadcastReceiver;
import proto.inventa.cct.com.inventalibrary.InventaSdk;
import proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper;

public class RootInventaApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
       // FirebaseApp.initializeApp(getApplicationContext());
        InventaSdk.sdkInitialize(getApplicationContext());
        DiscoveryHelper.setDiscoveryTransitionsReceiver(new DiscoveryBroadcastReceiver());
    }
}
