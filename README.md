# Java SDK for Apache APISIX

More detailed documentation please refer to:
- https://github.com/apache/incubator-apisix/blob/master/doc/admin-api-cn.md

- https://github.com/apache/incubator-apisix/blob/master/doc/architecture-design-cn.md


# Example

```java

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apiseven.apisix.admin.AdminClient;
import com.apiseven.apisix.admin.model.Route;
import com.apiseven.apisix.admin.model.Upstream;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;


String endpoint = "127.0.0.1:9080";
String version = "1.1";
Credential credential = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
Profile profile = DefaultProfile.getProfile(endpoint, version, credential);
AdminClient adminClient = new AdminClient(profile);


String id = "1";
Route route = new Route();
Upstream upstream = new Upstream();
Map<String, Integer> nodes = new HashMap<>();
List<String> methods = new ArrayList<>();

nodes.put("127.0.0.1:8080", 1);
methods.add("GET");
upstream.setType("roundrobin");
upstream.setNodes(nodes);

route.setUri("/helloworld");
route.setDesc("route added by java sdk");
route.setMethods(methods);
route.setUpstream(upstream);

Route res = adminClient.putRoute(id, route);

```


