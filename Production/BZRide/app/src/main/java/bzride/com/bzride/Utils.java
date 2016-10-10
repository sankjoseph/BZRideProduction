package bzride.com.bzride;

/**
 * Created by Santhosh.joseph on 29-06-2016.
 */
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Santhosh.Joseph on 17-06-2016.
 */
public class Utils {
//http://www.lemonlabs.in/accidentapp/service/register.php?name=santhosh&mobile=9446431692&email=sankjoseph@gmail.com&device_id=xeq212121212121
    /*public static final String BASE_URL= "http://www.lemonlabs.in/accidentapp/service";
    public static final String LOGIN_URL = "/login/";
    public static final String REGISTER_URL = "/register.php";*/

    //public static final String BASE_URL= "http://www.intimationsoftware.com/ws/bzridedummy";

    //public static final String BASE_URL= "http://intimationsoftware.com/bzride/web/bzride";

    public static final String API_NOT_CONNECTED = "Google API not connected";
    public static final String SOMETHING_WENT_WRONG = "OOPs!!! Something went wrong...";
    public static String PlacesTag = "Google Places Auto Complete";

    public static final String BASE_URL= "http://bzride.com/bzrideProduction";

    public static final String RIDE_REQUEST_I_URL = "/CreateRideRequestImmediate.php";

    public static final String LOGIN_DRIVER_URL = "/LoginDriver.php";
    public static final String LOGIN_RIDER_URL = "/LoginRider.php";
    public static final String REGISTER_RIDER_URL = "/RegisterRider.php";
    public static final String REGISTER_DRIVER_URL = "/RegisterDriver.php";
    public static final String GET_BANK_INFO_URL = "/GetBankInfo.php";
    public static final String UPDATE_BANK_INITIAL_INFO_URL = "/UpdateInitialBankInfo.php";
    public static final String UPDATE_RIDER_CARD_DETAILS_URL = "/UpdateRiderCardDetails.php";
    public static final String UPDATE_DRIVER_CARD_DETAILS_URL = "/UpdateDriverCardDetails.php";

   // public static final String ACCEPT_EULA_DRIVER_URL = "/AcceptEULADriver.php";
//
    public static final String UPDATE_DEVICE_TOKEN_URL = "/UpdateDeviceToken.php";
    public static final String UPDATE_DRIVER_AVAILABILITY_URL = "/UpdateDriverAvailability.php";
    public static final String UPDATE_DRIVER_LOCATION_URL = "/UpdateDriverLocation.php";
    public static final String READ_RIDE_REQUEST_URL = "/ReadRideRequest.php";
    public static final String ACCEPT_RIDE_REQUEST_URL = "/AcceptRideRequest.php";
    public static final String START_RIDE_URL = "/StartRide.php";
    public static final String END_RIDE_URL = "/EndRide.php";
    public static final String UPDATE_DRIVER_PROFILE_URL = "/UpdateDriverProfile.php";
    public static final String GET_DRIVER_PROFILE_URL = "/GetDriverProfile.php";

    public static final String UPDATE_RIDER_PROFILE_URL = "/UpdateRiderProfile.php";
    public static final String GET_RIDER_PROFILE_URL = "/GetRiderProfile.php";

    public static final String ARRIVE_RIDE_URL = "/ArriveRide.php";
    public static final String CANCEL_RIDE_URL = "/CancelRide.php";

    public static final String REGISTER_SUCCESS = "Login Success";

    public static final String STATUS_SUCCESS = "S";
    public static final String STATUS_FAILED = "F";

    public static final String MSG_TITLE = "BZRide";
    public static final String MSG_NO_BZRIDE = "Currently BZRide is not available in your country. Please try later.";
    public static final String MSG_NO_INTERNET = "You are not connected to Internet. Please try later.";
    public static final String MSG_ACC_EULA= "Please accept the license agreement to continue registration.";
    public static final String MSG_PWD_MISMATCH = "Password mismatch.";
    public static final String MSG_EMAIL_INVALID = "Please enter a valid email id.";
    public static final String MSG_NAME_EMPTY = "Please enter your name.";
    public static final String MSG_PHONE_EMPTY = "Please enter your phone number.";
    public static final String MSG_DOB_EMPTY = "Please enter your date of birth.";
    public static final String MSG_VEHICLE_EMPTY = "Please enter complete vehicle details";
    public static final String MSG_REG_EMPTY = "Please enter complete registration details";
    public static final String MSG_LIC_EMPTY = "Please enter complete license details";
    public static final String MSG_INS_EMPTY = "Please enter complete insurance details";
    public static final String MSG_CARD_EMPTY = "Please enter your card details";
    // for card
    public static final String MSG_CARD_NUMBER_EMPTY = "Please enter card number.";
    public static final String MSG_CARD_EXP_MONTH = "Please enter card expiry month.";
    public static final String MSG_CARD_EXP_YEAR = "Please enter card expiry year.";
    public static final String MSG_CARD_CVV = "Please enter card CVV.";


    public static final String MSG_INVALID_DOB = "Please enter valid date of birth in mm/dd/yyyy format.";


    public static final String MSG_SSN_EMPTY = "Please enter your SSN.";
    public static final String MSG_ERROR_SERVER = "Some error occured while connecting to server.";
    public static final String MSG_ERROR_NOT_READY = "Not implemented now.Please wait for future release.";
    public static final String MSG_INVALID_USER_LOGIN= "Invalid user login. Please login again.";
    public static final String MSG_INVALID_CARD_NUMBER= "Invalid card number. Please try again.";
    public static final String MSG_INVALID_CARD_CVV= "Invalid cvv code . Please try again.";
    public static final String MSG_INVALID_CARD_EXPDATE= "Invalid expiry date for card. Please try again.";
    public static final String MSG_ERROR_NO_ACTIVE_RIDE = "No active rides at this time.";
    public static final String MSG_ERROR_SERVER_DATA_HANDLE = "Error handling server response.";
    public static final String STRIPE_SANDBOX_PUBLIC_KEY = "pk_test_LL1jchCoTe2qzVPx5GfwGY4o";
    public static final String STRIPE_LIVE_PUBLIC_KEY = "pk_live_YgyoxGbTgtqBh20JXGxLP4rU";
    public static final String STRIPE_RUNNING_KEY = STRIPE_LIVE_PUBLIC_KEY;


    public static final String MSG_REQUEST_CONFIRM= "Are you sure to make the ride request?. You will not be able to cancel the request from the App";



    public static void showInfoDialog(Context c, String title, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .show();
    }

    public static String getFormatedTime(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat(format);
        return newFormat.format(convertedDate);
    }
    public static boolean isEmptyLocation(LatLng inputLatLng)
    {
        if (inputLatLng == null)
            return  true;
        else
        {
            LatLng defzeroLocation = new LatLng(0.0,0.0);
            if(inputLatLng.equals(defzeroLocation))
            {
                return true;
            }
        }

        return false;
    }
    public static boolean isEmpty(String inputString)
    {
        if (TextUtils.isEmpty(inputString)){
            return true;
        }
        return false;
    }
    public static boolean isValidEmail(String inputEmailString)
    {
        if (!TextUtils.isEmpty(inputEmailString))
        {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (inputEmailString.matches(emailPattern))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isEqualAndNotEmpty(String inputString,String strCompare)
    {
        if (!TextUtils.isEmpty(inputString))
        {
            if (inputString.equals(strCompare))
            {
                return true;
            }
        }
        return false;
    }
    public static final String md5encrypt(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
