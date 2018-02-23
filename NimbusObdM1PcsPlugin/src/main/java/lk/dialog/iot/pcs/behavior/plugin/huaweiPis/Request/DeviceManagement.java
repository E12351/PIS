package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter.AuthHandle;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter.Constant;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.HttpsUtil;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.JsonUtil;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.StreamClosedHttpResponse;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.factory.Factory;

@Service
public class DeviceManagement {

    public HashMap regDirectDevice(String nodeId) throws Exception {
        AuthHandle authHandle = Factory.getAuthHandale();

        String IP = Constant.URL;
        String port = Constant.PORT;
        String appID = Constant.APPID;

        String accessToken = authHandle.getaccessToken();

        String url = "https://" + IP + ":" + port + "/iocm/app/reg/v1.2.0/devices?appId=" + appID + "";

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> hedder = new HashMap();
        hedder.put("app_key", appID);
        hedder.put("Authorization", accessToken);
        hedder.put("Content-Type", "application/json");

        Map<String, Object> param_reg = new HashMap();
        param_reg.put("verifyCode", nodeId);
        param_reg.put("nodeId", nodeId);
        param_reg.put("timeout", 0);

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);

        StreamClosedHttpResponse bodyRefreshToken = httpsUtil.doPostJsonGetStatusLine(url, hedder, jsonRequest);

        Map<String, String> data = new HashMap();
        data = JsonUtil.jsonString2SimpleObj(bodyRefreshToken.getContent(), data.getClass());

//        System.out.println();
//        System.out.println(bodyRefreshToken.getStatusLine());
        String state = String.valueOf(bodyRefreshToken.getStatusLine());
        data.put("state", state);
        System.out.println(state);

        return (HashMap) data;
    }

    public String regNoNDirectDevice(String IP, String port, String appID, String deviceId, String accessToken) {

        String walletBalanceUrl = "https://" + IP + ":" + port + "/iocm/app/sec/v1.1.0/devices/{" + deviceId + "}/services/Discovery/sendCommand?appId={" + appID + "}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("app_key", appID);
        httpHeaders.set("Authorization", "Bearer {" + accessToken + "}");
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();

        json1.put("mode", "ACK");
        json1.put("from", "xxxxxxxxxxxxxxxxx");
        json1.put("to", "null");
        json1.put("toType", "null");
        json1.put("method", "DISCOVERY");
        json1.put("requestId", "xxxxxxxxxxx");
        json1.put("callbackURL", "http://server:port/na/iocm/message/confirm");

        json3.put("header", json1);

        json2.put("from", "xxxxxxxxxxxx");
        json2.put("sessionID", "xxxxxxxxxxxx");
        json2.put("sdp", "xxxxxxxxxxxx");

        json3.put("body", json2);

        HttpEntity<String> httpEntity = new HttpEntity<String>(json3.toString(), httpHeaders);
        System.out.println(httpEntity);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(walletBalanceUrl, httpEntity, String.class);

        return response;
    }

    public Map<String, String> activationStatus(String IP, String port, String deviceId, String appId, String accessToken) throws Exception {

        String url = "https://" + IP + ":" + port + "/iocm/app/reg/v1.1.0/devices/" + deviceId + "?appId=" + appId;

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, Object> param_reg = new HashMap();
        param_reg.put("appId", appId);
        param_reg.put("Authorization", accessToken);
        param_reg.put("Content-Type", "application/json");

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
        StreamClosedHttpResponse bodyRefreshToken = httpsUtil.doPostJsonGetStatusLine(url, jsonRequest);
        Map<String, String> data = new HashMap();
        data = JsonUtil.jsonString2SimpleObj(bodyRefreshToken.getContent(), data.getClass());

        String state = String.valueOf(bodyRefreshToken.getStatusLine());
        data.put("state", state);

        return data;
    }

    public void deleteDirectDevice(String IP, String port, String deviceId, String appId, String accessToken) throws Exception {
//
        String url = "https://" + IP + ":" + port + "/iocm/app/dm/v1.1.0/devices/" + deviceId + "?appId=" + appId;

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> hedder = new HashMap();
        hedder.put("app_key", appId);
        hedder.put("Authorization", accessToken);
        hedder.put("Content-Type", "application/json");

        StreamClosedHttpResponse response = httpsUtil.doDeleteGetStatusLine(url, hedder);

        System.out.println(response);

    }

    public void deleteNoNDirectDevice(String IP, String port, String deviceId, String appId, String accessToken) {
//
        String walletUrl = "https://" + IP + ":" + port + "/iocm/app/sec/v1.1.0/devices/{" + deviceId + "}/services/Remover/sendCommand?appId={" + appId + "}";

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("app_key", appId);
        httpHeaders.set("Authorization", "Bearer {" + accessToken + "}");
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();

        json1.put("mode", "ACK");
        json1.put("from", "xxxxxxxxxxxxxxxxx");
        json1.put("to", "null");
        json1.put("toType", "null");
        json1.put("method", "REMOVE");
        json1.put("requestId", "xxxxxxxxxxx");
        json1.put("callbackURL", "null");

        json3.put("header", json1);

        json2.put("timeout", 90);
        json2.put("deviceId", "xxxxxxxxxxxx");
        json2.put("mode", "ON");

        json3.put("body", json2);

        HttpEntity<String> httpEntity = new HttpEntity<String>(json3.toString(), httpHeaders);
        System.out.println(httpEntity);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(walletUrl, httpEntity, String.class);

//        return response;
    }

    public Map<String, String> deviceCapabilities(String deviceId) throws Exception {
        AuthHandle authHandle = Factory.getAuthHandale();
        String IP = Constant.URL;
        String port = Constant.PORT;
        String appID = Constant.APPID;

        String accessToken = authHandle.getaccessToken();

//        String url = "https://"+IP+":"+port+"/iocm/app/data/v1.1.0/deviceCapabilities?deviceId="+deviceId;
        String url = "https://" + IP + ":" + port + "/iocm/app/data/v1.1.0/deviceCapabilities";

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> headers = new HashMap();
        headers.put("app_key", appID);
        headers.put("Authorization", accessToken);
        headers.put("Content-Type", "application/json");

        Map<String, String> quary = new HashMap();
        quary.put("deviceId", deviceId);

        StreamClosedHttpResponse response = httpsUtil.doGetWithParasGetStatusLine(url, quary, headers);

        Map<String, String> data = new HashMap();
        data = JsonUtil.jsonString2SimpleObj(response.getContent(), data.getClass());

        return data;
    }

}
