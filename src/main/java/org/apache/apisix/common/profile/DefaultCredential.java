package org.apache.apisix.common.profile;

public class DefaultCredential implements Credential {
    private final String token;

    public DefaultCredential(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
