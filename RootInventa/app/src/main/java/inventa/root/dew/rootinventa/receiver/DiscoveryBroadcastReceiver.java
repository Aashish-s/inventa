package inventa.root.dew.rootinventa.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import inventa.root.dew.rootinventa.MainActivity;
import inventa.root.dew.rootinventa.model.NotificationDataAttributes;
import inventa.root.dew.rootinventa.model.NotificationDataModel;
import inventa.root.dew.rootinventa.model.NotificationTypeFormat;
import proto.inventa.cct.com.inventalibrary.InventaSdk;
import proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper;
import proto.inventa.cct.com.inventalibrary.models.StoreDataModel;
import proto.inventa.cct.com.inventalibrary.notification.NotificationHelper;
import proto.inventa.cct.com.inventalibrary.notification.NotificationHelper.SubscribedEZoneNotificationsListener;
import proto.inventa.cct.com.inventalibrary.profile.ProfileHelper;
import proto.inventa.cct.com.inventalibrary.rest.NotificationApiHelper;

import static proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper.KEY_GEOZONE_ID;
import static proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper.KEY_engagementZONE_ID;

public class DiscoveryBroadcastReceiver extends BroadcastReceiver {
    public static final String LOG_TAG = DiscoveryBroadcastReceiver.class.getSimpleName() + " Logs: ";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d(LOG_TAG, " intent.getAction() " + action);
        if (action != null) {

            switch (action) {
                case DiscoveryHelper.ACTION_DISCOVERY_GEOZONES_BROADCAST:
                    showGeoZones(intent);
                    break;

                case DiscoveryHelper.ACION_DISCOVERY_ENGAGEMENTZONES_BROADCAST:
                    showEngagementZones(intent);
                    break;

                case DiscoveryHelper.ACION_DISCOVERY_INSTORE_BROADCAST:
                    showInStore(intent);

                default:
                    break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void showInStore(Intent intent) {
        if (intent.getSerializableExtra(DiscoveryHelper.KEY_INSTORE_LIST) instanceof String) {
            // ;lasdfkasdk
            ArrayList<String> asda = new ArrayList<>();
        }
        ArrayList<String> p_ids = (ArrayList) intent.getSerializableExtra(DiscoveryHelper.KEY_INSTORE_LIST);
        Log.d(LOG_TAG, p_ids.toString());
        for (String singleId : p_ids) {
            String username = ProfileHelper.getProfileUsernameById(singleId);


            if (!username.isEmpty()) {
                if (ProfileHelper.isStoreManagerRoleProfile(singleId) && ProfileHelper.isSelfProfileConsumerRoleProfile()) {

                    // I am the consumer and the discovered profile is the store manager
                    findStoreName(singleId, username, proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY, NotificationHelper.NOTIFICATION_IDENTIFIER_INSTORE);

                } else if (ProfileHelper.isConsumerRoleProfile(singleId) && ProfileHelper.isSelfStoreManagerRoleProfile()) {

                    // I am the store manager and the discovered profile is the consumer
                    showNotification(singleId, username, "is in the store", proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY, NotificationHelper.NOTIFICATION_IDENTIFIER_INSTORE);
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    private void showEngagementZones(Intent intent) {
        HashMap<Integer, ArrayList<String>>
                engagementZonesMap = (HashMap<Integer, ArrayList<String>>)
                intent.getSerializableExtra(DiscoveryHelper.KEY_ENGAGEMENTZONES_MAP);
        Log.d(LOG_TAG, "ENGAGEMENTZONES_MAP received");

        if (engagementZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_ENTER)) {
            for (String singleId : engagementZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_ENTER)) {
                String name = InventaSdk.getRealmDataHelper().getEngagementZoneNameById(singleId);
                findNotifForEZone(singleId, name, "eZone entered", proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY);
            }
        }

        if (engagementZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_DWELL)) {
            for (String singleId : engagementZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_DWELL)) {
                String name = InventaSdk.getRealmDataHelper().getEngagementZoneNameById(singleId);
                findNotifForEZone(singleId, name, "eZone dwelt", proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY);
            }
        }

        if (engagementZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_EXIT)) {
            for (String singleId : engagementZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_EXIT)) {
                String name = InventaSdk.getRealmDataHelper().getEngagementZoneNameById(singleId);
                findNotifForEZone(singleId, name, "eZone exited", proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void showGeoZones(Intent intent) {
        HashMap<Integer, ArrayList<String>> geoZonesMap = (HashMap<Integer, ArrayList<String>>)
                intent.getSerializableExtra(DiscoveryHelper.KEY_GEOZONES_MAP);
        Log.d(LOG_TAG, "KEY_GEOZONES_MAP received : ");

        if (geoZonesMap != null) {

            if (geoZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_ENTER)) {
                for (String singleId : geoZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_ENTER)) {
                    String name = InventaSdk.getRealmDataHelper().getGeoZoneNameById(singleId);
                    findNotificationforGeoZone(singleId, name, "geoZone entered", proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY);
                }
            }

            if (geoZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_DWELL)) {
                for (String singleId : geoZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_DWELL)) {
                    String name = InventaSdk.getRealmDataHelper().getGeoZoneNameById(singleId);
                    findNotificationforGeoZone(singleId, name, "geoZone dwelt", proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY);
                }
            }

            if (geoZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_EXIT)) {
                for (String singleId : geoZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_EXIT)) {
                    String name = InventaSdk.getRealmDataHelper().getGeoZoneNameById(singleId);
                    findNotificationforGeoZone(singleId, name, "geoZone exited", proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY);
                }
            }
        }
    }

    public void findNotificationforGeoZone(String uuid, String name, String eventText, @proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNELS String channel) {

        //QUERRY FOR NOTIFICATION from server
        NotificationApiHelper notificationApiHelper = NotificationApiHelper.getInstance();
        notificationApiHelper.getNotificationByGeoZoneId(uuid, new NotificationHelper.onGeozoneNotificationsFound() {
            @Override
            public void onGeozoneNotificationsFound(String notificationList, String geoZoneId, String matchingStores) {
                Log.d(LOG_TAG, "  onGeozoneNotificationsFound matchingStores : " + matchingStores);
                showGeozoneNotifications(notificationList, geoZoneId, matchingStores);
            }

            @Override
            public void onError(String errorMsg) {
                Log.d(LOG_TAG, "  onGeozoneNotificationsFound matchingStores errorMsg : " + errorMsg);
            }
        });
    }


    public void findNotifForEZone(String uuid, String name, String eventText, @proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNELS String channel) {


        List<StoreDataModel> storesUnderEZone = InventaSdk.getRealmDataHelper().getStoreDataModelByEId(uuid);
        if (storesUnderEZone != null) {
            Log.d(LOG_TAG, " storesUnderEZone.size() = " + storesUnderEZone.size());
            for (StoreDataModel storeDataModel : storesUnderEZone) {
                if (storeDataModel.getSubscribed()) {

                    NotificationApiHelper notificationApiHelper = NotificationApiHelper.getInstance();
                    notificationApiHelper.getStoreNotifications(storeDataModel.getSt_id(), new SubscribedEZoneNotificationsListener() {
                        @Override
                        public void onSubscribedEZoneNotificationsFound(String notificationModelList, String eZoneId) {
                            showSubscribedEZoneNotifications(notificationModelList, eZoneId);
                        }

                        @Override
                        public void onError(String errorMsg) {
                            //TODO
                        }
                    });

                }
            }
        }
    }

