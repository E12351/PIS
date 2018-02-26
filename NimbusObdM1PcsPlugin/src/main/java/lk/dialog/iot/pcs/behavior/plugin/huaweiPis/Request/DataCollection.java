package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter.AuthHandle;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter.Constant;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.HttpsUtil;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.JsonUtil;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.StreamClosedHttpResponse;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.factory.Factory;

public class DataCollection {

    public HashMap deviceServiceInvocation(String serviceId, String deviceId) throws Exception {

        AuthHandle authHandle = Factory.getAuthHandale();
        String IP = Constant.URL;
        String port = Constant.PORT;
        String appID = Constant.APPID;

        String accessToken = authHandle.getaccessToken();

        String url = "https://" + IP + ":" + port + "/iocm/app/signaltrans/v1.1.0/devices/" + deviceId + "/services/" + serviceId + "/sendCommand?appId=" + appID;

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> hedder = new HashMap();
        hedder.put("app_key", appID);
        hedder.put("Authorization", accessToken);
        hedder.put("Content-Type", "application/json");

        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();

        json1.put("mode", "ACK");
        json1.put("method", "DISCOVERY");
        json1.put("callbackURL", Constant.CALLBACK);

        json3.put("header", json1);

        json2.put("from", "xxxxxxxxxxxx");
        json2.put("sessionID", "1234");
        json2.put("sdp", "xxxxxxxxxxxx");

        json3.put("body", json2);

        String jsonRequest = JsonUtil.jsonObj2Sting(json3);
        StreamClosedHttpResponse response = httpsUtil.doPostJsonGetStatusLine(url, hedder, jsonRequest);

        Map<String, String> data = new HashMap();
        data = JsonUtil.jsonString2SimpleObj(response.getContent(), data.getClass());
        data.put("state", String.valueOf(response.getStatusLine()));

        System.out.println("deviceServiceInvocation : " + response.getStatusLine());

        return (HashMap) data;
    }

    public HashMap subcribe(String notifyType) throws Exception {
        AuthHandle authHandle = Factory.getAuthHandale();
        String IP = Constant.URL;
        String port = Constant.PORT;
        String appID = Constant.APPID;

        String accessToken = authHandle.getaccessToken();

        String url = "https://" + IP + ":" + port + "/iocm/app/sub/v1.2.0/subscribe";

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> hedder = new HashMap();
        hedder.put("app_key", appID);
        hedder.put("Authorization", accessToken);
        hedder.put("Content-Type", "application/json");

        Map<String, String> param_reg = new HashMap();
        param_reg.put("notifyType", notifyType);
        param_reg.put("callbackurl", Constant.CALLBACK);

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
        StreamClosedHttpResponse response = httpsUtil.doPostJsonGetStatusLine(url, hedder, jsonRequest);

        Map<String, String> data = new HashMap();
        data = JsonUtil.jsonString2SimpleObj(response.getContent(), data.getClass());
        data.put("state", String.valueOf(response.getStatusLine()));

        return (HashMap) data;

    }

    public HashMap historicaldata(String deviceId, String gatewayId) throws Exception {

        AuthHandle authHandle = Factory.getAuthHandale();
        String IP = Constant.URL;
        String port = Constant.PORT;
        String appID = Constant.APPID;

        String accessToken = authHandle.getaccessToken();

        String url = "https://" + IP + ":" + port + "/iocm/app/data/v1.1.0/deviceDataHistory";

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> hedder = new HashMap();
        hedder.put("app_key", appID);
        hedder.put("Authorization", accessToken);
        hedder.put("Content-Type", "application/json");

        Map<String, String> param_reg = new HashMap();
        param_reg.put("deviceId", deviceId);
        param_reg.put("gatewayId", gatewayId);

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
        StreamClosedHttpResponse response = httpsUtil.doPostJsonGetStatusLine(url, hedder, jsonRequest);

        Map<String, String> data = new HashMap();
        data = JsonUtil.jsonString2SimpleObj(response.getContent(), data.getClass());
        data.put("state", String.valueOf(response.getStatusLine()));

//        System.out.println("Passed.");
        return (HashMap) data;

    }
}
