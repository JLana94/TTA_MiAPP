package eus.ehu.tta.upv_ehutour.presentador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by josu on 9/01/18.
 */

public class Localizador {

    @SuppressLint("MissingPermission")
    public static Location getLocation(Context context) {

        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        Location netLoc = null;
        Location gpsLoc = null;
        Location location = null;

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gpsEnabled)
            gpsLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (networkEnabled)
            netLoc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (gpsLoc != null && netLoc != null) {
            if (gpsLoc.getAccuracy() > netLoc.getAccuracy())
                location = netLoc;
            else
                location = gpsLoc;
        } else {

            if (gpsLoc != null) {
                location = gpsLoc;
            } else if (netLoc != null) {
                location = netLoc;
            }
        }

        return location;
    }
}
