package com.quinn.hunter.okhttp;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

/**
 * Created by quinn on 13/09/2018
 */
public class CustomGlobalDns implements Dns {

    @Override public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        if (hostname == null) throw new UnknownHostException("hostname == null");
        Log.i("CustomGlobalDns", "lookup " + hostname);
        try {
            return Arrays.asList(InetAddress.getAllByName(hostname));
        } catch (NullPointerException e) {
            UnknownHostException unknownHostException =
                    new UnknownHostException("Broken system behaviour for dns lookup of " + hostname);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

}


