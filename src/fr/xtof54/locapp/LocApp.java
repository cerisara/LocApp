package fr.xtof54.locapp;

public class LocApp {
    public void getSignalStrength() {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        int linkSpeed = wifiManager.getConnectionInfo().getRssi();

        TelephonyManager telephonyManager =        (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        CellInfoGsm cellinfogsm = (CellInfoGsm)telephonyManager.getAllCellInfo().get(0);
        CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
        cellSignalStrengthGsm.getDbm();
    }
}

