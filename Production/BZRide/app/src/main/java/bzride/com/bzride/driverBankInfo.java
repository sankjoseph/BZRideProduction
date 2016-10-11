package bzride.com.bzride;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Token;

import java.util.HashMap;
import java.util.Map;

public class driverBankInfo extends AppCompatActivity  implements   View.OnClickListener, OnPostExecuteListener {

    EditText txtBankName,txtBankAccountNumber,txtBankAccountHolderName,txtBankAccountRoutingNumber;
    private String Option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_bank_info);
        findViewById(R.id.btnBankDriverDone).setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        Option= extras.getString("mode");
        if (Option.equals("edit")){

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String userid = sharedPreferences.getString(QuickstartPreferences.USER_ID, null);
            BZAppManager.getInstance().currentUserId = userid;
        }



        txtBankName =  (EditText)  findViewById(R.id.txtBankName);
        txtBankAccountNumber = (EditText)  findViewById(R.id.txtBankAccountNumber);
        txtBankAccountHolderName = (EditText)  findViewById(R.id.txtBankAccountHolderName);
        txtBankAccountRoutingNumber = (EditText)  findViewById(R.id.txtBankAccountRoutingNumber);

        // load values if already fit in
        txtBankName.setText(BZAppManager.getInstance().bzDriverData.driverBankInfo.BankName);
        txtBankAccountNumber.setText(BZAppManager.getInstance().bzDriverData.driverBankInfo.BankAccountNumber);
        txtBankAccountHolderName.setText(BZAppManager.getInstance().bzDriverData.driverBankInfo.BankAccountHolderName);
        txtBankAccountRoutingNumber.setText(BZAppManager.getInstance().bzDriverData.driverBankInfo.BankAccountRoutingNumber);

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBankDriverDone:
                updateBankInfoaction();
                break;
        }
    }
    private void updateBankInfoaction() {
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
        BZAppManager.getInstance().bzDriverData.driverBankInfo.BankName  = txtBankName.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverBankInfo.BankAccountNumber  = txtBankAccountNumber.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverBankInfo.BankAccountHolderName  = txtBankAccountHolderName.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverBankInfo.BankAccountRoutingNumber  = txtBankAccountRoutingNumber.getText().toString();

            // todo test from app
            /*Stripe.apiKey = "sk_test_2rCnQT2VGQl5ndFbgfEas7g2";

            Map<String, Object> tokenParams = new HashMap<String, Object>();
            Map<String, Object> bank_accountParams = new HashMap<String, Object>();
            bank_accountParams.put("country", "US");
            bank_accountParams.put("currency", "usd");
            bank_accountParams.put("account_holder_name", "Jane Austen");
            bank_accountParams.put("account_holder_type", "individual");
            bank_accountParams.put("routing_number", "11000000");
            bank_accountParams.put("account_number", "000123456789");
            tokenParams.put("bank_account", bank_accountParams);

            try {
                Token s = Token.create(tokenParams);
                Log.d("Token", s.getId());
            } catch (AuthenticationException e) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, e.getMessage(), null);
            } catch (CardException e) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, e.getMessage(), null);
            } catch (APIException e) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, e.getMessage(), null);
            } catch (InvalidRequestException e) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, e.getMessage(), null);
            } catch (APIConnectionException e) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE,e.getMessage(), null);
            }*/

            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setMessage("updating bank details for driver...");
            api.setPostExecuteListener(this);

            String urlCall = Utils.BASE_URL + Utils.UPDATE_BANK_INITIAL_INFO_URL ;
            String params = BZAppManager.getInstance().getDriverBankDataParamsFlat();
            params = params + "&driverID=" + BZAppManager.getInstance().currentUserId;
            api.putDetails(urlCall, Utils.UPDATE_BANK_INITIAL_INFO_URL, params);


    } else {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
    }
}
    @Override
    public void onSuccess(BZJSONResp model) {
        BZJSONResp response = (BZJSONResp)model;
        if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {
            if (Option.equals("edit"))
            {
                finish();//update and close
            }
            else
            {// update and open landing
                startActivity(new Intent(getApplicationContext(), BZLanding.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
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
