package bzride.com.bzride;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BZLanding extends AppCompatActivity implements View.OnClickListener{
// NavigationView.OnNavigationItemSelectedListener

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bzlanding);

        findViewById(R.id.btnLoginDriver).setOnClickListener(this);
        findViewById(R.id.btnLoginRider).setOnClickListener(this);

        findViewById(R.id.btnRegisterDriver).setOnClickListener(this);
        findViewById(R.id.btnRegisterRider).setOnClickListener(this);


       /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


       mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();
        mActivityTitle = getTitle().toString();
        setupDrawer();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BZLanding.this, "cliked!", Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginDriver:
                BZAppManager.getInstance().isDriver = true;
                loginaction();
                break;
            case R.id.btnLoginRider:
                BZAppManager.getInstance().isDriver = false;
                loginaction();
                break;
            case R.id.btnRegisterDriver:
                BZAppManager.getInstance().isDriver = true;
                resgisteractionDriver();
                break;
            case R.id.btnRegisterRider:
                BZAppManager.getInstance().isDriver = false;
                resgisteractionRider();
                break;
        }
    }


    private void loginaction() {

        Intent myIntent = new Intent(BZLanding.this, login.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BZLanding.this.startActivity(myIntent);

    }

    private void resgisteractionDriver() {

        Intent myIntent = new Intent(BZLanding.this, registerDriver.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BZLanding.this.startActivity(myIntent);
    }

    private void resgisteractionRider() {

        Intent myIntent = new Intent(BZLanding.this, registeruser.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BZLanding.this.startActivity(myIntent);
    }


}
