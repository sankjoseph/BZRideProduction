package bzride.com.bzride;

/**
 * Created by Santhosh.Joseph on 12-07-2016.
 */
public class BZCommonUserInfo {
    public String FirstName;
    public String MiddleName;
    public String LastName;
    public String Email;
    public String Password;
    public String ConfirmPassword;
    public String Address1;
    public String Address2;
    public String City;
    public String State;
    public String Zip;
    public String PhoneNumber;
    public String dob;
    public String SSN;
    public String DeviceId;
    public String DeviceType;
    public String currentlat;
    public String currentlong;
    public String availableStatus;
    public BZCardInfo cardData;
    public BZCommonUserInfo()
    {
        FirstName = "";
        MiddleName = "";
        LastName = "";
        Email = "";
        Password = "";
        ConfirmPassword = "";
        Address1 = "";
        Address2 = "";
        City = "";
        State = "";
        Zip = "";
        dob = "";
        SSN = "";
        DeviceId = "";
        DeviceType = "";
        currentlat = "";
        currentlong = "";
        availableStatus = "";
        cardData = new BZCardInfo();
    }

}
