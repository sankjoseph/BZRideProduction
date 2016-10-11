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

/**
 * Created by Santhosh.Joseph on 25-09-2016.
 */
public class DFragmentRideActive extends DialogFragment implements  View.OnClickListener  {// for rider to see
    private TextView txtDriverComing;
    private TextView txtDriverComingPhone;
    private TextView txtDriverComingVNumber;
    private TextView txtDriverComingVmodel;
    private Button btnContact;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment, container,
                false);

        btnContact = (Button)rootView.findViewById(R.id.btnContactDriver);
        btnContact.setOnClickListener(this);

        txtDriverComing = (TextView) rootView.findViewById(R.id.txtDriverComing);
        txtDriverComingPhone = (TextView) rootView.findViewById(R.id.txtDriverComingPhone);
        txtDriverComingVNumber = (TextView) rootView.findViewById(R.id.txtDriverComingVNumber);
        txtDriverComingVmodel = (TextView) rootView.findViewById(R.id.txtDriverComingVmodel);


        txtDriverComing.setText( "Name: " + BZAppManager.getInstance().bzActiveRequestDriverData.FirstName );
        txtDriverComingPhone.setText("Phone: " + BZAppManager.getInstance().bzActiveRequestDriverData.Phone );
        txtDriverComingVNumber.setText("Vehicle Number: " + BZAppManager.getInstance().bzActiveRequestDriverData.VehicleNumber);
        txtDriverComingVmodel.setText("Vehicle Model: " + BZAppManager.getInstance().bzActiveRequestDriverData.VehicleModel);

        // Show DialogFragment
        //$pushMessage = "You have a ride request from ". $firstName. " start from ". $start. " to ". $end.
        // ":".$requestId. ":".$firstName.":".$phone.":".$startLat.":".$startLong.":".$endLat.":".$endLong;
        //"Your request accepted by".":".$requestID.":".$FirstName.":".$Phone.":".$VehicleNumber. ":".$VehicleModel;


        getDialog().setTitle("BZRide");
        // Do something else
        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContactDriver:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + BZAppManager.getInstance().bzActiveRequestDriverData.Phone));
                startActivity(intent);
                dismiss();
                break;
        }
    }
}
