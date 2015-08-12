package entekrishi.com.EnteKrishi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import entekrishi.com.EnteKrishi.common.Utils;
import entekrishi.com.EnteKrishi.model.Product;


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
        txtAmount.setText(activity.getString(R.string.rupee_symbol) + " " + productitem.price);
        txtQuantity.setText("Avl.Qty   " +productitem.quantity);
        txtTime.setText(Utils.getFormatedTime(productitem.date_added, "MM/dd/yyyy hh:mm:ss aa"));
        return vi;
    }
}
