package inventa.root.dew.rootinventa.model;

import com.google.gson.annotations.SerializedName;

public class NotificationTypeFormat {

    /**
     * Lookup key for {@link #name}
     */
    public static final String KEY_GEO_NOTIF_TYPE = "geo_notif_type";
    public static final String KEY_NOTIF_TYPE = "notif_type";

    /**
     * Lookup key for {@link #name}
     */
    public static final String KEY_EZONE_NOTIF_TYPE = "ezone_notif_type";

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @SerializedName("uuid")
    private String uuid;
}
