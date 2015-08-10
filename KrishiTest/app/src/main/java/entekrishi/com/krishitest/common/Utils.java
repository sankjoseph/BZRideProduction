package entekrishi.com.krishitest.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

}
