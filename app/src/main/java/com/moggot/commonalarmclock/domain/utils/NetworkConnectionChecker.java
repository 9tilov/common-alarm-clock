package com.moggot.commonalarmclock.domain.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.moggot.commonalarmclock.R;

public class NetworkConnectionChecker {

    private Context context;

    public NetworkConnectionChecker(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean available = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
        if (!available)
            internetUnavailable();
        return available;
    }

    private void internetUnavailable() {
        Toast.makeText(context,
                R.string.no_internet_connection,
                Toast.LENGTH_SHORT).show();
    }
}
