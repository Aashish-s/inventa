package inventa.root.dew.rootinventa.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationDataModel {

    @SerializedName("nt_id")
    private String nt_id;

    @SerializedName("st_id")
    private String st_id;

    @SerializedName("notification")
    private List<NotificationDataAttributes> notification;

    @SerializedName("notification_type")
    private List<NotificationTypeFormat> notification_type;

    public NotificationDataModel(String st_id) {
        this.st_id = st_id;
    }

    public List<NotificationDataAttributes> getNotification() {
        return notification;
    }

    public void setNotification(List<NotificationDataAttributes> notification) {
        this.notification = notification;
    }
    public String getNt_id() {
        return nt_id;
    }

    public void setNt_id(String nt_id) {
        this.nt_id = nt_id;
    }

    public List<NotificationTypeFormat> getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(List<NotificationTypeFormat> notification_type) {
        this.notification_type = notification_type;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }
}
