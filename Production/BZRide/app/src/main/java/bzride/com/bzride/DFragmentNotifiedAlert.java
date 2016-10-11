package bzride.com.bzride;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Santhosh.Joseph on 04-10-2016.
 */
public class DFragmentNotifiedAlert extends DialogFragment implements  View.OnClickListener {
    private TextView txtUserMessage;
    private Button btnDone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragmentcontacteachother, container,
                false);
        btnDone = (Button)rootView.findViewById(R.id.btnMessageDone);
        btnDone.setOnClickListener(this);

        txtUserMessage = (TextView) rootView.findViewById(R.id.txtUserMessage);
        txtUserMessage.setText(BZAppManager.getInstance().currentRideRequestMessage);

        getDialog().setTitle("BZRide");
        // Do something else
        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMessageDone:
                dismiss();
                break;
        }
    }
}
