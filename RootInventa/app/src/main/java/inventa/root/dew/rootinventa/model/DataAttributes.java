package inventa.root.dew.rootinventa.model;

import com.google.gson.annotations.SerializedName;

public class DataAttributes {

    @SerializedName("value_type")
    private String value_type;

    @SerializedName("display_name")
    private String display_name;

    @SerializedName("privacy_level")
    private String privacy_level;

    @SerializedName("key")
    private String key;

    @SerializedName("required")
    private Boolean required;

    @SerializedName("value")
    private String value;

    public String getValue_type(){
        return value_type;
    }

    public void setValue_type(String value_type){
        this.value_type = value_type;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPrivacy_level() {
        return privacy_level;
    }

    public void setPrivacy_level(String privacy_level) {
        this.privacy_level = privacy_level;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
