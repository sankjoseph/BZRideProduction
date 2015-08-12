package entekrishi.com.EnteKrishi.model;

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
}
