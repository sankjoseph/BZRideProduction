package bzride.com.bzride;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Santhosh.Joseph on 25-09-2016.
 */
// for driver to see
public class DFragmentDriveActive extends DialogFragment implements  View.OnClickListener  {
    private TextView txtRiderName;
    private TextView txtStartLocation;
    private TextView txtEndLocation;
    private TextView txtRiderPhone;
    private Button btnEndRide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragmentdrive, container,
                false);
        btnEndRide = (Button)rootView.findViewById(R.id.btnActiveRideDone);
        btnEndRide.setOnClickListener(this);
        //$pushMessage = "You have a ride request from ". $firstName. " start from ". $start. " to ". $end.
        // ":".$requestId. ":".$firstName.":".$phone.":".$startLat.":".$startLong.":".$endLat.":".$endLong;

        txtRiderName = (TextView) rootView.findViewById(R.id.txtRiderName);
        txtStartLocation = (TextView) rootView.findViewById(R.id.txtStartLocation);
        txtEndLocation = (TextView) rootView.findViewById(R.id.txtEndLocation);
        txtRiderPhone = (TextView) rootView.findViewById(R.id.txtRiderPhone);


        txtRiderName.setText("Name: " + BZAppManager.getInstance().currentRideRequestUserName);
        txtStartLocation.setText( "Pick up: " +   BZAppManager.getInstance().currentRideRequeststartLocation );
        txtEndLocation.setText( "Drop: " + BZAppManager.getInstance().currentRideRequestendLocation);
        txtRiderPhone.setText( "Phone: " + BZAppManager.getInstance().currentRideRequestUserPhone);
        //"Your request accepted by".":".$requestID.":".$FirstName.":".$Phone.":".$VehicleNumber. ":".$VehicleModel;


        getDialog().setTitle("BZRide");
        // Do something else
        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActiveRideDone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + BZAppManager.getInstance().currentRideRequestUserPhone));
                startActivity(intent);
                dismiss();
                break;
        }
    }
}
