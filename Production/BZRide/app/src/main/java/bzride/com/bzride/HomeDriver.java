package bzride.com.bzride;
// android:value="AIzaSyB0Vcr-J6ZqU1qg-yQ04rP2avZdpBBBsA8"
//android:value="AIzaSyCeUKXWdk9-P8kapOznl3cWyWVppLk7w7M" /> prod
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

public class HomeDriver extends AppCompatActivity  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks ,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LocationListener, OnPostExecuteListener,
        GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int ACCESS_MAPS_PERMISSIONS_REQUEST = 1;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    public static final int gcm_defaultSenderId=0x7f060034;
    ////

    ///////
    protected  GoogleApiClient mGoogleApiClient;
    private boolean connectedstatus;
    private boolean isOffline;
    private static final LatLngBounds BOUNDS_INDIA  = new LatLngBounds (new LatLng(-0,0), new LatLng(-0,0));

    LatLng m_latLng;

    private GoogleMap m_map;
    private LocationRequest mLocationRequest;
    private boolean onlineOption;
    Button btnArriveStartFinish;
    Button btnToggle;
    boolean bFinishRide;
    boolean bRequestAttended;
    boolean bArriving;
    boolean bArrived;

    FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
        isOffline = false;//default online
        findViewById(R.id.btnToggleOffline).setOnClickListener(this);
        findViewById(R.id.btnFinish).setOnClickListener(this);

        btnArriveStartFinish =  (Button) findViewById(R.id.btnFinish);
        btnToggle  =  (Button)findViewById(R.id.btnToggleOffline);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);
        String devicetoken = sharedPreferences.getString(QuickstartPreferences.DEVICE_TOKEN, null);
        String usertype = sharedPreferences.getString(QuickstartPreferences.USER_TYPE, null);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            setTitle("BZRide");
        }

        //keep the token
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler();
            api.setPostExecuteListener(this);

            /*if (BZAppManager.getInstance().isDriver == true) {
                String urlCall = Utils.BASE_URL + Utils.UPDATE_DEVICE_TOKEN_URL;
                String params = "&token="+ usertoken + "&devicetoken=" + devicetoken +"&usertype=" + usertype ;
                api.putDetails(urlCall, Utils.UPDATE_DEVICE_TOKEN_URL, params);
            }*/

            if (BZAppManager.getInstance().isDriver == true) {
                String urlCall = Utils.BASE_URL + Utils.GET_DRIVER_PROFILE_URL;
                String params = "&token="+ usertoken ;
                api.putDetails(urlCall, Utils.GET_DRIVER_PROFILE_URL, params);
            }
        } //update silent


        //todo


        /// get offline status and set;

        //?isOffline =
        FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mySupportMapFragment = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.mapdriver);
        mySupportMapFragment.getMapAsync(this);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_driver_profile) {
            Intent myIntent = new Intent(HomeDriver.this, updatedriverprofile.class);
            HomeDriver.this.startActivity(myIntent);
        }
        else if (id == R.id.nav_driver_bankinfo) {
            Intent myIntent = new Intent(HomeDriver.this, driverBankInfo.class);
            myIntent.putExtra("mode", "edit");
            HomeDriver.this.startActivity(myIntent);
        }
        else if (id == R.id.nav_driver_cardinfo) {
            Intent myIntent = new Intent(HomeDriver.this, usercarddetails.class);// todo add param and edit for server
            myIntent.putExtra("mode", "edit");
            HomeDriver.this.startActivity(myIntent);
        }
        else if (id == R.id.nav_driver_ride_cancel) {
            if  (!Utils.isEmpty(BZAppManager.getInstance().currentRideRequestId)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                        .setTitle("BZRide")
                        .setMessage("Do you want to cancel the ride now?")
                        .setCancelable(false)
                        .setPositiveButton("Cancel Now", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                CancelAction();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
            else
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_NO_ACTIVE_RIDE, null);
            }
        }


        /*else if (id == R.id.nav_driver_ride_history)
        {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_NOT_READY, null);
        }*/

        else if (id == R.id.nav_driver_ride_active)
        {
            if  (!Utils.isEmpty(BZAppManager.getInstance().currentRideRequestId)){
                DFragmentDriveActive dFragment = new DFragmentDriveActive();
                dFragment.show(fm, "Dialog Fragment");
            }
            else
            {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_NO_ACTIVE_RIDE, null);
            }
        }

        else if (id == R.id.nav_driver_contact) {
            Intent myIntent = new Intent(HomeDriver.this, ContactInfo.class);
            HomeDriver.this.startActivity(myIntent);
        }

        else if (id == R.id.nav_driver_request_ride) {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_NOT_READY, null);
        }


        else if (id == R.id.nav_driver_about) {
            // not required
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){


        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }
    private void ArrivingRideNowAction() {

        UpdateLocation();
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", m_latLng.latitude, m_latLng.longitude,
                BZAppManager.getInstance().selectedPickUpLocation.latitude, BZAppManager.getInstance().selectedPickUpLocation.longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
        //String url = getDirectionsUrl( BZAppManager.getInstance().selectedPickUpLocation , BZAppManager.getInstance().selectedDropLocation);
        return;

    }
    private void startRideAction() {

        UpdateLocation();
        double currentLat = m_latLng.latitude;
        double currentLong = m_latLng.longitude;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);

        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setPostExecuteListener(this);
            api.setMessage("Starting ride...");
            String urlCall = Utils.BASE_URL + Utils.START_RIDE_URL;
            //driver uses currentRideRequestId to start ride;
            String params = "token=" + usertoken + "&rideRequestId=" + BZAppManager.getInstance().currentRideRequestId +
                    "&currentLat=" +currentLat + "&currentLong=" +currentLong ;
            api.putDetails(urlCall, Utils.START_RIDE_URL, params);
        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }

    private void endRideAction() {
        if (BZAppManager.getInstance().isDriver == true) {
            UpdateLocation();
            double currentLat = m_latLng.latitude;
            double currentLong = m_latLng.longitude;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);

            if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
                BZRESTApiHandler api = new BZRESTApiHandler(this);
                api.setPostExecuteListener(this);
                api.setMessage("Ending ride...");
                String urlCall = Utils.BASE_URL + Utils.END_RIDE_URL;
                //driver uses currentRideRequestId to end ride;
                String params = "token=" + usertoken + "&rideRequestId=" + BZAppManager.getInstance().currentRideRequestId +
                        "&ActualEndLat=" + currentLat + "&ActualEndLong=" + currentLong;
                api.putDetails(urlCall, Utils.END_RIDE_URL, params);
            }
        }
    }

    private void NotifyArrivedAction() {
        //keep the token
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);
            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setMessage("Notifying rider....");
            api.setPostExecuteListener(this);

            if (BZAppManager.getInstance().isDriver == true) {
                String urlCall = Utils.BASE_URL + Utils.ARRIVE_RIDE_URL;
                String params= "token="+ usertoken + "&rideRequestId=" + BZAppManager.getInstance().currentRideRequestId;
                api.putDetails(urlCall, Utils.ARRIVE_RIDE_URL,params);
            }

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }
    private void CancelAction() {

        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setMessage("Cancelling ride...");
            api.setPostExecuteListener(this);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);

            if (BZAppManager.getInstance().isDriver == true) {
                String urlCall = Utils.BASE_URL + Utils.CANCEL_RIDE_URL ;
                String params =  "token="+ usertoken + "&rideRequestId=" + BZAppManager.getInstance().currentRideRequestId;
                api.putDetails(urlCall, Utils.CANCEL_RIDE_URL, params);
            }

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }

    }
    private void StartAction() {
        // call service for endride
        //driver uses currentRideRequestId to end ride;
        if  (!Utils.isEmpty(BZAppManager.getInstance().currentRideRequestId)){
            if (bFinishRide )
            {
                endRideAction();
                return;
            }

            if (bArriving )
            {
                // notify readiness to rider
                NotifyArrivedAction();
                bArriving = false;
                return;
            }

            if (bArrived)
            {
                bArrived = false;
                startRideAction();
                return;
            }
            if (!bRequestAttended)
            {
                bRequestAttended =  true;
                ArrivingRideNowAction();
                btnArriveStartFinish.setText("Arrived");
                bArriving  = true;
                bArrived =  false;
            }

        }
        else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_ERROR_NO_ACTIVE_RIDE, null);
        }
    }
    @Override
    public void onSuccess(BZJSONResp model) {



        BZJSONResp response = (BZJSONResp)model;
        if (response.status.toString().equalsIgnoreCase(Utils.STATUS_SUCCESS)) {

            if (response.info.toString().contains("Arrive ride"))
            {
                btnArriveStartFinish.setText("Start Ride");
                bArriving = false;
                bArrived = true;
            }

            if (response.info.toString().contains("Start ride"))
            {
                btnArriveStartFinish.setText("Finish");
                bFinishRide = true;
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", m_latLng.latitude, m_latLng.longitude,
                        BZAppManager.getInstance().selectedDropLocation.latitude, BZAppManager.getInstance().selectedDropLocation.longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                //String url = getDirectionsUrl( BZAppManager.getInstance().selectedPickUpLocation , BZAppManager.getInstance().selectedDropLocation);
                return;
            }
            if (response.info.toString().contains("Cancel ride")) {
                btnArriveStartFinish.setText("Start");
                bFinishRide = false;
                clearCurrentRequestInfo();
            }
            if (response.info.toString().contains("End ride"))
            {
                btnArriveStartFinish.setText("Start");
                bFinishRide = false;
                EndRideRsp responsefare = (EndRideRsp)model;
                String  fareMessage =  "last ride fare value $ " + responsefare.faredriver + "\n" + "Todays total " + responsefare.faredriverdaytotal;
                Utils.showInfoDialog(this, Utils.MSG_TITLE, fareMessage, null);
                clearCurrentRequestInfo();
                return;
            }

            if (response.info.toString().contains("Get Profile")) {
                GetProfileInfoResp responseObj = (GetProfileInfoResp)model;
                BZAppManager.getInstance().bzDriverData.FirstName = responseObj.firstName;
                BZAppManager.getInstance().bzDriverData.MiddleName = responseObj.middleName;
                BZAppManager.getInstance().bzDriverData.LastName = responseObj.lastName;
                BZAppManager.getInstance().bzDriverData.Email = responseObj.email;
                BZAppManager.getInstance().bzDriverData.availableStatus = responseObj.userStatus;
                BZAppManager.getInstance().bzDriverData.Address1 = responseObj.address1;
                BZAppManager.getInstance().bzDriverData.Address2 = responseObj.address2;
                BZAppManager.getInstance().bzDriverData.City = responseObj.city;
                BZAppManager.getInstance().bzDriverData.State = responseObj.state;
                BZAppManager.getInstance().bzDriverData.Zip = responseObj.zip;

                if (Utils.isEqualAndNotEmpty(BZAppManager.getInstance().bzDriverData.availableStatus, "A"))
                {
                    btnToggle.setText("Go Offline");
                    btnToggle.setBackgroundColor(Color.GREEN);
                    isOffline = false;
                }
                else
                {
                    btnToggle.setText("Go Online");
                    btnToggle.setBackgroundColor(Color.RED );
                    isOffline = true;
                }


                return;
            }


           if (response.info.toString().contains("Update driver status")) {
               if (onlineOption) {
                   if (isOffline)// now offline
                   {
                       // change btn caption
                       btnToggle.setText("Go Offline");
                       btnToggle.setBackgroundColor(Color.GREEN);
                       isOffline = false;
                   } else {//now online
                       // change btn caption
                       btnToggle.setText("Go Online");//red
                       btnToggle.setBackgroundColor(Color.RED);
                       isOffline = true;
                   }
               }
           }
        }
        else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, response.info, null);
        }
    }

    public void clearCurrentRequestInfo() {
        BZAppManager.getInstance().selectedPickUpLocation = new LatLng(0.0,0.0);
        BZAppManager.getInstance().selectedDropLocation = new LatLng(0.0,0.0);
        //driver uses currentRideRequestId to be cleared;
        BZAppManager.getInstance().currentRideRequestId = "";
        BZAppManager.getInstance().currentRideRequestUserPhone = "";
        BZAppManager.getInstance().currentRideRequestMessage = "";
        BZAppManager.getInstance().currentRideRequestUserName = "";
        bRequestAttended = false;
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
        else {//permission there
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

    public String getAddress(LatLng latlong) {
        String addressReturn = "";
        Geocoder geocoder = new Geocoder(HomeDriver.this, Locale.getDefault());
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

       /* MarkerOptions options = new MarkerOptions()
                .position(m_latLng)
                .title(locationAddress);
        m_map.addMarker(options);*///marker removed

        //m_map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        float zoomLevel = 16; //This goes up to 21
        m_map.moveCamera(CameraUpdateFactory.newLatLngZoom(m_latLng, zoomLevel));
        //update location in server

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);

        if (Utils.isEmpty(usertoken))
        {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_INVALID_USER_LOGIN, null);
        }
        //keep the token
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setPostExecuteListener(this);

            if (BZAppManager.getInstance().isDriver == true) {
                String urlCall = Utils.BASE_URL + Utils.UPDATE_DRIVER_LOCATION_URL;
                String params= "token="+ usertoken + "&Lat=" + currentLatitude +"&Long=" + currentLongitude ;
                api.putDetails(urlCall, Utils.UPDATE_DRIVER_LOCATION_URL,params);
            }

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();


        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    private void setUpMapIfNeeded() {
        if (m_map != null) {
            //todo marker not to be created
            m_map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToggleOffline:
                ToggleAction();
                break;
            case R.id.btnFinish:
                StartAction();
                break;
        }
    }

    private void UpdateAvailability (Boolean bStatus)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usertoken = sharedPreferences.getString(QuickstartPreferences.USER_TOKEN, null);

        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            BZRESTApiHandler api = new BZRESTApiHandler(this);
            api.setMessage("Changing status...");
            api.setPostExecuteListener(this);

            if (BZAppManager.getInstance().isDriver == true) {
                if (bStatus)
                {
                    String urlCall = Utils.BASE_URL + Utils.UPDATE_DRIVER_AVAILABILITY_URL ;
                    String params =  "token="+ usertoken + "&Flag=" + "TRUE";
                    api.putDetails(urlCall, Utils.UPDATE_DRIVER_AVAILABILITY_URL, params);

                }
                else
                {
                    String urlCall = Utils.BASE_URL + Utils.UPDATE_DRIVER_AVAILABILITY_URL;
                    String params = "token=" + usertoken + "&Flag=" + "FALSE";
                    api.putDetails(urlCall, Utils.UPDATE_DRIVER_AVAILABILITY_URL, params);
                }

            }

        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }
    private void ToggleAction() {

        onlineOption = true;
        if (NetworkListener.isConnectingToInternet(getApplicationContext())) {
            if (isOffline)// offline
            {
                //change online and call webs ervice
                UpdateAvailability(true);

            } else {

                //change to offline and call webs ervice
                UpdateAvailability(false);

            }
        }
        else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }



    private void UpdateLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            else {
                double currentLatitude = location.getLatitude();
                double currentLongitude = location.getLongitude();
                m_latLng = new LatLng(currentLatitude, currentLongitude);
            }
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
