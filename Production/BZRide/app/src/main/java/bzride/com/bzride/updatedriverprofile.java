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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class updatedriverprofile extends AppCompatActivity implements View.OnClickListener,OnPostExecuteListener {
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
        setContentView(R.layout.activity_updatedriverprofile);

        btnupdate = (Button) findViewById(R.id.btnUpdateDriverProfile);
        findViewById(R.id.btnUpdateDriverProfile).setOnClickListener(this);

        firstName = (EditText) findViewById(R.id.txtdriverFirstName);
        middleName = (EditText) findViewById(R.id.txtdrivermiddleName);
        lastName = (EditText) findViewById(R.id.txtdriverLastName);
        email = (EditText) findViewById(R.id.txtdriveremail);
        address1 = (EditText) findViewById(R.id.txtdriverAddress1);
        address2 = (EditText) findViewById(R.id.txtdriverAddress2);
        city = (EditText) findViewById(R.id.txtdriverCity);
        state = (EditText) findViewById(R.id.txtdriverState);
        zip = (EditText) findViewById(R.id.txtdriverZip);

        firstName.setText( BZAppManager.getInstance().bzDriverData.FirstName);
        middleName.setText( BZAppManager.getInstance().bzDriverData.MiddleName);
        lastName.setText( BZAppManager.getInstance().bzDriverData.LastName);
        email.setText( BZAppManager.getInstance().bzDriverData.Email);
        address1.setText( BZAppManager.getInstance().bzDriverData.Address1);
        address2.setText( BZAppManager.getInstance().bzDriverData.Address2);
        city.setText( BZAppManager.getInstance().bzDriverData.City);
        state.setText( BZAppManager.getInstance().bzDriverData.State);
        zip.setText( BZAppManager.getInstance().bzDriverData.Zip);





    }
@Override
public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnUpdateDriverProfile: updateraction();break;
        }
    }
    private void updateraction() {


        String name = ((EditText) findViewById(R.id.txtdriverFirstName)).getText().toString();
        if (Utils.isEmpty(name)) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NAME_EMPTY, null);
            return;
        }


        String emailText = ((EditText) findViewById(R.id.txtdriveremail)).getText().toString();
        if (!Utils.isValidEmail(emailText)) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_EMAIL_INVALID, null);
            return;
        }


        BZAppManager.getInstance().bzDriverData.FirstName = firstName.getText().toString();
        BZAppManager.getInstance().bzDriverData.MiddleName = middleName.getText().toString();
        BZAppManager.getInstance().bzDriverData.LastName = lastName.getText().toString();
        BZAppManager.getInstance().bzDriverData.Email = email.getText().toString();
        BZAppManager.getInstance().bzDriverData.Address1 = address1.getText().toString();
        BZAppManager.getInstance().bzDriverData.Address2 = address2.getText().toString();
        BZAppManager.getInstance().bzDriverData.City = city.getText().toString();
        BZAppManager.getInstance().bzDriverData.State = state.getText().toString();
        BZAppManager.getInstance().bzDriverData.Zip = zip.getText().toString();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);

        BZRESTApiHandler api = new BZRESTApiHandler(this);
        api.setMessage("Updating driver details...");
        api.setPostExecuteListener(this);

        String urlCall = Utils.BASE_URL + Utils.UPDATE_DRIVER_PROFILE_URL ;
        String params = BZAppManager.getInstance().getDriverDataParamsFlatForUpdateProfile();
        params = params + "&token="+ usertoken ;
        api.putDetails(urlCall, Utils.UPDATE_DRIVER_PROFILE_URL, params);
    }

    @Override
    public void onSuccess(BZJSONResp model) {

        try {
            BZJSONResp response = (BZJSONResp)model;
            if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {
                finish();
                startActivity(new Intent(getApplicationContext(), HomeDriver.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
