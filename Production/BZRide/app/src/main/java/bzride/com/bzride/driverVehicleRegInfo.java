package bzride.com.bzride;

import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class driverVehicleRegInfo extends AppCompatActivity implements   View.OnClickListener{
    EditText txtvehicleplatenumber,txtvehiclestate,txtvehicledateOfReg,txtvehicledateOfExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_vehicle_reg_info);
        findViewById(R.id.btnVehicleRegDone).setOnClickListener(this);

        txtvehicleplatenumber = (EditText)  findViewById(R.id.txtvehicleplatenumber);
        txtvehiclestate = (EditText)  findViewById(R.id.txtvehiclestate);
        txtvehicledateOfReg = (EditText)  findViewById(R.id.txtvehicledateOfReg);
        txtvehicledateOfExpiry = (EditText)  findViewById(R.id.txtvehicledateOfExpiry);

        // load values if already fit in
        txtvehicleplatenumber.setText(BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicleNumberPlateNumber);
        txtvehiclestate.setText(BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicleRegistrationState);
        txtvehicledateOfReg.setText(BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicledateOfRegistration);
        txtvehicledateOfExpiry.setText(BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicledateOfExpiry);

    }
    public  void onStart(){
        super.onStart();
        EditText txtvehicledateOfReg = (EditText)findViewById(R.id.txtvehicledateOfReg);
        txtvehicledateOfReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dlg = new DateDialog(v);
                    FragmentTransaction ftMgr = getFragmentManager().beginTransaction();
                    dlg.show(ftMgr, "");
                }

            }
        });


        EditText txtvehicledateOfExpiry = (EditText)findViewById(R.id.txtvehicledateOfExpiry);
        txtvehicledateOfExpiry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dlg = new DateDialog(v);
                    FragmentTransaction ftMgr = getFragmentManager().beginTransaction();
                    dlg.show(ftMgr, "");
                }

            }
        });
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVehicleRegDone:
                updateVehicleRegInfoaction();
                break;
        }
    }
    private void updateVehicleRegInfoaction() {
        BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicleNumberPlateNumber  = txtvehicleplatenumber.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicleRegistrationState  = txtvehiclestate.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicledateOfRegistration  = txtvehicledateOfReg.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverVehRegInfo.vehicledateOfExpiry  = txtvehicledateOfExpiry.getText().toString();
        super.onBackPressed();
    }
}
