package bzride.com.bzride;

/**
 * Created by Santhosh.joseph on 29-06-2016.
 */
public interface OnPostExecuteListener {
    public void onSuccess(BZJSONResp model);
    public void onFailure();
}
