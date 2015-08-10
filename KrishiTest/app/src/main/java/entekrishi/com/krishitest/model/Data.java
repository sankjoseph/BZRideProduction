package entekrishi.com.krishitest.model;

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
public class Data {
    private static Data mInstance;
    private  String mToken;
    private Data() {}
    public static Data getInstance() {
        if (mInstance == null)
            mInstance = new Data();
        return mInstance;
    }
    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }
    public boolean isNetworkConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
