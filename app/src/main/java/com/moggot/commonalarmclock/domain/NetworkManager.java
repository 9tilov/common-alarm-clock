package com.moggot.commonalarmclock.domain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.moggot.commonalarmclock.R;

public class NetworkManager {

    private Context context;

    public NetworkManager(Context context) {
        this.context = context;
    }

    public boolean isInternetEnable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean available = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
        if (!available)
            internetUnavailable();
        return available;
    }

    private void internetUnavailable() {
        Toast.makeText(context, R.string.no_internet_connection,
                Toast.LENGTH_SHORT).show();
    }
}
