package com.apiseven.apisix.common.profile;

import org.slf4j.Logger;

public class DefaultProfile implements Profile {
    private String endpoint;
    private Credential credential;
    private Logger logger;
    private String version;
    private HttpProfile httpProfile;

    private DefaultProfile(String endpoint, String version, Credential credential) {
        this.credential = credential;
        this.endpoint   = endpoint;
        this.version = version;
        this.httpProfile = new HttpProfile();
    }

    public static synchronized DefaultProfile getProfile(String endpoint, String version, Credential credential) {
        DefaultProfile profile = new DefaultProfile(endpoint, version, credential);
        return profile;
    }

    public synchronized Credential getCredential() {
        return this.credential;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getVersion(){
        return this.version;
    }

    public String getEndpoint(){
        return this.endpoint;
    }

    public HttpProfile getHttpProfile(){
        return this.httpProfile;
    }

}
