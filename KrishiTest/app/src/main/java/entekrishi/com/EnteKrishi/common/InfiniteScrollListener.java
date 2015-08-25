package entekrishi.com.EnteKrishi.common;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by gorillalogic on 3/30/15.
 */
public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener, ViewPager.OnPageChangeListener {
    public static int PAGE_SIZE = 10;
    private int bufferItemCount = 1; // No. of non-visible items at the end of the list, when next loading should occur.
    private int currentPage = 0;
    private int itemCount = 0;
    private boolean isLoading = true;
    private int pageEndItemIndex;

    public InfiniteScrollListener() {}

    public void resetItemCount() {
        itemCount = 0;
    }

    public InfiniteScrollListener(int bufferItemCount) {
        this.bufferItemCount = bufferItemCount;
    }

    public abstract void loadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (visibleItemCount == 0)
            return;

        if (totalItemCount < itemCount) {
            this.itemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true;
            }
        }

        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
            currentPage++;
        }

        if (!isLoading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)) {
            loadMore(currentPage + 1, totalItemCount);
            isLoading = true;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position > pageEndItemIndex
                && position  == PAGE_SIZE - 1) {
            loadMore(++currentPage, PAGE_SIZE);
            pageEndItemIndex = position;
            isLoading = true;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
