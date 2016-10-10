package bzride.com.bzride;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class registeruser extends AppCompatActivity  implements View.OnClickListener, OnPostExecuteListener {
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
    private String deviceid;
    private ImageView imgView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);
        findViewById(R.id.btnRegisterRiderAction).setOnClickListener(this);
        findViewById(R.id.btnRiderCardDetail).setOnClickListener(this);

        imgView = (ImageView) findViewById(R.id.imgRiderImageView);

        Context context = registeruser.this;
        PackageManager packageManager = context.getPackageManager();
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
            Toast.makeText(registeruser.this, "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imgView.setImageBitmap(photo);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegisterRiderAction:
                registeraction();break;

            case R.id.btnRiderCardDetail:
                cardDetailsAction();break;

        }
    }
    private void cardDetailsAction() {
        Intent myIntent = new Intent(registeruser.this, usercarddetails.class);
        myIntent.putExtra("mode", "new");
        registeruser.this.startActivity(myIntent);
    }
    private void registeraction() {
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            firstName = (EditText)findViewById(R.id.txtRiderFirstName);
            String name = ((EditText) findViewById(R.id.txtRiderFirstName)).getText().toString();
            if (Utils.isEmpty(name)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NAME_EMPTY, null);
                return;
            }

            middleName = (EditText)findViewById(R.id.txtRiderMiddleName);
            lastName = (EditText)findViewById(R.id.txtRiderLastName);
            email = (EditText)findViewById(R.id.txtrideremail);
            String emailText = ((EditText) findViewById(R.id.txtrideremail)).getText().toString();
            if (!Utils.isValidEmail(emailText)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_EMAIL_INVALID, null);
                return;
            }


            pwd = (EditText)findViewById(R.id.txtriderPwd);
            confirmpwd = (EditText)findViewById(R.id.txtriderConfirmPwd);

            String strpwd = ((EditText) findViewById(R.id.txtriderPwd)).getText().toString();
            String strconfirpwd = ((EditText) findViewById(R.id.txtriderConfirmPwd)).getText().toString();

            if (!Utils.isEqualAndNotEmpty(strconfirpwd, strpwd)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_PWD_MISMATCH, null);
                return;
            }

            address1 = (EditText)findViewById(R.id.txtriderAddress1);
            address2 = (EditText)findViewById(R.id.txtriderAddress2);
            city = (EditText)findViewById(R.id.txtriderCity);
            state = (EditText)findViewById(R.id.txtriderState);
            zip = (EditText)findViewById(R.id.txtriderZip);

            PhoneNumber = (EditText)findViewById(R.id.txtriderPhoneNumber);
            String phone = ((EditText) findViewById(R.id.txtriderPhoneNumber)).getText().toString();
            if (Utils.isEmpty(phone)) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_PHONE_EMPTY, null);
                return;
            }

            if (!validCardDetails())
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_CARD_EMPTY, null);
                return;
            }

            BZAppManager.getInstance().bzRiderData.FirstName = firstName.getText().toString();
            BZAppManager.getInstance().bzRiderData.MiddleName = middleName.getText().toString();
            BZAppManager.getInstance().bzRiderData.LastName = lastName.getText().toString();
            BZAppManager.getInstance().bzRiderData.Email = email.getText().toString();
            BZAppManager.getInstance().bzRiderData.Password = pwd.getText().toString();
            BZAppManager.getInstance().bzRiderData.ConfirmPassword = confirmpwd.getText().toString();
            BZAppManager.getInstance().bzRiderData.Address1 = address1.getText().toString();
            BZAppManager.getInstance().bzRiderData.Address2 = address2.getText().toString();
            BZAppManager.getInstance().bzRiderData.City = city.getText().toString();
            BZAppManager.getInstance().bzRiderData.State = state.getText().toString();
            BZAppManager.getInstance().bzRiderData.Zip = zip.getText().toString();
            BZAppManager.getInstance().bzRiderData.PhoneNumber = PhoneNumber.getText().toString();

            //call EULA and if accepted start the reg
            Intent myIntent = new Intent(registeruser.this, EULA.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            registeruser.this.startActivity(myIntent);

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }


    private boolean validCardDetails() {

        String cardType =  BZAppManager.getInstance().bzRiderData.cardData.cardType;
        String cardVendor =  BZAppManager.getInstance().bzRiderData.cardData.cardVendor;
        String cardNumber =  BZAppManager.getInstance().bzRiderData.cardData.cardNumber;
        String cardExpiryMonth =  BZAppManager.getInstance().bzRiderData.cardData.cardExpiryMonth;
        String cardExpiryYear =  BZAppManager.getInstance().bzRiderData.cardData.cardExpiryYear;
        String cardCVV =  BZAppManager.getInstance().bzRiderData.cardData.cardCVV;

        if (Utils.isEmpty(cardType) || Utils.isEmpty(cardVendor) ||
                Utils.isEmpty(cardNumber) || Utils.isEmpty(cardExpiryMonth) ||
                Utils.isEmpty(cardExpiryYear) || Utils.isEmpty(cardCVV))
        {
            return  false;
        }
        return  true;
    }
    @Override
    public void onSuccess(BZJSONResp model) {

        RegisterResp response = (RegisterResp)model;
        if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {

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
