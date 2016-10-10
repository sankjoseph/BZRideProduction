package bzride.com.bzride;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class driverVehicleInfo extends AppCompatActivity implements   View.OnClickListener{
    EditText txtvehicleyear,txtvehiclemodel,txtvehiclemake,txtvehiclecolor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_vehicle_info);
        findViewById(R.id.btnVehicleDone).setOnClickListener(this);

        txtvehicleyear = (EditText)  findViewById(R.id.txtvehicleyear);
        txtvehiclemodel = (EditText)  findViewById(R.id.txtvehiclemodel);
        txtvehiclemake = (EditText)  findViewById(R.id.txtvehiclemake);
        txtvehiclecolor = (EditText)  findViewById(R.id.txtvehiclecolor);


        // load values if already fit in
        txtvehicleyear.setText(BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleYearOfManufacture);
        txtvehiclemodel.setText(BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleModel);
        txtvehiclemake.setText(BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleMake);
        txtvehiclecolor.setText(BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleColor);


    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVehicleDone:
                updateVehicleInfoaction();
                break;
        }
    }
    private void updateVehicleInfoaction() {
        BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleYearOfManufacture  = txtvehicleyear.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleModel  = txtvehiclemodel.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleMake  = txtvehiclemake.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverVehicleInfo.vehicleColor  = txtvehiclecolor.getText().toString();
        super.onBackPressed();
    }
}

