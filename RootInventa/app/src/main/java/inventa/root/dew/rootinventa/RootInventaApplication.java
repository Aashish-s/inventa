package inventa.root.dew.rootinventa;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

import inventa.root.dew.rootinventa.receiver.DiscoveryBroadcastReceiver;
import proto.inventa.cct.com.inventalibrary.InventaSdk;
import proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper;

public class RootInventaApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        FirebaseApp.initializeApp(getApplicationContext());
        super.onCreate();
        InventaSdk.sdkInitialize(getApplicationContext());
        DiscoveryHelper.setDiscoveryTransitionsReceiver(new DiscoveryBroadcastReceiver());
    }
}
