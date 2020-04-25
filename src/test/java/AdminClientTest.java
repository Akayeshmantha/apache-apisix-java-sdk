import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.apisix.admin.model.*;
import org.apache.apisix.admin.model.plugin.KeyAuth;
import org.apache.apisix.admin.model.plugin.LimitCount;
import org.apache.apisix.admin.model.plugin.Plugin;

import org.apache.apisix.admin.AdminClient;
import org.apache.apisix.common.profile.Credential;
import org.apache.apisix.common.profile.DefaultCredential;
import org.apache.apisix.common.profile.DefaultProfile;
import org.apache.apisix.common.profile.Profile;
import org.apache.apisix.common.exception.ApisixSDKExcetion;


public class AdminClientTest {
    private String endpoint = "localhost:9080";
    private String version = "1.1";
    private String apiKey = "edd1c9f034335f136f87ad84b625c8f1";

    //@Test
    public void testUpstream() throws ApisixSDKExcetion {
        Credential credential = new DefaultCredential(apiKey);
        Profile profile = DefaultProfile.getProfile(endpoint, version, credential);
        AdminClient adminClient = new AdminClient(profile);

        String id = "2";
        Upstream upstream = new Upstream();
        Map<String, Integer> nodes = new HashMap<>();

        nodes.put("127.0.0.1:8080", 1);

        upstream.setType("roundrobin");
        upstream.setDesc("upstream created by java sdk.");
        upstream.setNodes(nodes);

        adminClient.putUpstream(id, upstream);

        Upstream up = adminClient.getUpstream(id);

        assertEquals("upstream created by java sdk.", up.getDesc());
        assertEquals("roundrobin", up.getType());


        List<Upstream> list = adminClient.listUpstreams();
        int size1 = list.size();

        //must has 1 or more routes
        assertTrue(size1 > 0);

        //delete not exist route
        try {
            adminClient.deleteUpstream("id-not-exists");
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

        assertTrue(adminClient.deleteUpstream(id));

        try {
            adminClient.getUpstream(id);
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

    }


    //@Test
    public void testService() throws ApisixSDKExcetion {
        Credential credential = new DefaultCredential(apiKey);
        Profile profile = DefaultProfile.getProfile(endpoint, version, credential);
        AdminClient adminClient = new AdminClient(profile);


        String id = "2";
        Service service = new Service();
        Upstream upstream = new Upstream();
        Map<String, Integer> nodes = new HashMap<>();
        List<String> methods = new ArrayList<>();

        nodes.put("127.0.0.1:8080", 1);
        methods.add("GET");
        upstream.setType("roundrobin");
        upstream.setNodes(nodes);


        LimitCount lmt = new LimitCount();
        lmt.setCount(2);
        lmt.setKey("remote_addr");
        lmt.setRejectedCode(503);
        lmt.setTimeWindow(60);

        Map<String, Plugin> plugins = new HashMap<>();
        plugins.put("limit-count", lmt);

        service.setUpstream(upstream);
        service.setDesc("service created by java sdk.");
        service.setPlugins(plugins);

        adminClient.putService(id, service);

        Service svc = adminClient.getService(id);

        assertEquals("service created by java sdk.", svc.getDesc());


        List<Service> list = adminClient.listServices();
        int size1 = list.size();

        //must has 1 or more services
        assertTrue(size1 > 0);

        //delete not exist service
        try {
            adminClient.deleteService("id-not-exists");
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

        boolean delRes = adminClient.deleteService(id);
        assertTrue(delRes);

        try {
            adminClient.getService(id);
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

    }

    //@Test
    public void testRoute() throws ApisixSDKExcetion {
        Credential credential = new DefaultCredential(apiKey);
        Profile profile = DefaultProfile.getProfile(endpoint, version, credential);
        AdminClient adminClient = new AdminClient(profile);

        //创建upstream
        String upstreamId = "3";
        Upstream upstream = new Upstream();
        Map<String, Integer> nodes = new HashMap<>();
        nodes.put("127.0.0.1:8080", 1);

        upstream.setType("roundrobin");
        upstream.setDesc("upstream created by java sdk.");
        upstream.setNodes(nodes);

        adminClient.putUpstream(upstreamId, upstream);

        //创建service

        String serviceId = "3";
        Service service = new Service();

        LimitCount lmt = new LimitCount();
        lmt.setCount(2);
        lmt.setKey("remote_addr");
        lmt.setRejectedCode(503);
        lmt.setTimeWindow(60);

        Map<String, Plugin> plugins = new HashMap<>();
        plugins.put("limit-count", lmt);

        service.setUpstreamId(upstreamId);
        service.setDesc("service created by java sdk.");
        service.setPlugins(plugins);

        adminClient.putService(serviceId, service);

        String routeId = "3";
        Route route = new Route();

        List<String> methods = new ArrayList<>();

        nodes.put("127.0.0.1:8080", 1);
        methods.add("GET");
        upstream.setType("roundrobin");
        upstream.setNodes(nodes);

        route.setUri("/helloworld");
        route.setDesc("route created by java sdk");
        route.setMethods(methods);
        route.setServiceId(serviceId);

        adminClient.putRoute(routeId, route);

        Route routeEntity = adminClient.getRoute(routeId);

        assertEquals("/helloworld", routeEntity.getUri());
        assertEquals(serviceId, routeEntity.getServiceId());


        List<Route> list = adminClient.listRoutes();
        int size1 = list.size();

        //must has 1 or more routes
        assertTrue(size1 > 0);

        //delete not exist route
        try {
            adminClient.deleteRoute("id-not-exists");
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

        assertTrue(adminClient.deleteRoute(routeId));
        assertTrue(adminClient.deleteService(serviceId));
        assertTrue(adminClient.deleteUpstream(upstreamId));

        list = adminClient.listRoutes();
        int size2 = list.size();

        //size minus
        assertEquals(size1, size2 + 1);

        try {
            adminClient.getRoute(routeId);
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }
    }

    @Test
    public void testK8sInfo() throws ApisixSDKExcetion {
        Credential credential = new DefaultCredential(apiKey);
        Profile profile = DefaultProfile.getProfile(endpoint, version, credential);
        AdminClient adminClient = new AdminClient(profile);

        //创建upstream
        Upstream upstream = new Upstream();
        Map<String, Integer> nodes = new HashMap<>();
        K8sDeploymentInfo k8sInfo = new K8sDeploymentInfo();

        k8sInfo.setNamespace("test-namespace");
        k8sInfo.setDeployName("test-deploy");
        k8sInfo.setServiceName("test-service");
        k8sInfo.setPort(8080);
        k8sInfo.setBackendType("pod");

        String routeId = "3";
        Route route = new Route();

        List<String> methods = new ArrayList<>();

        nodes.put("127.0.0.1:8080", 1);
        methods.add("GET");
        upstream.setType("roundrobin");
        upstream.setNodes(nodes);
        upstream.setK8sDeploymentInfo(k8sInfo);

        route.setUri("/helloworld");
        route.setDesc("route created by java sdk");
        route.setMethods(methods);
        route.setUpstream(upstream);

        adminClient.putRoute(routeId, route);

        Route routeEntity = adminClient.getRoute(routeId);

        assertEquals("/helloworld", routeEntity.getUri());


        List<Route> list = adminClient.listRoutes();
        int size1 = list.size();

        //must has 1 or more routes
        assertTrue(size1 > 0);

        //delete not exist route
        try {
            adminClient.deleteRoute("id-not-exists");
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

        String namespace = k8sInfo.getNamespace();
        String deployName = k8sInfo.getDeployName();
        String serviceName = k8sInfo.getServiceName();
        String upstreamId = namespace + "-" + deployName + "-" + serviceName;

        assertTrue(adminClient.deleteRoute(routeId));
        assertTrue(adminClient.deleteUpstream(upstreamId));

        list = adminClient.listRoutes();
        int size2 = list.size();

        //size minus
        assertEquals(size1, size2 + 1);

        try {
            adminClient.getRoute(routeId);
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }
    }

    //@Test
    public void testConsumer() throws ApisixSDKExcetion {
        Credential credential = new DefaultCredential(apiKey);
        Profile profile = DefaultProfile.getProfile(endpoint, version, credential);
        AdminClient adminClient = new AdminClient(profile);

        String username = "test";
        Consumer consumer = new Consumer();

        KeyAuth keyAuth = new KeyAuth();
        keyAuth.setKey("testkey");

        Map<String, Plugin> plugins = new HashMap<>();
        plugins.put("key-auth", keyAuth);

        consumer.setUsername("test");
        consumer.setDesc("consumer created by java sdk.");
        consumer.setPlugins(plugins);

        adminClient.putConsumer(username, consumer);

        Consumer csm = adminClient.getConsumer(username);

        assertEquals("consumer created by java sdk.", csm.getDesc());
        assertEquals(username, csm.getUsername());


        //delete not exist consumer
        try {
            adminClient.deleteConsumer("id-not-exists");
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

        assertTrue(adminClient.deleteConsumer(username));

        try {
            adminClient.getConsumer(username);
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

    }

    //@Test
    public void testSSL() throws ApisixSDKExcetion {
        Credential credential = new DefaultCredential(apiKey);
        Profile profile = DefaultProfile.getProfile(endpoint, version, credential);
        AdminClient adminClient = new AdminClient(profile);

        String id = "2";
        SSL ssl = new SSL();

        ssl.setCert("fake-cert-contentfake-cert-contentfake-cert-contentfake-cert-contentfake-cert-contentfake-cert-contentfake-cert-contentfake-cert-contentfake-cert-contentfake-cert-contentfake-cert-contentfake-cert-content");
        ssl.setKey("fake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-contentfake-key-content");
        ssl.setSni("sni.cn");

        adminClient.putSSL(id, ssl);

        SSL s = adminClient.getSSL(id);

        assertEquals("sni.cn", s.getSni());

        //delete not exist ssl
        try {
            adminClient.deleteSSL("id-not-exists");
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

        assertTrue(adminClient.deleteSSL(id));

        try {
            adminClient.getSSL(id);
        } catch (ApisixSDKExcetion e) {
            assertEquals(e.getErrorCode(), "404");
        }

    }
}
