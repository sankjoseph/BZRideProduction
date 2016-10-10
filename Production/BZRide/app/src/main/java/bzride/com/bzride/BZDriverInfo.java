package bzride.com.bzride;

/**
 * Created by Santhosh.Joseph on 12-07-2016.
 */
public class BZDriverInfo extends BZCommonUserInfo {
    public BZBankInfo driverBankInfo;
    public BZInsuranceInfo driverInsuranceInfo;
    public BZLicenseInfo driverLicenseInfo;
    public BZVehicleRegInfo driverVehRegInfo;
    public BZVehicleInfo driverVehicleInfo;

    public BZDriverInfo()
    {
        driverBankInfo = new BZBankInfo();
        driverInsuranceInfo = new BZInsuranceInfo();
        driverLicenseInfo = new BZLicenseInfo();
        driverVehRegInfo = new BZVehicleRegInfo();
        driverVehicleInfo = new BZVehicleInfo();
    }
}
