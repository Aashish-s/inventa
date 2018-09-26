package inventa.root.dew.rootinventa.model;

import com.google.gson.annotations.SerializedName;

public class NotificationDataAttributes {


    @SerializedName("action_url")
    private String action_url;

    @SerializedName("text")
    private String text;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("validity_end")
    private String validity_end;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("validity_start")
    private String validity_start;

    @SerializedName("nt_id")
    private String nt_id;


    public String getAction_url() {
        return action_url;
    }

    public void setAction_url(String action_url) {
        this.action_url = action_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValidity_end() {
        return validity_end;
    }

    public void setValidity_end(String validity_end) {
        this.validity_end = validity_end;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getValidity_start() {
        return validity_start;
    }

    public void setValidity_start(String validity_start) {
        this.validity_start = validity_start;
    }

    public String getNt_id() {
        return nt_id;
    }

    public void setNt_id(String nt_id) {
        this.nt_id = nt_id;
    }
}
