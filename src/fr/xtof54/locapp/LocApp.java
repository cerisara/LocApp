package fr.xtof54.locapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;

public class LocApp extends Activity {
    
    public static LocApp main;

/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        main=this;
        setContentView(R.layout.main);
    }


    public void getSignalStrength() {
        WifiManager wifiManager = (WifiManager)main.getSystemService(Context.WIFI_SERVICE);
        int linkSpeed = wifiManager.getConnectionInfo().getRssi();

        TelephonyManager telephonyManager = (TelephonyManager)main.getSystemService(Context.TELEPHONY_SERVICE);
        CellInfoGsm cellinfogsm = (CellInfoGsm)telephonyManager.getAllCellInfo().get(0);
        CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
        cellSignalStrengthGsm.getDbm();
    }
}

