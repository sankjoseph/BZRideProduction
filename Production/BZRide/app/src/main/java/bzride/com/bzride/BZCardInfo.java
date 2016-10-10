package bzride.com.bzride;

/**
 * Created by Santhosh.Joseph on 12-07-2016.
 */
public class BZCardInfo {
    public String cardType;//D/C
    public String cardVendor;//M/V
    public String cardNumber;
    public String cardBillingAddress1;
    public String cardBillingAddress2;
    public String cardBillingCity;
    public String cardBillingState;
    public String cardBillingZip;
    public String cardExpiryMonth;
    public String cardExpiryYear;
    public String cardCVV;
    public String cardToken;
    public BZCardInfo()
    {
        cardType = "";
        cardVendor = "";
        cardNumber = "";
        cardBillingAddress1 = "";
        cardBillingAddress2 = "";
        cardBillingCity = "";
        cardBillingState = "";
        cardBillingZip = "";
        cardExpiryMonth = "";
        cardExpiryYear = "";
        cardCVV = "";
        cardToken = "";
    }
}
