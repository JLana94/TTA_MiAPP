package eus.ehu.tta.upv_ehutour.presentador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by josu on 9/01/18.
 */

public class Localizador {

    private final int MIN_TIME_BW_UPDATES=1000;
    private final int MIN_DISTANCE_CHANGE_FOR_UPDATES=100;


    public Localizador()
    {

    }

    @SuppressLint("MissingPermission")
    public Location getLocation(Context context) {

        boolean gps_enabled = false;
        boolean network_enabled = false;

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location net_loc = null, gps_loc = null, location = null;

        if (gps_enabled)
            gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (network_enabled)
            net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (gps_loc != null && net_loc != null) {

            //smaller the number more accurate result will
            if (gps_loc.getAccuracy() > net_loc.getAccuracy())
                location = net_loc;
            else
                location = gps_loc;

            // I used this just to get an idea (if both avail, its upto you which you want to take as I've taken location with more accuracy)

        } else {

            if (gps_loc != null) {
                location = gps_loc;
            } else if (net_loc != null) {
                location = net_loc;
            }
        }

        return location;
    }
}
