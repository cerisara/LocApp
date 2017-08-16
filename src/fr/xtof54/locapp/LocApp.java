package fr.xtof54.locapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.SignalStrength;
import android.telephony.PhoneStateListener;
import android.telephony.CellSignalStrengthGsm;
import android.widget.Button;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class LocApp extends Activity {
    
    public static LocApp main;

    private ArrayList<Float> vals = new ArrayList<Float>();
    MyPhoneStateListener mPhoneStatelistener;
    int mSignalStrength = 0;

/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        main=this;
        setContentView(R.layout.main);

		{
			final Button button = (Button)findViewById(R.id.but1);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View vi) {
                    int v=getSignalStrength();
                    vals.add((float)v);
                    plot();
				}
			});
		}

        mPhoneStatelistener = new MyPhoneStateListener();
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }


    class MyPhoneStateListener extends PhoneStateListener {
         @Override
         public void onSignalStrengthsChanged(SignalStrength signalStrength) {
             super.onSignalStrengthsChanged(signalStrength);
             mSignalStrength = signalStrength.getGsmSignalStrength();
             mSignalStrength = (2 * mSignalStrength) - 113; // -> dBm           
         }
     }

    public int getSignalStrength() {
        return mSignalStrength;

        // WifiManager wifiManager = (WifiManager)main.getSystemService(Context.WIFI_SERVICE);
        // int linkSpeed = wifiManager.getConnectionInfo().getRssi();

        /*
        TelephonyManager telephonyManager = (TelephonyManager)main.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager==null) return -2;
        try {
            List<CellInfo> cellinfo = telephonyManager.getAllCellInfo();
            if (cellinfo==null) {
                // old device
                CellLocation cl = telephonyManager.getCellLocation();
                GsmCellLocation ll = (GsmCellLocation)cl;
                
            }
            CellInfoGsm cellinfogsm = (CellInfoGsm)cellinfo.get(0);
            CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
            int l = cellSignalStrengthGsm.getDbm();
            return l;
        } catch (Exception e) {
            System.out.println("ERROR: no telephonymanager !");
            return -1;
        }
        */
    }

    public void plot() {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] vs = new DataPoint[vals.size()];
        for (int i=0;i<vs.length;i++) vs[i]=new DataPoint(i,vals.get(i));
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(vs);
        graph.addSeries(series);
    }
}

