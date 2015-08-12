package entekrishi.com.EnteKrishi.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Santhosh.Joseph on 07-08-2015.
 */
public class Utils {
    public static final String BASE_URL= "http://www.entekrishi.com/mobile";
    public static final String LOGIN_URL = "/login/";
    public static final String CHECK_SESSION_URL = "/check/";
    public static final String NOTIFICATION_LIST_URL = "/notification/";
    /// for the constatnat check
    public static final String LOGIN_SUCCESS = "Login Success";
    public static final String PULL_NOTIFY_SUCCESS = "Success";
    public static final String TOKEN_SUCCESS = "Token ";//Token not exist

    //message titles
    public static final String MSG_TITLE = "EnteKrishi";
    public static final String MSG_NO_INTERNET = "You are not connected to Internet. Please try later.";
    ///WebView

    public static final String HOME_URL = "http://www.entekrishi.com/";
    public static final String SEARCH_URL = "http://www.entekrishi.com/advanced/";
    public static final String ALL_PRODUCTS__URL = "http://www.entekrishi.com/listing/";

    // for list view

    public static final String KEY_TITLE = "title";
    public static final String KEY_PROVIDER = "provider";
    public static final String KEY_DESCRIPTION = "des";


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

}
