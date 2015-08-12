package entekrishi.com.EnteKrishi.Rest;
import entekrishi.com.EnteKrishi.model.ModelClassMapper;

/**
 * Created by Santhosh.Joseph on 07-08-2015.
 */
public interface OnPostExecuteListener {
    public void onSuccess(ModelClassMapper model);
    public void onFailure();
}
