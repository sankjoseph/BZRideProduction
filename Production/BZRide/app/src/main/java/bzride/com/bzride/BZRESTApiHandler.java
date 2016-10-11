package bzride.com.bzride;

/**
 * Created by Santhosh.joseph on 29-06-2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Santhosh.Joseph on 17-06-2016.
 */
public class BZRESTApiHandler {
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private BZJSONResp model;
    private OnPostExecuteListener mExecuteListener;

    private String mProgressDialogMessage="";
    private String mUrl;
    private String mKey;
    private String mParams;
    private String mResponse;

    public BZRESTApiHandler() {

    }
    public BZRESTApiHandler(Activity a) {
        mActivity = a;
    }

    public BZRESTApiHandler(Activity a, OnPostExecuteListener listener) {
        mActivity = a;
        mExecuteListener = listener;
    }

    public void setPostExecuteListener(OnPostExecuteListener listener) {
        mExecuteListener = listener;
    }


    public void putDetails(String url,String key, String Params) {
        mUrl = url;
        mKey = key;
        mParams = Params;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (mActivity==null)
                {
                    return;
                }
                if (mProgressDialogMessage != null) {
                    mProgressDialog = new ProgressDialog(mActivity);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage(mProgressDialogMessage.isEmpty() ? "Loading..." : mProgressDialogMessage);
                    mProgressDialog.show();
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                String reader = GENERICRESTApiHandler.postData(mUrl, mParams);
                if (reader != null) {
                    try {
                        model = new GsonBuilder().create().fromJson(reader, ModelClassMapper.getModelClass(mKey));
                        int a =0;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (mProgressDialog != null)
                    mProgressDialog.dismiss();

                if (mExecuteListener != null) {
                    if (model != null)
                        mExecuteListener.onSuccess(model);
                    else
                        mExecuteListener.onFailure();
                }
            }
        }.execute();
    }




    public void get(String url,String key) {
        mUrl = url;
        mKey = key;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (mActivity==null)
                {
                    return;
                }
                if (mProgressDialogMessage != null) {
                    mProgressDialog = new ProgressDialog(mActivity);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage(mProgressDialogMessage.isEmpty() ? "Loading..." : mProgressDialogMessage);
                    mProgressDialog.show();
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                String reader  = GENERICRESTApiHandler.getData(mUrl);
                if (reader != null) {
                    try {
                        model = new GsonBuilder().create().fromJson(reader,  ModelClassMapper.getModelClass(mKey));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (mProgressDialog != null)
                    mProgressDialog.dismiss();
                if (mExecuteListener != null) {
                    if (model != null)
                        mExecuteListener.onSuccess(model);
                    else
                        mExecuteListener.onFailure();
                }
            }
        }.execute();
    }

    public void setMessage(String message) {
        mProgressDialogMessage = message;
    }
}
