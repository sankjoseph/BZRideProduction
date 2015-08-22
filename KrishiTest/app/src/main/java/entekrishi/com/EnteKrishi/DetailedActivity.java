package entekrishi.com.EnteKrishi;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by gorillalogic on 8/13/15.
 */
public class DetailedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        final WebView wv = (WebView) findViewById(R.id.web_view);
        wv.setWebViewClient(new MyBrowser((ProgressBar) findViewById(R.id.progressBar)));
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(getIntent().getStringExtra("url"));
        wv.post(new Runnable() {
            @Override
            public void run() {
                wv.scrollTo(0
                        , (int) (wv.getBottom() - getResources().getDimension(R.dimen.activity_vertical_margin)));
            }
        });
    }
}
