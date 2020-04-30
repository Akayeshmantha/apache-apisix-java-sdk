package com.apiseven.apisix.common.profile;

import org.slf4j.Logger;

public interface Profile {
    Credential getCredential();
    HttpProfile getHttpProfile();
    Logger getLogger();
    void setLogger(Logger logger);
    String getVersion();
    String getEndpoint();
}
