package bzride.com.bzride;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class driverInsuranceInfo extends AppCompatActivity implements   View.OnClickListener {
    EditText txtinsurancecompany,txtinspolicynumber,txtdateinsvalidityfrom,txtdateinsvalidityto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_insurance_info);
        findViewById(R.id.btnInsDone).setOnClickListener(this);

        txtinsurancecompany =  (EditText)  findViewById(R.id.txtinsurancecompany);
        txtinspolicynumber = (EditText)  findViewById(R.id.txtinspolicynumber);
        txtdateinsvalidityfrom = (EditText)  findViewById(R.id.txtdateinsvalidityfrom);
        txtdateinsvalidityto = (EditText)  findViewById(R.id.txtdateinsvalidityto);

        // load values if already fit in
        txtinsurancecompany.setText(BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insuranceCompany);
        txtinspolicynumber.setText(BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insuranceNumber);
        txtdateinsvalidityfrom.setText(BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insurancedateFrom);
        txtdateinsvalidityto.setText(BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insurancedateOfExpiry);

    }
    public  void onStart(){
        super.onStart();
        EditText txtdateinsvalidityfrom = (EditText)findViewById(R.id.txtdateinsvalidityfrom);
        txtdateinsvalidityfrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dlg = new DateDialog(v);
                    FragmentTransaction ftMgr = getFragmentManager().beginTransaction();
                    dlg.show(ftMgr, "");
                }

            }
        });


        EditText txtdateinsvalidityto = (EditText)findViewById(R.id.txtdateinsvalidityto);
        txtdateinsvalidityto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
            case R.id.btnInsDone:
                updateInsuranceaction();
                break;
        }
    }
    private void updateInsuranceaction() {
        BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insuranceCompany  = txtinsurancecompany.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insuranceNumber  = txtinspolicynumber.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insurancedateFrom  = txtdateinsvalidityfrom.getText().toString();
        BZAppManager.getInstance().bzDriverData.driverInsuranceInfo.insurancedateOfExpiry  = txtdateinsvalidityto.getText().toString();
        super.onBackPressed();
    }
}
