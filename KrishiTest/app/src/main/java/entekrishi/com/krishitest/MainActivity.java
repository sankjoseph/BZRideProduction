package entekrishi.com.krishitest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.provider.Settings.Secure;

import entekrishi.com.krishitest.model.Data;
import entekrishi.com.krishitest.model.ModelClassMapper;
import java.util.ArrayList;
import entekrishi.com.krishitest.model.loginRsp;
import entekrishi.com.krishitest.Rest.*;
import entekrishi.com.krishitest.common.*;

public class MainActivity extends ActionBarActivity implements View.OnClickListener, OnPostExecuteListener {
    private Button btnLogin;
    private EditText txtUserName;
    private  EditText txtPwd;
    private String deviceid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String datevalues= (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date());

        btnLogin = (Button) findViewById(R.id.button_login) ;
        txtUserName = (EditText) findViewById(R.id.username) ;
        txtUserName.setText("kirankrishnan@axtecindia.com");
        txtPwd = (EditText) findViewById(R.id.password) ;
        txtPwd.setText("kiran123");
        findViewById(R.id.button_login).setOnClickListener(this);
        //getting unique id for device
        deviceid = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                login();
                break;
        }
    }

    private void login() {
        if (Data.getInstance().isNetworkConnected( getApplicationContext()))
        {
            RestApi api = new RestApi(this);
            api.setMessage("Authenticating...");
            api.setPostExecuteListener(this);
            String urlCall = Utils.BASE_URL + Utils.LOGIN_URL+ "?username="+ txtUserName.getText()+"&password="+txtPwd.getText()+"&device="+deviceid;
            api.get(urlCall,Utils.LOGIN_URL);
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle(Utils.MSG_TITLE)
                    .setMessage(Utils.MSG_NO_INTERNET)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            timeUp();
                        }
                    })
                    .show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSuccess(ModelClassMapper model) {
        loginRsp response = (loginRsp)model;
        if (response.msg.toString().equalsIgnoreCase(Utils.LOGIN_SUCCESS))
        {
            // take token;
            Data.getInstance().setToken(response.token);
            startActivity(new Intent(this, HomeTab.class));
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle(Utils.MSG_TITLE)
                    .setMessage(response.msg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            timeUp();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onFailure() {

    }
    private void timeUp() {

        //finish();
        // show result page
    }
}
