package bzride.com.bzride;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//OnMapReadyCallback
public class Home extends AppCompatActivity /*FragmentActivity*/  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks ,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LocationListener,OnPostExecuteListener,
        GoogleMap.OnMarkerClickListener,NavigationView.OnNavigationItemSelectedListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int ACCESS_MAPS_PERMISSIONS_REQUEST = 1;

    protected  GoogleApiClient mGoogleApiClient;
    private boolean connectedstatus;
    private static final LatLngBounds BOUNDS_INDIA  = new LatLngBounds (new LatLng(-0,0), new LatLng(-0,0));

    LatLng m_latLng;
    LatLng selectedpick;
    LatLng selecteddrop;

    private GoogleMap m_map;
    private LocationRequest mLocationRequest;
    private Context myContext;
    FragmentManager fm = getSupportFragmentManager();
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Your request accepted by:09:Shenjin:9164263900:6TKH159:Q7
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");


            String[] separated = message.split(":");//0,1,2,3,4,5
            if (separated.length > 1) {
                BZAppManager.getInstance().bzActiveRequestDriverData.RequestID = separated[1].toString();
                BZAppManager.getInstance().bzActiveRequestDriverData.FirstName = separated[2].toString();
                BZAppManager.getInstance().bzActiveRequestDriverData.Phone = separated[3].toString();
                BZAppManager.getInstance().bzActiveRequestDriverData.VehicleNumber = separated[4].toString();
                BZAppManager.getInstance().bzActiveRequestDriverData.VehicleModel = separated[5].toString();
                BZAppManager.getInstance().currentRideRequestId = BZAppManager.getInstance().bzActiveRequestDriverData.RequestID;

                DFragmentRideActive dFragment = new DFragmentRideActive();
                /*Bundle args = new Bundle();
                args.putString("message",message);
                dFragment.setArguments(args);*/
                dFragment.show(fm, "Dialog Fragment");
            }
            else
            {
                DFragmentNotifiedAlert dFragment = new DFragmentNotifiedAlert();
                dFragment.show(fm, "Dialog Fragment");
            }

      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.btnRequest).setOnClickListener(this);
        findViewById(R.id.btnSchedule).setOnClickListener(this);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rider);
        if (toolbar != null) {
            setSupportActionBar(toolbar);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_rider);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_rider);
            navigationView.setNavigationItemSelectedListener(this);
           setTitle("BZRide");
        }



