package bzride.com.bzride;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class registerDriver extends AppCompatActivity implements View.OnClickListener {
    private EditText firstName;
    private EditText middleName;
    private EditText lastName;
    private EditText email;
    private EditText pwd;
    private EditText confirmpwd;
    private EditText address1;
    private EditText address2;
    private EditText city;
    private EditText state;
    private EditText zip;

    private EditText PhoneNumber;
    private EditText SSN;
    private EditText DOB;
    private DateValidator dateValidator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);
        findViewById(R.id.btnLicenseDetails).setOnClickListener(this);
        findViewById(R.id.btnVehicleDetails).setOnClickListener(this);
        findViewById(R.id.btnRegistrationDetails).setOnClickListener(this);
        findViewById(R.id.btnInsuranceDetails).setOnClickListener(this);
        findViewById(R.id.btnRegisterDriverAction).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLicenseDetails:
                licenseaction();break;
            case R.id.btnVehicleDetails:
                vehicleDetailsaction();break;
            case R.id.btnRegistrationDetails:
                vehicleRegistrationDetailsaction();break;
            case R.id.btnInsuranceDetails:
                vehicleInsuranceDetailsaction();break;
            case R.id.btnRegisterDriverAction:
                registeraction();break;

        }
    }

    private boolean validVehicleDetails() {

        String year =  BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleYearOfManufacture;
        String color =  BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleColor;
        String make =  BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleMake;
        String model =  BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleModel;

        if (Utils.isEmpty(year) || Utils.isEmpty(color) ||
                Utils.isEmpty(make) || Utils.isEmpty(model) ) {
            return false;
        }
        return true;
    }
    private boolean validateRegistrationDetails() {

        String expdate =  BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicledateOfExpiry;
        String regdate =  BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicledateOfRegistration;
        String numberplate =  BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicleNumberPlateNumber;
        String state =  BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicleRegistrationState;
        if (Utils.isEmpty(expdate) || Utils.isEmpty(regdate) ||
                Utils.isEmpty(numberplate) || Utils.isEmpty(state) )
        {
            return false;
        }
        return true;
    }
    private boolean validateInsuranceDetails() {


        String company =  BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insuranceCompany;
        String fromdate =  BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insurancedateFrom;
        String expdate =  BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insurancedateOfExpiry;
        String number =  BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insuranceNumber;
        if (Utils.isEmpty(fromdate) || Utils.isEmpty(expdate) ||
                Utils.isEmpty(company) || Utils.isEmpty(number) )
        {
            return false;
        }
        return true;
    }
    private boolean validateLicenseDetails() {

        String expdate =  BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensedateofExpiry;
        String fromdate =  BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensedateOfIssue;
        String state =  BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensestateIssued;
        String number =  BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licenseNumber;
        if (Utils.isEmpty(expdate) || Utils.isEmpty(fromdate) ||
                Utils.isEmpty(state) || Utils.isEmpty(number) )
        {
            return false;
        }
        return true;
    }
    private void registeraction() {
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            dateValidator = new DateValidator();
            firstName = (EditText) findViewById(R.id.txtdriverFirstName);
            middleName = (EditText) findViewById(R.id.txtdrivermiddleName);
            lastName = (EditText) findViewById(R.id.txtdriverLastName);

            pwd = (EditText) findViewById(R.id.txtdriverPwd);
            confirmpwd = (EditText) findViewById(R.id.txtdriverConfirmPwd);

            String name = ((EditText) findViewById(R.id.txtdriverFirstName)).getText().toString();
            if (Utils.isEmpty(name)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NAME_EMPTY, null);
                return;
            }

            email = (EditText) findViewById(R.id.txtdriveremail);
            String emailText = ((EditText) findViewById(R.id.txtdriveremail)).getText().toString();
            if (!Utils.isValidEmail(emailText)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_EMAIL_INVALID, null);
                return;
            }

            String strpwd = ((EditText) findViewById(R.id.txtdriverPwd)).getText().toString();
            String strconfirpwd = ((EditText) findViewById(R.id.txtdriverConfirmPwd)).getText().toString();

            if (!Utils.isEqualAndNotEmpty(strconfirpwd, strpwd)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_PWD_MISMATCH, null);
                return;
            }

            address1 = (EditText) findViewById(R.id.txtdriverAddress1);
            address2 = (EditText) findViewById(R.id.txtdriverAddress2);

            city = (EditText) findViewById(R.id.txtdriverCity);
            state = (EditText) findViewById(R.id.txtdriverState);
            zip = (EditText) findViewById(R.id.txtdriverZip);

            PhoneNumber = (EditText) findViewById(R.id.txtdriverPhoneNumber);

            String phone = ((EditText) findViewById(R.id.txtdriverPhoneNumber)).getText().toString();
            if (Utils.isEmpty(phone)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_PHONE_EMPTY, null);
                return;
            }

            DOB = (EditText) findViewById(R.id.txtDOB);
            String dob = ((EditText) findViewById(R.id.txtDOB)).getText().toString();
            if (Utils.isEmpty(dob)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_DOB_EMPTY, null);
                return;
            }

            boolean validDOB = dateValidator.validate(dob);
            if (!validDOB)
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_INVALID_DOB, null);
                return;
            }

            SSN = (EditText) findViewById(R.id.txtdriverSSN);
            String ssn = ((EditText) findViewById(R.id.txtdriverSSN)).getText().toString();
            if (Utils.isEmpty(ssn)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_SSN_EMPTY, null);
                return;
            }

           if (!validVehicleDetails())
           {
               Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_VEHICLE_EMPTY, null);
               return;
           }
            if (!validateRegistrationDetails())
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_REG_EMPTY, null);
                return;
            }
            if (!validateInsuranceDetails())
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_INS_EMPTY, null);
                return;
            }

            if (!validateLicenseDetails())
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_LIC_EMPTY, null);
                return;
            }

            BZAppManager.getInstance().bzDriverData.FirstName = firstName.getText().toString();
            BZAppManager.getInstance().bzDriverData.MiddleName = middleName.getText().toString();
            BZAppManager.getInstance().bzDriverData.LastName = lastName.getText().toString();
            BZAppManager.getInstance().bzDriverData.Email = email.getText().toString();
            BZAppManager.getInstance().bzDriverData.Password = pwd.getText().toString();
            BZAppManager.getInstance().bzDriverData.ConfirmPassword = confirmpwd.getText().toString();
            BZAppManager.getInstance().bzDriverData.Address1 = address1.getText().toString();
            BZAppManager.getInstance().bzDriverData.Address2 = address2.getText().toString();

            BZAppManager.getInstance().bzDriverData.City = city.getText().toString();
            BZAppManager.getInstance().bzDriverData.State = state.getText().toString();
            BZAppManager.getInstance().bzDriverData.Zip = zip.getText().toString();

            BZAppManager.getInstance().bzDriverData.PhoneNumber = PhoneNumber.getText().toString();
            BZAppManager.getInstance().bzDriverData.dob = DOB.getText().toString();
            BZAppManager.getInstance().bzDriverData.SSN = SSN.getText().toString();
            //call EULA and if accepted start the reg
            Intent myIntent = new Intent(registerDriver.this, EULA.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            registerDriver.this.startActivity(myIntent);
        }
    }

    private void licenseaction() {
        Intent myIntent = new Intent(registerDriver.this, driverLicInfo.class);
        registerDriver.this.startActivity(myIntent);
    }

    private void vehicleDetailsaction() {
        Intent myIntent = new Intent(registerDriver.this, driverVehicleInfo.class);
        registerDriver.this.startActivity(myIntent);
    }

    private void vehicleRegistrationDetailsaction() {
        Intent myIntent = new Intent(registerDriver.this, driverVehicleRegInfo.class);
        registerDriver.this.startActivity(myIntent);
    }
    private void vehicleInsuranceDetailsaction() {
        Intent myIntent = new Intent(registerDriver.this, driverInsuranceInfo.class);
        registerDriver.this.startActivity(myIntent);
    }
}
