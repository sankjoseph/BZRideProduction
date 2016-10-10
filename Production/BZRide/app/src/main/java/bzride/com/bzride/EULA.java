package bzride.com.bzride;

import android.content.Intent;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class EULA extends AppCompatActivity implements View.OnClickListener, OnPostExecuteListener {
    private String deviceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eul);

        findViewById(R.id.btnAcceptEULA).setOnClickListener(this);
        findViewById(R.id.btnDeclineEULA).setOnClickListener(this);

        WebView wv = (WebView) findViewById(R.id.webViewEULA);
        wv.setVerticalScrollBarEnabled(true);
        wv.loadUrl("file:///android_asset/BZRideTerms.html");
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAcceptEULA:
                acceptEULAaction();break;
            case R.id.btnDeclineEULA:
                declineEULAaction();break;
        }
    }
    private void acceptEULAaction() {
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {


            // Get all other info from drill down screens from this register screen ie  card info taken in other child screen
            if (BZAppManager.getInstance().isDriver == true) {
                BZRESTApiHandler api = new BZRESTApiHandler(this);
                api.setMessage("Registering new driver...");
                api.setPostExecuteListener(this);

                String urlCall = Utils.BASE_URL + Utils.REGISTER_DRIVER_URL ;
                String params = BZAppManager.getInstance().getDriverDataParamsFlat();
                deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                params = params + "&deviceId=" + deviceid;
                api.putDetails(urlCall, Utils.REGISTER_DRIVER_URL, params);
            }
            else{
                BZRESTApiHandler api = new BZRESTApiHandler(this);
                api.setMessage("Registering new rider...");
                api.setPostExecuteListener(this);

                String urlCall = Utils.BASE_URL + Utils.REGISTER_RIDER_URL;
                String params = BZAppManager.getInstance().getRiderDataParamsFlat();

                deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                params = params + "&deviceId=" + deviceid;
                api.putDetails(urlCall, Utils.REGISTER_RIDER_URL, params);
            }
            // Get all other info from drill down screens from this register screen ie  vehicle,license, registration, insurance
            // This is done in other child drill down screens


        } else
        {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }

    }
    private void declineEULAaction() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ACC_EULA, null);
    }

    @Override
    public void onSuccess(BZJSONResp model) {

        RegisterResp response = (RegisterResp)model;
        if (model.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {
            // store driver id for filling bank info
            if (BZAppManager.getInstance().isDriver == true) {
                BZAppManager.getInstance().currentUserId = response.Id;
                Intent myIntent = new Intent(EULA.this, driverBankInfo.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                myIntent.putExtra("mode", "new");
                EULA.this.startActivity(myIntent);
            }
            else {//rider response

                BZAppManager.getInstance().currentUserId = response.Id;
                startActivity(new Intent(getApplicationContext(), BZLanding.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }

        }
        else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, model.info, null);
            finish();
        }
    }


    @Override
    public void onFailure() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_SERVER, null);
    }
}
