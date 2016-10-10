package bzride.com.bzride;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class login extends AppCompatActivity implements View.OnClickListener, OnPostExecuteListener  {
    private EditText loginmobiletext;
    private EditText passwordtext;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginmobiletext = (EditText)findViewById(R.id.txtmobile);
        passwordtext = (EditText)findViewById(R.id.txtpassword);
        findViewById(R.id.btnLogin).setOnClickListener(this);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.v("IGA", "Token retrieved and sent to server");
                } else {
                    Log.v("IGA", "Token creation failed");
                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                loginaction();
                break;
        }
    }
    private void loginaction() {
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setPostExecuteListener(this);
            if (BZAppManager.getInstance().isDriver == true) {
                api.setMessage("Authenticating driver...");
                String urlCall = Utils.BASE_URL + Utils.LOGIN_DRIVER_URL + "?mobilenumber="+ loginmobiletext.getText() + "&password=" + passwordtext.getText();
                api.get(urlCall, Utils.LOGIN_DRIVER_URL);
            }
            else{
                api.setMessage("Authenticating rider...");
                String urlCall = Utils.BASE_URL + Utils.LOGIN_RIDER_URL+ "?mobilenumber="+ loginmobiletext.getText() + "&password=" + passwordtext.getText();;
                api.get(urlCall, Utils.LOGIN_RIDER_URL);
            }

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }



    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("TAG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();

    }
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
    }
    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    @Override
    public void onSuccess(BZJSONResp model) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        LoginResp response = (LoginResp)model;
         if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {
            //sharedPreferences.edit().putString(QuickstartPreferences.USER_TOKEN, response.token).apply();

            try {
                String encodedString = URLEncoder.encode(response.token, "UTF-8");
                sharedPreferences.edit().putString(QuickstartPreferences.USER_TOKEN, encodedString).apply();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            sharedPreferences.edit().putString(QuickstartPreferences.USER_ID,response.userid).apply();

            BZAppManager.getInstance().currentUserId = response.userid;

            if (BZAppManager.getInstance().isDriver == true) {
                Intent myIntent = new Intent(login.this, HomeDriver.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                login.this.startActivity(myIntent);
                sharedPreferences.edit().putString(QuickstartPreferences.USER_TYPE, "Driver").apply();
            }
            else{
                Intent myIntent = new Intent(login.this, Home.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                login.this.startActivity(myIntent);
                sharedPreferences.edit().putString(QuickstartPreferences.USER_TYPE, "Rider").apply();
            }

             if (checkPlayServices()) {
                 // Start IntentService to register this application with GCM.
                 Intent intent = new Intent(this, RegistrationIntentService.class);
                 startService(intent);
             }
        }
        else {
            sharedPreferences.edit().putString(QuickstartPreferences.USER_TOKEN, "").apply();
            Utils.showInfoDialog(this, Utils.MSG_TITLE, response.info, null);
        }
    }


    @Override
    public void onFailure() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_SERVER, null);
    }
}
