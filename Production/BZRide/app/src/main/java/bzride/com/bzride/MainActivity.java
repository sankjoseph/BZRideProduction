package bzride.com.bzride;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity  {

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);
        String usertype = sharedPreferences.getString(QuickstartPreferences.USER_TYPE, null);
        if (Utils.isEqualAndNotEmpty(usertype, "Driver")) {
            BZAppManager.getInstance().isDriver = true;
        }

       /* Intent myIntent = new Intent(MainActivity.this, registeruser.class);
        //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW
        MainActivity.this.startActivity(myIntent);
        return;*/
         /*       Intent myIntent2 = new Intent(MainActivity.this, BZLanding.class);
        //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW
        MainActivity.this.startActivity(myIntent2);
        return;*/

        /*Intent myIntent2 = new Intent(MainActivity.this, driverBankInfo.class);
        //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW
        MainActivity.this.startActivity(myIntent2);
        return;*/

        /*Intent myIntent = new Intent(MainActivity.this, HomeDriver.class);
        //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW
        MainActivity.this.startActivity(myIntent);
        return;*/

        //String pass = "Password1";
        //String encryptedPass =  Utils.md5encrypt(pass);


        /*Intent myIntent = new Intent(MainActivity.this, registerDriver.class);
        //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainActivity.this.startActivity(myIntent);*/

        /*if (BZAppManager.getInstance().isDriver ) {
            Intent myIntent = new Intent(MainActivity.this, HomeDriver.class);
            //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW
            MainActivity.this.startActivity(myIntent);
        }
        else {
            Intent myIntent = new Intent(MainActivity.this, Home.class);
            //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW
            MainActivity.this.startActivity(myIntent);
        }*/

        /*BZAppManager.getInstance().currentUserId ="5";
        Intent myIntent = new Intent(MainActivity.this, driverBankInfo.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainActivity.this.startActivity(myIntent);
        return;*/

        //good

      if (!Utils.isEmpty(usertoken))
        {
            if (BZAppManager.getInstance().isDriver == true)
            {
                Intent myIntent = new Intent(MainActivity.this, HomeDriver.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MainActivity.this.startActivity(myIntent);
            }
            else
            {
                Intent myIntent = new Intent(MainActivity.this, Home.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MainActivity.this.startActivity(myIntent);
            }
        }
        else
        {
            Intent myIntent = new Intent(MainActivity.this, BZLanding.class);
            //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            MainActivity.this.startActivity(myIntent);
        }
        //good
    }


}
