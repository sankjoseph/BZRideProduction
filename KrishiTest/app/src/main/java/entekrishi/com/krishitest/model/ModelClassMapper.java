package entekrishi.com.krishitest.model;

import java.util.HashMap;
import entekrishi.com.krishitest.common.Utils;
import entekrishi.com.krishitest.model.loginRsp;
/**
 * Created by Santhosh.Joseph on 07-08-2015.
 */
public class ModelClassMapper {
    private static HashMap<String, Class> classHashMap;
    static {
        classHashMap = new HashMap<>();
        classHashMap.put(Utils.LOGIN_URL, loginRsp.class);
        classHashMap.put(Utils.NOTIFICATION_LIST_URL, NotificationRsp.class);
    }

    public static Class<ModelClassMapper> getModelClass(String url) {
        return classHashMap.get(url);
    }
}
