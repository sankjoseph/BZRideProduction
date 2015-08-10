package entekrishi.com.krishitest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santhosh.Joseph on 08-08-2015.
 */
public class ProductListing {
    private ArrayList<Product> productstList = new ArrayList<Product>();

    public List<Product> getProductList() {
        return productstList;
    }

    public void setProductList(List<Product> productList) {
        this.productstList = (ArrayList<Product>)productList;
    }
}
