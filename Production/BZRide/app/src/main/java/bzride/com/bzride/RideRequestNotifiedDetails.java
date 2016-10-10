package bzride.com.bzride;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class RideRequestNotifiedDetails extends AppCompatActivity implements  View.OnClickListener, OnPostExecuteListener{
    TextView txtriderData;
    TextView txtTimer;
    String requestId = "0";
    String phone = "0";
    String messageHeader= "";
    boolean bRequestAccepted;
    double startLat;
    double startLong ;
    double endLat;
    double endLong ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_request_notified_details);
        txtriderData = (TextView)  findViewById(R.id.txtrideRequest);
        txtTimer = (TextView)findViewById(R.id.txttimeremaining);

        findViewById(R.id.btnAcceptRide).setOnClickListener(this);

        bRequestAccepted = false;

        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (!bRequestAccepted) {
                    finish();
                }
            }

        }.start();

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

      //$pushMessage = "You have a ride request from ". $firstName. " start from ". $start. " to ". $end.
      // ":".$requestId. ":".$firstName.":".$phone.":".$start.":".$end.
       // ":".$startLat.":".$startLong.":".$endLat.":".$endLong;

        //"You have a ride request from jessy start from Thalanadu, Kerala to T.B Road:31:jessy:8281038708:Thalanadu, Kerala:T.B Road:9.75345:76.7897:9.68581:76.7751"
        String[] separated = message.split(":");//0,1,2,3,4,5,6,7,8,9

        messageHeader = separated[0];
        requestId = separated[1];
        BZAppManager.getInstance().currentRideRequestUserName =  separated[2].toString();
        phone = separated[3];
        BZAppManager.getInstance().currentRideRequeststartLocation = (separated[4].toString());
        BZAppManager.getInstance().currentRideRequestendLocation = (separated[5].toString());

        startLat = Double.parseDouble(separated[6].toString());
        startLong = Double.parseDouble(separated[7].toString());
        endLat =  Double.parseDouble(separated[8].toString());
        endLong = Double.parseDouble(separated[9].toString());

        txtriderData.setText(messageHeader);


    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAcceptRide:
                acceptrideAction();
                break;
            //case R.id.btnContact:
              //  contactAction();
               // break;
        }
    }
    private void contactAction() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }
    private void acceptrideAction() {

        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            if (BZAppManager.getInstance().isDriver == true) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);
                //keep the token

                BZRESTApiHandler api = new BZRESTApiHandler(this);
                api.setMessage("Accepting ride...");
                api.setPostExecuteListener(this);

                String urlCall = Utils.BASE_URL + Utils.ACCEPT_RIDE_REQUEST_URL;
                String params= "token="+ usertoken + "&rideRequestId=" + requestId ;
                api.putDetails(urlCall, Utils.ACCEPT_RIDE_REQUEST_URL,params);
        }

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
        }
    @Override
    public void onSuccess(BZJSONResp model) {
        BZJSONResp response = (BZJSONResp)model;
        if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {
            bRequestAccepted=true;
            BZAppManager.getInstance().currentRideRequestId = requestId;
            BZAppManager.getInstance().currentRideRequestMessage = messageHeader;
            BZAppManager.getInstance().currentRideRequestUserPhone = phone;
            BZAppManager.getInstance().selectedPickUpLocation = new LatLng(startLat,startLong);
            BZAppManager.getInstance().selectedDropLocation = new LatLng(endLat,endLong);

            finish();
        }
        else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, response.info, null);
        }

    }
    @Override
    public void onFailure() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_SERVER, null);
    }
}
