package com.hunter.library.okhttp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class GlobalEventFactory implements EventListener.Factory {

    EventListener.Factory mGlobalFactory;
    EventListener.Factory mUserFactory;

    GlobalEventFactory(EventListener.Factory globalFactory, EventListener.Factory userFactory) {
        mGlobalFactory = globalFactory;
        mUserFactory = userFactory;
    }

    public EventListener create(Call call) {
        EventListener global = null;
        EventListener user = null;
        if (mGlobalFactory != null) {
            global = mGlobalFactory.create(call);
        }
        if (mUserFactory != null) {
            user = mUserFactory.create(call);
        }
        return new GlobalEventListener(global, user);
    }
}

class GlobalEventListener extends EventListener {

    EventListener mGlobalEventListener;
    EventListener mUserEventListener;

    GlobalEventListener(EventListener global, EventListener user) {
        this.mGlobalEventListener = global;
        this.mUserEventListener = user;
    }

    public void callStart(Call call) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.callStart(call);
        }
        if (mUserEventListener != null) {
            mUserEventListener.callStart(call);
        }
    }

    public void dnsStart(Call call, String domainName) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.dnsStart(call, domainName);
        }
        if (mUserEventListener != null) {
            mUserEventListener.dnsStart(call, domainName);
        }
    }

    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.dnsEnd(call, domainName, inetAddressList);
        }
        if (mUserEventListener != null) {
            mUserEventListener.dnsEnd(call, domainName, inetAddressList);
        }
    }

    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.connectStart(call, inetSocketAddress, proxy);
        }
        if (mUserEventListener != null) {
            mUserEventListener.connectStart(call, inetSocketAddress, proxy);
        }
    }

    public void secureConnectStart(Call call) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.secureConnectStart(call);
        }
        if (mUserEventListener != null) {
            mUserEventListener.secureConnectStart(call);
        }
    }

    public void secureConnectEnd(Call call, Handshake handshake) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.secureConnectEnd(call, handshake);
        }
        if (mUserEventListener != null) {
            mUserEventListener.secureConnectEnd(call, handshake);
        }
    }

    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
                           Protocol protocol) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.connectEnd(call, inetSocketAddress, proxy, protocol);
        }
        if (mUserEventListener != null) {
            mUserEventListener.connectEnd(call, inetSocketAddress, proxy, protocol);
        }
    }

    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
                              Protocol protocol, IOException ioe) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        }
        if (mUserEventListener != null) {
            mUserEventListener.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        }
    }

    public void connectionAcquired(Call call, Connection connection) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.connectionAcquired(call, connection);
        }
        if (mUserEventListener != null) {
            mUserEventListener.connectionAcquired(call, connection);
        }
    }

    public void connectionReleased(Call call, Connection connection) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.connectionReleased(call, connection);
        }
        if (mUserEventListener != null) {
            mUserEventListener.connectionReleased(call, connection);
        }
    }

    public void requestHeadersStart(Call call) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.requestHeadersStart(call);
        }
        if (mUserEventListener != null) {
            mUserEventListener.requestHeadersStart(call);
        }
    }

    public void requestHeadersEnd(Call call, Request request) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.requestHeadersEnd(call, request);
        }
        if (mUserEventListener != null) {
            mUserEventListener.requestHeadersEnd(call, request);
        }
    }

    public void requestBodyStart(Call call) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.requestBodyStart(call);
        }
        if (mUserEventListener != null) {
            mUserEventListener.requestBodyStart(call);
        }
    }

    public void requestBodyEnd(Call call, long byteCount) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.requestBodyEnd(call, byteCount);
        }
        if (mUserEventListener != null) {
            mUserEventListener.requestBodyEnd(call, byteCount);
        }
    }

    public void responseHeadersStart(Call call) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.responseHeadersStart(call);
        }
        if (mUserEventListener != null) {
            mUserEventListener.responseHeadersStart(call);
        }
    }

    public void responseHeadersEnd(Call call, Response response) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.responseHeadersEnd(call, response);
        }
        if (mUserEventListener != null) {
            mUserEventListener.responseHeadersEnd(call, response);
        }
    }

    public void responseBodyStart(Call call) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.responseBodyStart(call);
        }
        if (mUserEventListener != null) {
            mUserEventListener.responseBodyStart(call);
        }
    }

    public void responseBodyEnd(Call call, long byteCount) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.responseBodyEnd(call, byteCount);
        }
        if (mUserEventListener != null) {
            mUserEventListener.responseBodyEnd(call, byteCount);
        }
    }

    public void callEnd(Call call) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.callEnd(call);
        }
        if (mUserEventListener != null) {
            mUserEventListener.callEnd(call);
        }
    }

    public void callFailed(Call call, IOException ioe) {
        if (mGlobalEventListener != null) {
            mGlobalEventListener.callFailed(call, ioe);
        }
        if (mUserEventListener != null) {
            mUserEventListener.callFailed(call, ioe);
        }
    }
}
