package bzride.com.bzride;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Santhosh.Joseph on 16-07-2016.
 */
public class Test {

   /* items =  getResources().getStringArray(R.array.bzmenuitems);
    dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerList = (ListView) findViewById(R.id.drawer_list);


    // getActionBar().setTitle(mTitle);


    adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
    mDrawerList.setAdapter(adapter);
    mDrawerList.setSelector(android.R.color.holo_blue_dark);


       mDrawerToggle = new ActionBarDrawerToggle(this, dLayout,
                R.mipmap.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            // Called when drawer is closed
            public void onDrawerClosed(View view) {
                //Put your code here
            }

            // Called when a drawer is opened
            public void onDrawerOpened(View drawerView) {
                //Put your code here
            }
        };


    // mDrawerLayout.setDrawerListener(mDrawerToggle);

    // Enabling Home button
    // getActionBar().setHomeButtonEnabled(true);
    // getActionBar().setDisplayHomeAsUpEnabled(true);

    mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

            dLayout.closeDrawers();
            Bundle args = new Bundle();
            args.putString("Menu", items[position]);
            BZMenudetailFragment detail = new BZMenudetailFragment();
            detail.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, detail).commit();

        }

    });

}*/
}
