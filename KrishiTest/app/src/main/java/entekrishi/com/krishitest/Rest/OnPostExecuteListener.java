package entekrishi.com.krishitest.Rest;
import entekrishi.com.krishitest.model.ModelClassMapper;

/**
 * Created by Santhosh.Joseph on 07-08-2015.
 */
public interface OnPostExecuteListener {
    public void onSuccess(ModelClassMapper model);
    public void onFailure();
}
