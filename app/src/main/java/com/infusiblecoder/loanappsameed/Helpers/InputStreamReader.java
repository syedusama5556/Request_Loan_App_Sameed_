package com.infusiblecoder.loanappsameed.Helpers;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

public class InputStreamReader extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    public Map<String, String> responseHeaders;
    private Map<String, String> mParams;

    public InputStreamReader(int method, String mUrl, Response.Listener<byte[]> listener,
                             Response.ErrorListener errorListener, HashMap<String, String> params) {

        super(method, mUrl, errorListener);
        // Every time it should make new request, should not use cache
        setShouldCache(false);
        mListener = listener;
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;
        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}