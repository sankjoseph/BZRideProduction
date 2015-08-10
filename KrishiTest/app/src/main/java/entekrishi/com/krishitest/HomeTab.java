package entekrishi.com.krishitest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;

import java.util.ArrayList;

import entekrishi.com.krishitest.Rest.OnPostExecuteListener;
import entekrishi.com.krishitest.Rest.RestApi;
import entekrishi.com.krishitest.common.Utils;
import entekrishi.com.krishitest.model.Data;
import entekrishi.com.krishitest.model.ModelClassMapper;
import entekrishi.com.krishitest.model.NotificationRsp;
import entekrishi.com.krishitest.model.Product;
import entekrishi.com.krishitest.model.loginRsp;


public class HomeTab extends ActionBarActivity implements View.OnClickListener,OnPostExecuteListener {
    WebView wv;
    ListView prodList;
    ProductListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);

        wv = (WebView) findViewById(R.id.webViewHome);
        wv.setWebViewClient(new MyBrowser());
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(Utils.HOME_URL);

        findViewById(R.id.btnSearch).setOnClickListener(this);
        findViewById(R.id.btnAllproducts).setOnClickListener(this);
        findViewById(R.id.btnNotify).setOnClickListener(this);
        /*TabHost hometabHost = (TabHost)findViewById(R.id.tabHost);

        hometabHost.setup();
        TabHost.TabSpec tabSpecSearch = hometabHost.newTabSpec("search");
        tabSpecSearch.setContent(R.id.search);
        tabSpecSearch.setContent(new Intent(this,Tab1Activity.class));
        tabSpecSearch.setIndicator("Search");
        hometabHost.addTab(tabSpecSearch);

        TabHost.TabSpec tabSpecNotify = hometabHost.newTabSpec("notify");
        tabSpecSearch.setContent(R.id.notify);
        tabSpecSearch.setIndicator("Notify");
        hometabHost.addTab(tabSpecNotify);

        TabHost.TabSpec tabSpecAllProducts = hometabHost.newTabSpec("allproducts");
        tabSpecSearch.setContent(R.id.allproducts);
        tabSpecSearch.setIndicator("All Products");
        hometabHost.addTab(tabSpecAllProducts);*/
    }
    @Override
    public void onClick(View v) {
        if (Data.getInstance().isNetworkConnected( getApplicationContext())) {
        switch (v.getId()) {
            case R.id.btnSearch:

                findViewById(R.id.plistView).setVisibility(View.GONE);
                findViewById(R.id.webViewHome).setVisibility(View.VISIBLE);
                //findViewById(R.id.btnSearch).setVisibility(View.GONE);
                //findViewById(R.id.btnSearch).setBackgroundColor(Color.RED);
                wv.loadUrl(Utils.SEARCH_URL);
                break;
            case R.id.btnNotify:
                findViewById(R.id.plistView).setVisibility(View.VISIBLE);
                findViewById(R.id.webViewHome).setVisibility(View.GONE);
                PullNotfications();
                break;
            case R.id.btnAllproducts:
                findViewById(R.id.plistView).setVisibility(View.GONE);
                findViewById(R.id.webViewHome).setVisibility(View.VISIBLE);
                wv.loadUrl(Utils.ALL_PRODUCTS__URL);
                break;
        }
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle(Utils.MSG_TITLE)
                    .setMessage(Utils.MSG_NO_INTERNET)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            timeUp();
                        }
                    })
                    .show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_tab, menu);
        return true;
    }
    private void PullNotfications() {
            RestApi api = new RestApi(this);
            api.setMessage("Getting latest notifications...");
            api.setPostExecuteListener(this);
            String urlCall = Utils.BASE_URL + Utils.NOTIFICATION_LIST_URL + "?token=" + Data.getInstance().getToken().toString() + "&page=1";
            api.get(urlCall, Utils.NOTIFICATION_LIST_URL);
    }

    public void onSuccess(ModelClassMapper model) {
        NotificationRsp response = (NotificationRsp)model;

        if (response.msg.toString().equalsIgnoreCase(Utils.PULL_NOTIFY_SUCCESS))
        {
            prodList =(ListView)findViewById(R.id.plistView);
            ArrayList<Product> productlist = response.listofProducts;
            adapter=new ProductListAdapter(this, productlist);
            prodList.setAdapter(adapter);

            // Click event for single list row
            prodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view,
                                        int position, long id) {
                   // String value = (String)adapter.getItemAtPosition(position);

                }
            });
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle(Utils.MSG_TITLE)
                    .setMessage(response.msg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            timeUp();
                        }
                    })
                    .show();
        }
    }


    @Override
    public void onFailure() {

    }
    private void timeUp() {

        //finish();
        // show result page
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
}
