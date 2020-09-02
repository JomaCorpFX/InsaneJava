package com.insaneio.insane.net;


import com.insaneio.insane.task.CancellationTaskToken;
import com.insaneio.insane.task.TaskCancelledException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joma on 04/07/2016.
 */
public class HttpRequestManager {

    public static final String INTERNAL_ERROR = X.get("oiju8oXSWjz7na_UVU_xMKrt0oz9mZKe8d7FhGer1ac,", true)/* "InternalError" */;
    public static final String TIMEOUT_ERROR = X.get("7RMW-zWRD9BpNAoEfCHGsnhh2TUEw0g3hmGrB85xxcM,", true)/* "TimeoutError" */;
    public static final int CONNECTION_TIMEOUT = 60000;
    public static final int READING_TIMEOUT = 60000;

    private static HttpResponse doRequest(URL uri, HttpRequestMethod method, String data, HashMap<String,String> headers, CancellationTaskToken token) throws Exception
    {
        return doRequest(uri, method, data, HttpRequestManager. CONNECTION_TIMEOUT, HttpRequestManager.READING_TIMEOUT, headers, token);
    }

    private static HttpResponse doRequest(URL uri, HttpRequestMethod method, String data, CancellationTaskToken token) throws Exception
    {
        return doRequest(uri, method, data, HttpRequestManager. CONNECTION_TIMEOUT, HttpRequestManager.READING_TIMEOUT, null, token);
    }

    private static HttpResponse doRequest(URL uri, HttpRequestMethod method, String data, int connectionTimeout, int readingTimeout, HashMap<String,String> headers, CancellationTaskToken token)
    {
        HttpURLConnection connection = null;
        HttpResponse ret = null;
        try {
            token.throwIfCanceled();
            connection = (HttpURLConnection) uri.openConnection();
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readingTimeout);

            if (method == HttpRequestMethod.POST) {
                connection.setDoOutput(true);
            }

            if (headers != null) {
                for (Map.Entry<String, String> value : headers.entrySet()) {
                    connection.setRequestProperty(value.getKey(), value.getValue());
                }
            }
            token.throwIfCanceled();

            if (data != null && method == HttpRequestMethod.POST) {
                connection.getOutputStream().write(data.getBytes(X.get("PcqM9ZZC-oNBDyQF7Y6fOrvCjR6vGiylCC4P0gdeRLU,", true)/* "UTF-8" */));
            }
            token.throwIfCanceled();
            connection.connect();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[2048];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    token.throwIfCanceled();
                    result.write(buffer, 0, length);
                }
                connection.disconnect();
                return new HttpResponse(result.toString(X.get("rnaIfFBvdceZ0_KpNB73lIctChtsqMj2satOkzsCqXo,", true)/* "UTF-8" */), connection.getResponseCode(), connection.getResponseMessage());
            }
            return new HttpResponse(null, connection.getResponseCode(), connection.getResponseMessage());
        }
        catch (TaskCancelledException ex)
        {
            return null;
        }
        catch (SocketTimeoutException ex)
        {
            return new HttpResponse(HttpRequestManager.TIMEOUT_ERROR, 0, null);
        }
        catch (Exception ex)
        {
            return new HttpResponse(HttpRequestManager.INTERNAL_ERROR, 0, null);
        }
    }

}
