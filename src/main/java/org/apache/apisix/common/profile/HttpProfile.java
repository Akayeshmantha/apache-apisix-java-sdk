package org.apache.apisix.common.profile;

public class HttpProfile {

    public static final String REQ_HTTPS = "https://";
    public static final String REQ_HTTP = "http://";
    public static final String REQ_POST = "POST";
    public static final String REQ_GET = "GET";
    public static final String REQ_PUT = "PUT";
    public static final String REQ_DELETE = "DELETE";

    private String endpoint;

    private String protocol;

    private int readTimeout;

    private int writeTimeout;

    private int connTimeout;


    public HttpProfile() {
        this.endpoint = null;
        this.protocol = HttpProfile.REQ_HTTP;
        this.readTimeout = 10;
        this.writeTimeout = 10;
        this.connTimeout = 30;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    public int getConnTimeout() {
        return this.connTimeout;
    }

    public String getProtocol() {
        return this.protocol;
    }

}