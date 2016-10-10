package bzride.com.bzride;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class driverLicInfo extends AppCompatActivity implements   View.OnClickListener{
    EditText txtlicensenumber,txtstatelicenseissued,txtdatelicenseissued,txtdatelicenseexpiry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_lic_info);
        findViewById(R.id.btnLicDone).setOnClickListener(this);

        txtlicensenumber = (EditText)  findViewById(R.id.txtlicensenumber);
        txtstatelicenseissued = (EditText)  findViewById(R.id.txtstatelicenseissued);
        txtdatelicenseissued = (EditText)  findViewById(R.id.txtdatelicenseissued);
        txtdatelicenseexpiry = (EditText)  findViewById(R.id.txtdatelicenseexpiry);

        // load values if already fit in
        txtlicensenumber.setText(BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licenseNumber);
        txtstatelicenseissued.setText(BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensestateIssued);
        txtdatelicenseissued.setText(BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensedateOfIssue);
        txtdatelicenseexpiry.setText(BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensedateofExpiry);

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLicDone:
                updateLicenseaction();
                break;
        }
    }
    private void updateLicenseaction() {
        BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licenseNumber  = txtlicensenumber.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensestateIssued  = txtstatelicenseissued.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensedateOfIssue  = txtdatelicenseissued.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverLicenseInfo.licensedateofExpiry  = txtdatelicenseexpiry.getText().toString();
        super.onBackPressed();
    }
    public  void onStart(){
        super.onStart();
        EditText txtDatelicIssued = (EditText)findViewById(R.id.txtdatelicenseissued);
        txtDatelicIssued.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dlg = new DateDialog(v);
                    FragmentTransaction ftMgr = getFragmentManager().beginTransaction();
                    dlg.show(ftMgr, "");
                }

            }
        });


        EditText txtDatelicExpiry = (EditText)findViewById(R.id.txtdatelicenseexpiry);
        txtDatelicExpiry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dlg = new DateDialog(v);
                    FragmentTransaction ftMgr = getFragmentManager().beginTransaction();
                    dlg.show(ftMgr,"");
                }

            }
        });
    }
}