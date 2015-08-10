package entekrishi.com.krishitest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import entekrishi.com.krishitest.common.Utils;
import entekrishi.com.krishitest.model.Product;


/**
 * Created by Santhosh.Joseph on 09-08-2015.
 */
public   class ProductListAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Product> data;
    private static LayoutInflater inflater=null;
    public ProductListAdapter(Activity a, ArrayList<Product>  d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.listrow, null);

        TextView txtProduct = (TextView)vi.findViewById(R.id.title); // title
        TextView txtProvider = (TextView)vi.findViewById(R.id.provider); // farmer
        TextView txtDescription = (TextView)vi.findViewById(R.id.des); // description of item
        TextView txtAmount = (TextView)vi.findViewById(R.id.amount); // amount
        TextView txtQuantity = (TextView)vi.findViewById(R.id.avlqty); // qty
        TextView txtTime = (TextView)vi.findViewById(R.id.timerec); // time

        Product productitem = data.get(position);

        // Setting all values in listview
        txtProduct.setText(productitem.title);
        txtProvider.setText(productitem.contact_name + " " + productitem.district_name);
        txtDescription.setText(productitem.description);
        txtAmount.setText("RS " + productitem.price);
        txtQuantity.setText("Avl.Qty " +productitem.quantity);
        txtTime.setText(productitem.date_added);
        //String dateString = "03/26/2012 11:49:00 AM";
        //new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        Date convertedDate;
        try {
            convertedDate = dateFormat.parse(productitem.date_added);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/
        //2015-08-06 11:39:08
        return vi;
    }
}
