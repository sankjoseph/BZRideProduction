package bzride.com.bzride;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.stripe.android.Stripe.*;
import com.stripe.android.*;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONObject;

public class usercarddetails extends AppCompatActivity implements View.OnClickListener,OnPostExecuteListener {

    private EditText txtCardDetailsNumber,txtCardDetailsAddress1,txtCardDetailsAddress2;
    private EditText txtCardDetailsCity,txtCardDetailsState,txtCardDetailsZip;
    private EditText txtCardDetailsExpMonth,txtCardDetailsExpYear,txtCardDetailsCVV;
    private String Option;
    public BZCardInfo myCardData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercarddetails);
        findViewById(R.id.btnCardDetailsDone).setOnClickListener(this);

        if (BZAppManager.getInstance().isDriver == true){

            myCardData = BZAppManager.getInstance().bzDriverData.cardData;
        }
        else
        {
            myCardData = BZAppManager.getInstance().bzRiderData.cardData;
        }
        //load if data there

        Bundle extras = getIntent().getExtras();
        Option= extras.getString("mode");


        RadioGroup radiogrcardType = (RadioGroup) findViewById(R.id.radioGroupCardType);

        if (Utils.isEqualAndNotEmpty(myCardData.cardType, "D"))
        {
           RadioButton btn = (RadioButton) radiogrcardType.findViewById(R.id.radioButtonDC);
           btn.setChecked(true);
        }
        else if (Utils.isEqualAndNotEmpty(myCardData.cardType, "C"))
        {
           RadioButton btn = (RadioButton) radiogrcardType.findViewById(R.id.radioButtonCC);
           btn.setChecked(true);
        }

        //card vendor
        RadioGroup radiogrcardVendor = (RadioGroup) findViewById(R.id.radioGroupVendor);

        if (Utils.isEqualAndNotEmpty(myCardData.cardVendor, "M"))
        {
            RadioButton btn = (RadioButton) radiogrcardVendor.findViewById(R.id.radioButtonMC);
            btn.setChecked(true);
        }
        else if (Utils.isEqualAndNotEmpty(myCardData.cardVendor,"V"))
        {
            RadioButton btn = (RadioButton) radiogrcardVendor.findViewById(R.id.radioButtonVISA);
            btn.setChecked(true);
        }

        else if (Utils.isEqualAndNotEmpty(myCardData.cardVendor,"A"))
        {
            RadioButton btn = (RadioButton) radiogrcardVendor.findViewById(R.id.radioButtonAmex);
            btn.setChecked(true);
        }

        txtCardDetailsNumber = (EditText)findViewById(R.id.txtCardDetailsNumber);
        txtCardDetailsAddress1 = (EditText)findViewById(R.id.txtCardDetailsAddress1);
        txtCardDetailsAddress2 = (EditText)findViewById(R.id.txtCardDetailsAddress2);

        txtCardDetailsExpMonth = (EditText)findViewById(R.id.txtCardDetailsExpMonth);
        txtCardDetailsExpYear = (EditText)findViewById(R.id.txtCardDetailsExpYear);
        txtCardDetailsCVV = (EditText)findViewById(R.id.txtCardDetailsCVV);
        txtCardDetailsCity = (EditText)findViewById(R.id.CardDetailsCity);
        txtCardDetailsState = (EditText)findViewById(R.id.CardDetailsState);
        txtCardDetailsZip = (EditText)findViewById(R.id.CardDetailsgZip);

        // load values if already fit in
        txtCardDetailsNumber.setText(myCardData.cardNumber);
        txtCardDetailsAddress1.setText(myCardData.cardBillingAddress1);
        txtCardDetailsAddress2.setText(myCardData.cardBillingAddress2);
        txtCardDetailsCity.setText(myCardData.cardBillingCity);
        txtCardDetailsState.setText(myCardData.cardBillingState);
        txtCardDetailsZip.setText(myCardData.cardBillingZip);

        txtCardDetailsExpMonth.setText(myCardData.cardExpiryMonth);
        txtCardDetailsExpYear.setText(myCardData.cardExpiryYear);
        txtCardDetailsCVV.setText(myCardData.cardCVV);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCardDetailsDone:
                cardDetailsDoneAction();break;

        }
    }
    private void cardDetailsDoneAction() {
        RadioGroup radiogrcardType = (RadioGroup) findViewById(R.id.radioGroupCardType);

        /// card vendor
        int indexType = radiogrcardType.getCheckedRadioButtonId();
        RadioButton btnType = (RadioButton) radiogrcardType.findViewById(indexType);
        switch (btnType.getId()) {
            case R.id.radioButtonCC:
                myCardData.cardType = "C";
                break;
            case R.id.radioButtonDC:
                myCardData.cardType = "D";
                break;
        }

    /// card vendor
        RadioGroup radiogrcardVendor = (RadioGroup) findViewById(R.id.radioGroupVendor);
        int indexVendor = radiogrcardVendor.getCheckedRadioButtonId();
        RadioButton btnVendor = (RadioButton) radiogrcardVendor.findViewById(indexVendor);
        switch (btnVendor.getId()) {
            case R.id.radioButtonMC:
                myCardData.cardVendor = "M";
                break;
            case R.id.radioButtonVISA:
                myCardData.cardVendor = "V";
                break;
            case R.id.radioButtonAmex:
                myCardData.cardVendor = "A";
                break;
        }


        if (Utils.isEmpty(txtCardDetailsNumber.getText().toString())) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_CARD_NUMBER_EMPTY, null);
            return;
        }

        if (Utils.isEmpty(txtCardDetailsExpMonth.getText().toString())) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_CARD_EXP_MONTH, null);
            return;
        }
        if (Utils.isEmpty(txtCardDetailsExpYear.getText().toString())) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_CARD_EXP_YEAR, null);
            return;
        }

        if (Utils.isEmpty(txtCardDetailsCVV.getText().toString())) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_CARD_CVV, null);
            return;
        }



        Card card = new Card(txtCardDetailsNumber.getText().toString(),
                Integer.parseInt(txtCardDetailsExpMonth.getText().toString()),
                Integer.parseInt(txtCardDetailsExpYear.getText().toString()),
                txtCardDetailsCVV.getText().toString());

        if (!card.validateNumber())
        {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_INVALID_CARD_NUMBER, null);
            return;
        }
        else {
            int b =0;
        }
        if (!card.validateExpiryDate())
        {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_INVALID_CARD_EXPDATE, null);
        }
        if (!card.validateCVC())
        {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_INVALID_CARD_CVV, null);
            return;
        }
        else{
            int b =0;
        }


        new Stripe().createToken(card, Utils.STRIPE_RUNNING_KEY,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                        String tokenString = token.getId();

                        myCardData.cardToken = tokenString;
                        Log.d("Stripesample", tokenString);
                    }

                    public void onError(Exception error) {
                        // Show localized error message
                        Toast.makeText(getApplicationContext(),
                                "Some error while getting credit cark token",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
        // other details
        myCardData.cardNumber = txtCardDetailsNumber.getText().toString();
        myCardData.cardBillingAddress1 = txtCardDetailsAddress1.getText().toString();
        myCardData.cardBillingAddress2 = txtCardDetailsAddress2.getText().toString();
        myCardData.cardBillingCity = txtCardDetailsCity.getText().toString();
        myCardData.cardBillingState = txtCardDetailsState.getText().toString();
        myCardData.cardBillingZip = txtCardDetailsZip.getText().toString();
        myCardData.cardExpiryMonth = txtCardDetailsExpMonth.getText().toString();
        myCardData.cardExpiryYear = txtCardDetailsExpYear.getText().toString();
        myCardData.cardCVV = txtCardDetailsCVV.getText().toString();

        if (Option.equals("edit")) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);

            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setMessage("updating card details...");
            api.setPostExecuteListener(this);

            if (BZAppManager.getInstance().isDriver == true){
                String urlCall = Utils.BASE_URL + Utils.UPDATE_DRIVER_CARD_DETAILS_URL ;
                String params = BZAppManager.getInstance().getDriverCardDataParamsFlat();
                params = params + "&token="+ usertoken ;
                api.putDetails(urlCall, Utils.UPDATE_DRIVER_CARD_DETAILS_URL, params);
            }
            else {
                String urlCall = Utils.BASE_URL + Utils.UPDATE_RIDER_CARD_DETAILS_URL ;
                String params = BZAppManager.getInstance().getRiderCardDataParamsFlat();
                params = params + "&token="+ usertoken ;
                api.putDetails(urlCall, Utils.UPDATE_RIDER_CARD_DETAILS_URL, params);
            }

        }
        else
        {
            super.onBackPressed();
        }
    }
    @Override
    public void onSuccess(BZJSONResp model) {
        BZJSONResp response = (BZJSONResp)model;
        if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {
           // if edit updated and close
            finish();
            if (BZAppManager.getInstance().isDriver == true){
                startActivity(new Intent(getApplicationContext(), HomeDriver.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
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
