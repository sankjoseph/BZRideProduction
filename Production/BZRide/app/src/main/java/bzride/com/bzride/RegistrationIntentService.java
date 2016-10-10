package bzride.com.bzride;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by Santhosh.Joseph on 31-08-2016.
 */
public class RegistrationIntentService extends IntentService implements  OnPostExecuteListener {
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    public static final int gcm_defaultSenderId=0x7f060034;

    public RegistrationIntentService() {

        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {

            String authorizedEntity = "607309428469"; // Project id from Google Developers Console
            String scope = "GCM"; // e.g. communicating using GCM, but you can use any
            // URL-safe characters up to a maximum of 1000, or
            // you can also leave it blank.
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(authorizedEntity,GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(String token) {
        // update device token with web service
        // Add custom implementation, as needed.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);
        String usertype = sharedPreferences.getString(QuickstartPreferences.USER_TYPE, null);
        sharedPreferences.edit().putString(QuickstartPreferences.DEVICE_TOKEN,token).apply();

        BZRESTApiHandler api = new BZRESTApiHandler();
        String urlCall = Utils.BASE_URL + Utils.UPDATE_DEVICE_TOKEN_URL;
        String params = "&token="+ usertoken + "&devicetoken=" + token +"&usertype=" + usertype ;
        api.putDetails(urlCall, Utils.UPDATE_DEVICE_TOKEN_URL, params);


        //keep the token
        /*if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler();
            api.setPostExecuteListener(this);

            if (BZAppManager.getInstance().isDriver == true) {
                api.setMessage("updating driver device...");
                String urlCall = Utils.BASE_URL + Utils.UPDATE_DEVICE_TOKEN_URL + "?token="+ usertoken + "&devicetoken=" + token;
                api.get(urlCall, Utils.UPDATE_DEVICE_TOKEN_URL);
            }
            else{
                api.setMessage("updating rider device...");
                String urlCall = Utils.BASE_URL + Utils.UPDATE_DEVICE_TOKEN_URL+ "?username="+ usertoken + "&devicetoken=" + token;
                api.get(urlCall, Utils.UPDATE_DEVICE_TOKEN_URL);
            }

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }*/

    }

    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
        @Override
    public void onSuccess(BZJSONResp model) {

    }
    @Override
    public void onFailure() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_SERVER, null);
    }
}
