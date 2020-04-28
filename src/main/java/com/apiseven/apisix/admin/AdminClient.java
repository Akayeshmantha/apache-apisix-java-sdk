package com.apiseven.apisix.admin;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.apiseven.apisix.admin.model.*;
import com.apiseven.apisix.admin.model.response.Item;
import com.apiseven.apisix.admin.model.response.Multi;
import com.apiseven.apisix.admin.model.response.Wrap;
import com.apiseven.apisix.common.BaseClient;
import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.HttpProfile;
import com.apiseven.apisix.common.profile.Profile;

import java.lang.reflect.Type;
import java.util.List;

public class AdminClient extends BaseClient {

    public AdminClient(Profile profile) {
        super(profile);
    }

    //路由列表
    public List<Route> listRoutes() throws ApisixSDKExcetion {
        Wrap<Multi<Route>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Multi<Route>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/routes"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<Route> result = this.arrangeMulti(rsp.node.nodes);

        return result;
    }

    //按id获取路由
    public Route getRoute(String id) throws ApisixSDKExcetion {
        Wrap<Item<Route>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<Route>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //删除指定id的路由
    public boolean deleteRoute(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/routes/" + id);
        return true;
    }

    //按指定id创建路由
    public Route putRoute(String id, Route route) throws ApisixSDKExcetion {
        Wrap<Item<Route>> rsp = null;
        try {
            route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Item<Route>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //自动生成id创建路由
    public Route postRoute(Route route) throws ApisixSDKExcetion {
        Wrap<Item<Route>> rsp = null;
        try {
            route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Item<Route>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_POST, "/apisix/admin/routes/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    private Route resolveUpstream(Route route) throws ApisixSDKExcetion {
        Upstream upstream = route.getUpstream();
        if(upstream != null) {
            K8sDeploymentInfo k8sDeploymentInfo = upstream.getK8sDeploymentInfo();
            //k8s deployment info is not empty
            if (k8sDeploymentInfo != null) {
                String namespace = k8sDeploymentInfo.getNamespace();
                String deployName = k8sDeploymentInfo.getDeployName();
                String serviceName = k8sDeploymentInfo.getServiceName();
                if(deployName != null && !deployName.equals("") || (serviceName != null && !serviceName.equals(""))){
                    String upstreamId =   namespace + "-" + deployName + "-" + serviceName + "-" + k8sDeploymentInfo.getPort() ;
                    Upstream res = putUpstream(upstreamId, upstream);
                    if (k8sDeploymentInfo.toString().equals(res.getK8sDeploymentInfo().toString())) {
                        //replace to upstream id
                        route.setUpstream(null);
                        route.setUpstreamId(upstreamId);
                    }
                }
            }
        }
        return route;
    }

    private Service resolveUpstream(Service service) throws ApisixSDKExcetion {
        Upstream upstream = service.getUpstream();
        if(upstream != null) {
            K8sDeploymentInfo k8sDeploymentInfo = upstream.getK8sDeploymentInfo();
            //k8s deployment info is not empty
            if (k8sDeploymentInfo != null) {
                String namespace = k8sDeploymentInfo.getNamespace();
                String deployName = k8sDeploymentInfo.getDeployName();
                String serviceName = k8sDeploymentInfo.getServiceName();
                if(deployName != null && !deployName.equals("") || (serviceName != null && !serviceName.equals(""))){
                    String upstreamId = namespace + "-" + deployName + "-" + serviceName + "-" + k8sDeploymentInfo.getPort() ;
                    Upstream res = putUpstream(upstreamId, upstream);
                    if (k8sDeploymentInfo.toString().equals(res.getK8sDeploymentInfo().toString())) {
                        //replace to upstream id
                        service.setUpstream(null);
                        service.setUpstreamId(upstreamId);
                    }
                }
            }
        }
        return service;
    }


    //service列表
    public List<Service> listServices() throws ApisixSDKExcetion {
        Wrap<Multi<Service>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Multi<Service>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/services"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<Service> result = this.arrangeMulti(rsp.node.nodes);

        return result;
    }

    //按id获取service
    public Service getService(String id) throws ApisixSDKExcetion {
        Wrap<Item<Service>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<Service>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/services/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //删除指定id的service
    public boolean deleteService(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/services/" + id);
        return true;
    }

    //按指定id创建service
    public Service putService(String id, Service service) throws ApisixSDKExcetion {
        Wrap<Item<Service>> rsp = null;
        try {
            service = resolveUpstream(service);
            Type type = new TypeToken<Wrap<Item<Service>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(service, HttpProfile.REQ_PUT, "/apisix/admin/services/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //自动生成id创建service
    public Service postService(Service service) throws ApisixSDKExcetion {
        Wrap<Item<Service>> rsp = null;
        try {
            service = resolveUpstream(service);
            Type type = new TypeToken<Wrap<Item<Service>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(service, HttpProfile.REQ_POST, "/apisix/admin/services/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //upstream列表
    public List<Upstream> listUpstreams() throws ApisixSDKExcetion {
        Wrap<Multi<Upstream>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Multi<Upstream>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/upstreams"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<Upstream> result = this.arrangeMulti(rsp.node.nodes);

        return result;
    }

    //按id获取upstream
    public Upstream getUpstream(String id) throws ApisixSDKExcetion {
        Wrap<Item<Upstream>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<Upstream>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/upstreams/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //删除指定id的upstream
    public boolean deleteUpstream(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/upstreams/" + id);
        return true;
    }

    //按指定id创建upstream
    public Upstream putUpstream(String id, Upstream upstream) throws ApisixSDKExcetion {
        Wrap<Item<Upstream>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<Upstream>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(upstream, HttpProfile.REQ_PUT, "/apisix/admin/upstreams/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //自动生成id创建upstream
    public Upstream postUpstream(Upstream upstream) throws ApisixSDKExcetion {
        Wrap<Item<Upstream>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<Upstream>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(upstream, HttpProfile.REQ_POST, "/apisix/admin/upstreams/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //consumer列表
    public List<Consumer> listConsumers() throws ApisixSDKExcetion {
        Wrap<Multi<Consumer>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Multi<Consumer>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumers"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<Consumer> result = this.arrangeMulti(rsp.node.nodes);

        return result;
    }

    //按id获取consumer
    public Consumer getConsumer(String username) throws ApisixSDKExcetion {
        Wrap<Item<Consumer>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<Consumer>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumers/" + username), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //删除指定id的consumer
    public boolean deleteConsumer(String username) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/consumers/" + username);
        return true;
    }

    //按指定id创建consumer
    public Consumer putConsumer(String username, Consumer consumer) throws ApisixSDKExcetion {
        Wrap<Item<Consumer>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<Consumer>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(consumer, HttpProfile.REQ_PUT, "/apisix/admin/consumers/" + username), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //自动生成id创建consumer
    public Consumer postConsumer(Consumer consumer) throws ApisixSDKExcetion {
        Wrap<Item<Consumer>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<Consumer>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(consumer, HttpProfile.REQ_POST, "/apisix/admin/consumers/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //ssl列表
    public List<SSL> listSSLs() throws ApisixSDKExcetion {
        Wrap<Multi<SSL>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Multi<SSL>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/ssl"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<SSL> result = this.arrangeMulti(rsp.node.nodes);

        return result;
    }

    //按id获取ssl
    public SSL getSSL(String id) throws ApisixSDKExcetion {
        Wrap<Item<SSL>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<SSL>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/ssl/" + id), type);
        } catch (ApisixSDKExcetion | JsonSyntaxException e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //删除指定id的ssl
    public boolean deleteSSL(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/ssl/" + id);
        return true;
    }

    //按指定id创建ssl
    public SSL putSSL(String id, SSL ssl) throws ApisixSDKExcetion {
        Wrap<Item<SSL>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<SSL>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(ssl, HttpProfile.REQ_PUT, "/apisix/admin/ssl/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

    //自动生成id创建ssl
    public SSL postSSL(SSL ssl) throws ApisixSDKExcetion {
        Wrap<Item<SSL>> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Item<SSL>>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(ssl, HttpProfile.REQ_POST, "/apisix/admin/ssl/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.node.value;
    }

}
