package com.example.sergio.sistz;

/**
 * Created by Sergio on 4/14/2016.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.sergio.sistz.data.SISConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionDetector {

    private Context _context;

    private static boolean internetAvailability = false;
    private static String lastVersion = "";
    private static long lastServerConnectionTime = 0;

    /**
     * This variable indicates how long have to wait(in milliseconds)
     * to check again the app version in the server.
     */
    private static final int CACHE_VALID_TIME = 120000;

    /**
     * Time out for connect with the server
     * this time should be considerably smaller than {@code CACHED_VALID_TIME}
     */
    private static final int TIME_OUT = 1000;

    public ConnectionDetector(Context context){
        this._context = context;
    }

    /**
     * Check if is Connecting to WiFi or a Mobile Network
     * NOTE: if this method returns true not necessarily has internet connection.
     * @return
     */
    public boolean isConnectingToInternet(){
        boolean connected = false;
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if ( (info[i].getType() == ConnectivityManager.TYPE_WIFI ||  info[i].getType() == ConnectivityManager.TYPE_MOBILE)
                            && info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        connected = true;
                    }

        }
        return connected;
    }

    /**
     * Check if the device is connected to a WiFi network
     * @param context
     * @return true if connected
     * @author Diego Calderon 31/10/2018
     */
    public static boolean isConnectedToWiFi(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getType() == ConnectivityManager.TYPE_WIFI) {
                        return info[i].getState() == NetworkInfo.State.CONNECTED;
                    }

        }
        return false;
    }

    /**
     * This method check for a SIM that is present and ready to work
     * @param context
     * @return true if the sim is available
     * @author Diego Calderon 31/10/2018
     */
    public static boolean isSimPresent(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telMgr.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * Open a new HttpConnection to the server
     * @return an InputStream to the server
     * @throws NoConnectionException
     */
    private static InputStream openHttpConnection() throws NoConnectionException {
        InputStream in = null;
        int response = -1;

        try {
            URL url = new URL(SISConstants.PING_URL);
            URLConnection conn = url.openConnection();

            if (!(conn instanceof HttpURLConnection)) {
                throw new NoConnectionException("Not an HTTP connection");
            }
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(TIME_OUT);
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            } else {
                throw new NoConnectionException("Response Code HTTP " + response);
            }
        } catch (Exception ex) {
            throw new NoConnectionException("Error connecting " + ex.getMessage());
        }
        return in;
    }

    /**
     * Returns the last version number from the server using the cache
     * @see #getLastVersionNumber(boolean)
     * @return String with version
     * @throws NoConnectionException
     */
    public static String getLastVersionNumber() throws NoConnectionException {
        return ConnectionDetector.getLastVersionNumber(false);
    }

    /**
     * Return the last version number from the server if the ignoreCache is false this method will
     * return the value from the last connection, but if the ignoreCache is true this method will
     * connect to the server and check for the last version number.
     * @param ignoreCache if true will force a connection to the server
     * @return last version number
     * @throws NoConnectionException
     */
    public static String getLastVersionNumber(boolean ignoreCache) throws NoConnectionException {
        final NoConnectionException[] exception = new NoConnectionException[1];
        if(ignoreCache || System.currentTimeMillis() - lastServerConnectionTime > CACHE_VALID_TIME) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    lastServerConnectionTime = System.currentTimeMillis();
                    int BUFFER_SIZE = 2000;
                    InputStream in = null;
                    try {
                        in = openHttpConnection();
                        String str = "";
                        if (in != null) {
                            InputStreamReader isr = new InputStreamReader(in);
                            int charRead;
                            char[] inputBuffer = new char[BUFFER_SIZE];
                            while ((charRead = isr.read(inputBuffer)) > 0) {
                                // ---convert the chars to a String---
                                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                                str += readString;
                                inputBuffer = new char[BUFFER_SIZE];
                            }
                            in.close();
                        }
                        if (str.length() > 4){
                            throw new NoConnectionException("Error in response of the server: " + str);
                        }
                        internetAvailability = true;
                        lastVersion = str;
                    } catch (IOException e) {
                        Log.d("ConnectionDetector", "Error happen while connecting to the server");
                        exception[0] = new NoConnectionException("Error happen while connecting to the server");
                    } catch (NoConnectionException e) {
                        Log.d("ConnectionDetector", e.getMessage());
                        internetAvailability = false;
                        exception[0] = e;
                    }
                }
            };
            t.start();
            if(ignoreCache) {
                try {
                    t.join();
                    if(exception[0] != null)
                        throw exception[0];
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return lastVersion;
        } else {
            return lastVersion;
        }

    }

    /**
     * @return true if the internet Availability has been already checked
     */
    public static boolean isInternetAvailabilityChecked() {
        return  lastServerConnectionTime != 0;
    }

    /**
     * Check if is internet connection available
     * @see #isInternetAvailable(boolean)
     * @return true if is connected to internet
     */
    public static boolean isInternetAvailable() {
        return ConnectionDetector.isInternetAvailable(false);
    }

    /**
     * Check if is internet connection available
     * @param ignoreCache
     * @return true if is connected to internet
     */
    public static boolean isInternetAvailable(boolean ignoreCache) {
        try {
            if(ignoreCache || System.currentTimeMillis() - lastServerConnectionTime > CACHE_VALID_TIME) {
                getLastVersionNumber();
            }
        } catch(NoConnectionException e) {
            e.printStackTrace();
        }
        return internetAvailability;
    }


}
class NoConnectionException extends Exception {
    public NoConnectionException(String str) {
        super(str);
    }
}