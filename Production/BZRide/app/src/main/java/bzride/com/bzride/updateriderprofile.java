package bzride.com.bzride;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updateriderprofile extends AppCompatActivity implements View.OnClickListener,OnPostExecuteListener {

    private EditText firstName;
    private EditText middleName;
    private EditText lastName;
    private EditText email;
    private EditText address1;
    private EditText address2;
    private EditText city;
    private EditText state;
    private EditText zip;
    private Button btnupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateriderprofile);

        btnupdate = (Button) findViewById(R.id.btnRiderUpdateProfile);
        findViewById(R.id.btnRiderUpdateProfile).setOnClickListener(this);

        firstName = (EditText) findViewById(R.id.txtRiderFirstName);
        middleName = (EditText) findViewById(R.id.txtRiderMiddleName);
        lastName = (EditText) findViewById(R.id.txtRiderLastName);
        email = (EditText) findViewById(R.id.txtriderEmail);
        address1 = (EditText) findViewById(R.id.txtriderAddress1);
        address2 = (EditText) findViewById(R.id.txtriderAddress2);
        city = (EditText) findViewById(R.id.txtriderCity);
        state = (EditText) findViewById(R.id.txtriderState);
        zip = (EditText) findViewById(R.id.txtriderZip);

        firstName.setText( BZAppManager.getInstance().bzRiderData.FirstName);
        middleName.setText( BZAppManager.getInstance().bzRiderData.MiddleName);
        lastName.setText( BZAppManager.getInstance().bzRiderData.LastName);
        email.setText( BZAppManager.getInstance().bzRiderData.Email);
        address1.setText( BZAppManager.getInstance().bzRiderData.Address1);
        address2.setText( BZAppManager.getInstance().bzRiderData.Address2);
        city.setText( BZAppManager.getInstance().bzRiderData.City);
        state.setText( BZAppManager.getInstance().bzRiderData.State);
        zip.setText( BZAppManager.getInstance().bzRiderData.Zip);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRiderUpdateProfile: updateraction();break;
        }
    }
    private void updateraction() {

        String name = ((EditText) findViewById(R.id.txtRiderFirstName)).getText().toString();
        if (Utils.isEmpty(name)) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NAME_EMPTY, null);
            return;
        }


        String emailText = ((EditText) findViewById(R.id.txtriderEmail)).getText().toString();
        if (!Utils.isValidEmail(emailText)) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_EMAIL_INVALID, null);
            return;
        }


        BZAppManager.getInstance().bzRiderData.FirstName = firstName.getText().toString();
        BZAppManager.getInstance().bzRiderData.MiddleName = middleName.getText().toString();
        BZAppManager.getInstance().bzRiderData.LastName = lastName.getText().toString();
        BZAppManager.getInstance().bzRiderData.Email = email.getText().toString();
        BZAppManager.getInstance().bzRiderData.Address1 = address1.getText().toString();
        BZAppManager.getInstance().bzRiderData.Address2 = address2.getText().toString();
        BZAppManager.getInstance().bzRiderData.City = city.getText().toString();
        BZAppManager.getInstance().bzRiderData.State = state.getText().toString();
        BZAppManager.getInstance().bzRiderData.Zip = zip.getText().toString();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);

        BZRESTApiHandler api = new BZRESTApiHandler(this);
        api.setMessage("Updating rider details...");
        api.setPostExecuteListener(this);

        String urlCall = Utils.BASE_URL + Utils.UPDATE_RIDER_PROFILE_URL;
        String params = BZAppManager.getInstance().getRiderDataParamsFlatForUpdateProfile();
        params = params + "&token="+ usertoken ;
        api.putDetails(urlCall, Utils.UPDATE_RIDER_PROFILE_URL, params);
    }
    @Override
    public void onSuccess(BZJSONResp model) {

        try {
            BZJSONResp response = (BZJSONResp)model;
            if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {
                finish();
                startActivity(new Intent(getApplicationContext(), Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
            else {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, response.info, null);
            }
        }
        catch (Exception e) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_SERVER_DATA_HANDLE, null);
        }

    }


    @Override
    public void onFailure() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_SERVER, null);
    }
}
