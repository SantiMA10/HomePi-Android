package xyz.santima.homepi.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FirebaseConfiguration extends RealmObject {

    @PrimaryKey
    private String apiKey;
    private String applicationId;
    private String databaseUrl;
    private String gcmSenderId;
    private String storageBucket;

    public FirebaseConfiguration() {
        this.apiKey = "";
        this.applicationId = "";
        this.databaseUrl = "";
        this.gcmSenderId = "";
        this.storageBucket = "";
    }

    public FirebaseConfiguration(String apiKey, String applicationId, String databaseUrl, String gcmSenderId, String storageBucket) {
        this.apiKey = apiKey;
        this.applicationId = applicationId;
        this.databaseUrl = databaseUrl;
        this.gcmSenderId = gcmSenderId;
        this.storageBucket = storageBucket;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getGcmSenderId() {
        return gcmSenderId;
    }

    public void setGcmSenderId(String gcmSenderId) {
        this.gcmSenderId = gcmSenderId;
    }

    public String getStorageBucket() {
        return storageBucket;
    }

    public void setStorageBucket(String storageBucket) {
        this.storageBucket = storageBucket;
    }

    public boolean isCorrect(){
        return !getApiKey().isEmpty() && !getApplicationId().isEmpty()
                && !getDatabaseUrl().isEmpty() && !getGcmSenderId().isEmpty()
                && !getStorageBucket().isEmpty();
    }

}
