package entekrishi.com.EnteKrishi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entekrishi.com.EnteKrishi.Rest.OnPostExecuteListener;
import entekrishi.com.EnteKrishi.Rest.RestApi;
import entekrishi.com.EnteKrishi.common.InfiniteScrollListener;
import entekrishi.com.EnteKrishi.common.NetworkListener;
import entekrishi.com.EnteKrishi.common.Utils;
import entekrishi.com.EnteKrishi.model.Data;
import entekrishi.com.EnteKrishi.model.ModelClassMapper;
import entekrishi.com.EnteKrishi.model.NotificationRsp;
import entekrishi.com.EnteKrishi.model.Product;


public class HomeTab extends Activity implements View.OnClickListener,OnPostExecuteListener, AdapterView.OnItemClickListener {
    private WebView wv;
    private ListView prodList;
    private ProductListAdapter adapter;

    private TextView btn_search, btn_notifications, btn_all_products, badge;
    private ProgressBar mProgressBar;
    private boolean isNotifyTab, mPullRequired = true;
    private List<Product> mProducts;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        wv = (WebView) findViewById(R.id.webViewHome);
        wv.setWebViewClient(new MyBrowser(mProgressBar));
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(Utils.HOME_URL);

        prodList = (ListView) findViewById(R.id.plistView);
        prodList.setEmptyView(findViewById(android.R.id.empty));
        btn_search = (TextView) findViewById(R.id.btnSearch);
        btn_notifications = (TextView) findViewById(R.id.btnNotify);
        btn_all_products = (TextView) findViewById(R.id.btnAllproducts);
        badge = (TextView) findViewById(R.id.notification_count);
        btn_search.setOnClickListener(this);
        btn_notifications.setOnClickListener(this);
        btn_all_products.setOnClickListener(this);

        // default - search
        selectButton(R.id.btnSearch);
    }
    @Override
    public void onClick(View v) {
        if (NetworkListener.isNetworkConnected(getApplicationContext())) {
            selectButton(v.getId());
        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }

    private void selectButton(int id) {
        // drawable
        btn_search.setCompoundDrawablesWithIntrinsicBounds(0, id == R.id.btnSearch ? R.drawable.search_enabled : R.drawable.search, 0, 0);
        btn_notifications.setCompoundDrawablesWithIntrinsicBounds(0, id == R.id.btnNotify ? R.drawable.notification_enabled : R.drawable.notification, 0, 0);
        btn_all_products.setCompoundDrawablesWithIntrinsicBounds(0, id == R.id.btnAllproducts ? R.drawable.products_enabled : R.drawable.products, 0, 0);

        // background
        btn_search.setBackgroundColor(getResources().getColor(id == R.id.btnSearch ? android.R.color.white : R.color.LimeGreen));
        btn_notifications.setBackgroundColor(getResources().getColor(id == R.id.btnNotify ? android.R.color.white : R.color.LimeGreen));
        btn_all_products.setBackgroundColor(getResources().getColor(id == R.id.btnAllproducts ? android.R.color.white : R.color.LimeGreen));

        // text color
        btn_search.setTextColor(getResources().getColor(id == R.id.btnSearch ? android.R.color.black : android.R.color.white));
        btn_notifications.setTextColor(getResources().getColor(id == R.id.btnNotify ? android.R.color.black : android.R.color.white));
        btn_all_products.setTextColor(getResources().getColor(id == R.id.btnAllproducts ? android.R.color.black : android.R.color.white));

        // content
        isNotifyTab = false;
        prodList.setVisibility(id == R.id.btnNotify ? View.VISIBLE : View.GONE);
        wv.setVisibility(id != R.id.btnNotify ? View.VISIBLE : View.GONE);
        switch (id) {
            case R.id.btnSearch:
                mProgressBar.setVisibility(View.VISIBLE);
                wv.loadUrl(Utils.SEARCH_URL);
                break;
            case R.id.btnNotify:
                mProgressBar.setVisibility(View.GONE);
                isNotifyTab = true;
                prodList.setOnItemClickListener(this);
                prodList.setOnScrollListener(new InfiniteScrollListener() {
                    @Override
                    public void loadMore(int page, int totalItemsCount) {
                        pullNotfications(++mCurrentPage);
                    }
                });
                if (mCurrentPage == 0)
                    pullNotfications(++mCurrentPage);
                break;
            case R.id.btnAllproducts:
                mProgressBar.setVisibility(View.VISIBLE);
                wv.loadUrl(Utils.ALL_PRODUCTS__URL);
                break;
        }
    }

    private void pullNotfications(int page) {
        if (mPullRequired) {
            RestApi api = new RestApi(this);
            api.setMessage(isNotifyTab ? "Getting latest notifications..." : null);
            api.setPostExecuteListener(this);
            String urlCall = Utils.BASE_URL + Utils.NOTIFICATION_LIST_URL + "?token=" + Data.getInstance().getToken().toString() + "&page=" + page;
            api.get(urlCall, Utils.NOTIFICATION_LIST_URL);
        }
    }

    public void onSuccess(ModelClassMapper model) {
        NotificationRsp response = (NotificationRsp)model;
        if (response.msg.toString().equalsIgnoreCase(Utils.PULL_NOTIFY_SUCCESS)) {
            if (response.listofProducts != null && response.listofProducts.isEmpty()) {
                mPullRequired = false;
                return;
            }

            if (mProducts == null)
                mProducts = new ArrayList<>();
            mProducts.addAll(response.listofProducts);
            if (Integer.parseInt(response.unread)==0) {
                Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_NOTIFICATIONS, null);
            }

            if (isNotifyTab) {
                if (adapter != null) {
                    adapter.setItems(mProducts);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new ProductListAdapter(this, mProducts);
                    prodList.setAdapter(adapter);
                }
            }
            updateCount(response.unread);
        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, response.msg + Utils.MSG_LOGIN_AGAIN, null);
        }
    }

    private void updateCount(String count) {
        badge.setText(count);
        badge.setVisibility(Integer.parseInt(count) != 0 ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onFailure() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(".EnteKrishi", "Item clicked position = " + i);
        Product p = (Product) adapter.getItem(i);
        startActivity(new Intent(HomeTab.this, DetailedActivity.class).putExtra("url", p.url));
    }
}