// testing


        //keep the token
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler();
            api.setPostExecuteListener(this);

            /*String urlCall = Utils.BASE_URL + Utils.UPDATE_DEVICE_TOKEN_URL;
            String params = "&token="+ usertoken + "&devicetoken=" + devicetoken +"&usertype=" + usertype ;
            api.putDetails(urlCall, Utils.UPDATE_DEVICE_TOKEN_URL, params);*/

            String urlCall = Utils.BASE_URL + Utils.GET_RIDER_PROFILE_URL;
            String params = "&token="+ usertoken ;
            api.putDetails(urlCall, Utils.GET_RIDER_PROFILE_URL, params);

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }

        //map related old code

        FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mySupportMapFragment = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.map);
        mySupportMapFragment.getMapAsync(this);


        /*FragmentManager myFragmentManager = getSupportFragmentManager();
        CustomMapFragment mySupportMapFragment = (CustomMapFragment)myFragmentManager.findFragmentById(R.id.mapCustom);
        mySupportMapFragment.onCreate(savedInstanceState);*/
       // mySupportMapFragment.getMapAsync(this);



        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        try
        {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();
            mGoogleApiClient.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_rider);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_rider_profile) {
            Intent myIntent = new Intent(Home.this, updateriderprofile.class);
            Home.this.startActivity(myIntent);

        }
        else if (id == R.id.nav_rider_card_details) {
            Intent myIntent = new Intent(Home.this, usercarddetails.class);
            myIntent.putExtra("mode", "edit");
            Home.this.startActivity(myIntent);
        }

        /*else if (id == R.id.nav_rider_ride_history)
        {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_NOT_READY, null);
        }*/

        else if (id == R.id.nav_rider_ride_active)
        {
            if  (!Utils.isEmpty(BZAppManager.getInstance().currentRideRequestId)){
                DFragmentRideActive dFragment = new DFragmentRideActive();
                /*Bundle args = new Bundle();
                args.putString("message", BZAppManager.getInstance().currentRideRequestMessage);
                dFragment.setArguments(args);*/
                dFragment.show(fm, "Dialog Fragment");
            }
            else
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_NO_ACTIVE_RIDE, null);
            }

        }

        else if (id == R.id.nav_rider_contact) {
            Intent myIntent = new Intent(Home.this, ContactInfo.class);
            Home.this.startActivity(myIntent);
        }

        else if (id == R.id.nav_rider_about) {
            // not required
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_rider);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onSuccess(BZJSONResp model) {

        BZJSONResp response = (BZJSONResp)model;
        if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {

            if (response.info.toString().contains("Get Profile")) {
                GetProfileInfoResp responseObj = (GetProfileInfoResp)model;
                BZAppManager.getInstance().bzRiderData.FirstName = responseObj.firstName;
                BZAppManager.getInstance().bzRiderData.MiddleName = responseObj.middleName;
                BZAppManager.getInstance().bzRiderData.LastName = responseObj.lastName;
                BZAppManager.getInstance().bzRiderData.Email = responseObj.email;
                BZAppManager.getInstance().bzRiderData.Address1 = responseObj.address1;
                BZAppManager.getInstance().bzRiderData.Address2 = responseObj.address2;
                BZAppManager.getInstance().bzRiderData.City = responseObj.city;
                BZAppManager.getInstance().bzRiderData.State = responseObj.state;
                BZAppManager.getInstance().bzRiderData.Zip = responseObj.zip;
            }
        }
        else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, response.info, null);
        }
    }


    @Override
    public void onFailure() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_SERVER, null);
    }

    @Override
    public boolean onMarkerClick(Marker arg0) {


        arg0.showInfoWindow();
        return false;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        m_map = googleMap;

        m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        m_map.setTrafficEnabled(true);
        m_map.setOnMarkerClickListener(this);
        enableMyLocation();

        UiSettings settings = m_map.getUiSettings();
        settings.setScrollGesturesEnabled(true);
        settings.setRotateGesturesEnabled(true);
        settings.setZoomControlsEnabled(true);
        settings.setZoomGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setAllGesturesEnabled(true);
        settings.setCompassEnabled(true);

    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_MAPS_PERMISSIONS_REQUEST);
        } else if (m_map != null) {
            // Access to the location has been granted to the app.
            m_map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        connectedstatus = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                 ActivityCompat.requestPermissions(this,
                         new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                         ACCESS_MAPS_PERMISSIONS_REQUEST);
        }
        else {
            m_map.setMyLocationEnabled(true);
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            else {
                handleNewLocation(location);
            }
        }

    }

    @Override
       public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original ACCESS_MAPS_PERMISSIONS_REQUEST
        if (requestCode == ACCESS_MAPS_PERMISSIONS_REQUEST) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                m_map.setMyLocationEnabled(true);
                if (location == null) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                }
                else {
                    handleNewLocation(location);
                }
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    public String getCountryCode(LatLng latlong) {
        String countryReturn = "";
        Geocoder geocoder = new Geocoder(Home.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latlong.latitude, latlong.longitude, 1);
            Address obj = addresses.get(0);
            countryReturn = obj.getCountryCode();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return countryReturn;
    }
    public String getAddress(LatLng latlong) {
        String addressReturn = "";
        Geocoder geocoder = new Geocoder(Home.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latlong.latitude, latlong.longitude, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            /*add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();*/

            Log.v("IGA", "Address" + add);
            addressReturn = add;


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return addressReturn;
    }
    private void handleNewLocation(Location location) {

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        m_latLng = new LatLng(currentLatitude, currentLongitude);

        String locationAddress = getAddress(m_latLng);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(m_latLng)
                .title(locationAddress);
        //m_map.addMarker(options);
        //m_map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        float zoomLevel = 16; //This goes up to 21
        m_map.moveCamera(CameraUpdateFactory.newLatLngZoom(m_latLng, zoomLevel));
        //update location in server

    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("request_accepted"));
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    private void setUpMapIfNeeded() {
            if (m_map != null) {
                m_map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
            }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRequest:
                requestNowAction();
                break;
            case R.id.btnSchedule:
                scheduleNowAction();
                break;
        }
    }

    private void scheduleNowAction() {
    //call web service //to do
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_NOT_READY, null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {//after pickup location
            //show place finder
            Intent myIntent = new Intent(Home.this, PlaceFinder.class);
            //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            myIntent.putExtra("LocationOption", "Drop");
            Home.this.startActivityForResult(myIntent, 2);
        }
        if (requestCode == 2)//after drop location
        {
            //check for valid pickup and drop
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabledGPS = service
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Check if enabled and if not send user to the GSP settings
            // Better solution would be to display a dialog and suggesting to
            // go to the settings
            if (!enabledGPS) {
                Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                return;
            }

            selectedpick =  BZAppManager.getInstance().selectedPickUpLocation;

            if(Utils.isEmptyLocation(selectedpick))
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, "Please specify pickup location", null);
                return;
            }
            selecteddrop = BZAppManager.getInstance().selectedDropLocation;

            if(Utils.isEmptyLocation(selecteddrop))
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, "Please specify drop location", null);
                return;
            }
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                    switch (choice) {
                        case DialogInterface.BUTTON_POSITIVE:
                            createRequest();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            return;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setMessage(Utils.MSG_REQUEST_CONFIRM)
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();



        }
    }//onActivityResult

    private void createRequest() {

        //call web service
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setMessage("Creating ride request...");

            String urlCall = Utils.BASE_URL + Utils.RIDE_REQUEST_I_URL ;

            String locationStartAddress = getAddress(selectedpick);
            String locationEndAddress = getAddress(selecteddrop);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String userid = sharedPreferences.getString(QuickstartPreferences.USER_ID, null);

            String params = "&requestorId="+ userid + "&startLocation=" + locationStartAddress + "&endLocation=" + locationEndAddress +
                    "&startLat=" + selectedpick.latitude +  "&startLong=" + selectedpick.longitude +
                    "&endLat=" +selecteddrop.latitude +  "&endLong=" + selecteddrop.longitude;//todo fix

            api.putDetails(urlCall, Utils.RIDE_REQUEST_I_URL, params);
            api.setPostExecuteListener(this);
        }
        else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }
    private boolean isLocationEnabled()
    {

        LocationManager lm =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            return false   ;
        }
        return true;
    }
    private void requestNowAction() {
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            if (!isLocationEnabled())
            {
                Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                return;
            }
            String countryCode =  getCountryCode(m_latLng);
            if (countryCode.equals("US"))
            {
                final CharSequence[] items = { "Automatically", "Manually",
                        "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Select Pickup location!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        String userChoosenTask;

                        if (items[index].equals("Automatically")) {
                            //start location as m_latLng
                            BZAppManager.getInstance().selectedPickUpLocation = m_latLng;
                            Intent myIntent = new Intent(Home.this, PlaceFinder.class);
                            //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            myIntent.putExtra("LocationOption", "Drop");
                            Home.this.startActivityForResult(myIntent, 2);
                        } else if (items[index].equals("Manually")) {
                            //show place finder
                            Intent myIntent = new Intent(Home.this, PlaceFinder.class);
                            //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            myIntent.putExtra("LocationOption", "PickUp");
                            Home.this.startActivityForResult(myIntent, 1);
                        } else if (items[index].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
            else
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_BZRIDE, null);
            }
        }
        else{
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }

    public GoogleApiClient getAPIClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
      /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i("TAG", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }
}