//    private List<StoreModelData> createStoreModelFromJson(String jsonstoresUnderEZone) {
//        Gson gson = new Gson();
//        Type collectionType = new TypeToken<List<StoreModelData>>() {}.getType();
//        return gson.fromJson(jsonstoresUnderEZone, collectionType);
//    }

    private List<NotificationDataModel> createNotificationModelFromJson(String jsonString) {

        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<NotificationDataModel>>() {
        }.getType();
        return gson.fromJson(jsonString, collectionType);
    }

    public void showGeozoneNotifications(String jsonNotifString, String geoZoneId, String matchingStores) {

        List<NotificationDataModel> notificationModelList = createNotificationModelFromJson(jsonNotifString);

        for (int i = 0; i < notificationModelList.size(); i++) {
            NotificationDataModel notificationModel = notificationModelList.get(i);
            String nt_id = notificationModel.getNt_id();

            List<NotificationTypeFormat> type = notificationModel.getNotification_type();
            String name = type.get(0).getName();

            List<NotificationDataAttributes> notifAttribute = notificationModel.getNotification();
            String action_url = notifAttribute.get(0).getAction_url();
            String image_url = notifAttribute.get(0).getImage_url();
            String text = notifAttribute.get(0).getText();
            String validity_end = notifAttribute.get(0).getValidity_end();
            String validity_start = notifAttribute.get(0).getValidity_start();

            showNotification(geoZoneId, text, matchingStores, proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY,
                    NotificationHelper.NOTIFICATION_IDENTIFIER_GEOZONE);
        }
    }

    public void showSubscribedEZoneNotifications(String jsonNotifString, String storeId) {

        List<NotificationDataModel> notificationModelList = createNotificationModelFromJson(jsonNotifString);

        for (int i = 0; i < notificationModelList.size(); i++) {
            NotificationDataModel notificationModel = notificationModelList.get(i);
            String nt_id = notificationModel.getNt_id();

            List<NotificationTypeFormat> type = notificationModel.getNotification_type();
            String name = type.get(0).getName();

            List<NotificationDataAttributes> notifAttribute = notificationModel.getNotification();
            String action_url = notifAttribute.get(0).getAction_url();
            String image_url = notifAttribute.get(0).getImage_url();
            String text = notifAttribute.get(0).getText();
            String validity_end = notifAttribute.get(0).getValidity_end();
            String validity_start = notifAttribute.get(0).getValidity_start();

            showNotification(storeId, name, text, proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY, NotificationHelper.NOTIFICATION_IDENTIFIER_EZONE);
        }
    }

    //show instore notification
    public void showStoreNameForInstore(List<String> storeIds) {

        String eventText = "Welcome to  - ";
        if (storeIds != null && storeIds.size() > 0) {
            String storeId = storeIds.get(0);
            String storeName = InventaSdk.getRealmDataHelper().getStoreNameByStoreId(storeId);
            showNotification(storeId, storeName, eventText.concat(storeName), proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY, NotificationHelper.NOTIFICATION_IDENTIFIER_INSTORE);
        }
    }

    public void showNotification(String zoneId, String title, String eventText, @proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNELS String channel, String type) {

        proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper notificationHelperHelper = new proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper(InventaSdk.getContext(), channel, false);
        int notificationId = 0;
        switch (type) {
            case NotificationHelper.NOTIFICATION_IDENTIFIER_GEOZONE:
                Intent geoIntent = new Intent(InventaSdk.getContext(), MainActivity.class);
                geoIntent.putExtra(KEY_GEOZONE_ID, zoneId);
                notificationId = 0;
                PendingIntent pendingIntent = notificationHelperHelper.setupPendingIntent(geoIntent);
                NotificationCompat.Builder builder = notificationHelperHelper.getNotificationCompatBuilder(title, eventText);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);

                //create a unique notifcation id each time, so that one notification doesnt replace another
                notificationHelperHelper.notify(zoneId, notificationId, builder);

                break;
            case NotificationHelper.NOTIFICATION_IDENTIFIER_EZONE:
                Intent eZoneIntent = new Intent(InventaSdk.getContext(), MainActivity.class);

                eZoneIntent.putExtra(KEY_engagementZONE_ID, zoneId);
                notificationId = 1;
                PendingIntent eZonePendingIntent = notificationHelperHelper.setupPendingIntent(eZoneIntent);
                NotificationCompat.Builder eZoneNotiBuilder = notificationHelperHelper.getNotificationCompatBuilder(title, eventText);
                eZoneNotiBuilder.setContentIntent(eZonePendingIntent);
                eZoneNotiBuilder.setAutoCancel(true);

                //create a unique notifcation id each time, so that one notification doesnt replace another
                notificationHelperHelper.notify(zoneId, notificationId, eZoneNotiBuilder);

                break;

            case NotificationHelper.NOTIFICATION_IDENTIFIER_INSTORE:

                createInStoreNotification(zoneId, title, eventText, notificationHelperHelper);
                break;
        }
    }

    private void findStoreName(String id, String username, String channelDiscovery, String singleId) {

        NotificationApiHelper notificationApiHelper = NotificationApiHelper.getInstance();
        notificationApiHelper.getAssignedStoreByProfileId(id, new NotificationHelper.OnAssignedStoreManagerByProfileID() {
            @Override
            public void OnAssignedStoreManagerByProfileID(List<String> storeIds) {
                showStoreNameForInstore(storeIds);
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(LOG_TAG, errorMsg);
            }
        });
        //Show notif after you receive store name from server
    }

    private void createInStoreNotification(String zoneId, String title, String eventText, proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper notificationHelperHelper) {
        int notificationId;
        Intent inStoreIntent = new Intent(InventaSdk.getContext(), MainActivity.class);

        inStoreIntent.putExtra("UUID", zoneId);
        inStoreIntent.putExtra(NotificationHelper.KEY_NOTIF_TYPE, "InStore");
//                intent.removeExtra(NotificationTypeFormat.KEY_GEO_NOTIF_TYPE);
        notificationId = 1;
        PendingIntent inStorePendingIntent = notificationHelperHelper.setupPendingIntent(inStoreIntent);
        NotificationCompat.Builder inStoreNotiBuilder = notificationHelperHelper.getNotificationCompatBuilder(title, eventText);
        inStoreNotiBuilder.setContentIntent(inStorePendingIntent);
        inStoreNotiBuilder.setAutoCancel(true);

        //create a unique notifcation id each time, so that one notification doesnt replace another
        if (!notificationHelperHelper.isNotificationActive(zoneId, notificationId)) {
            notificationHelperHelper.notify(zoneId, notificationId, inStoreNotiBuilder);
        }
    }
}
