package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request;

import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter.AuthHandle;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter.Constant;

import java.util.HashMap;
import java.util.Map;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.HttpsUtil;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.JsonUtil;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.StreamClosedHttpResponse;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.factory.Factory;

public class AuthReq {

    public HashMap login() throws Exception {
    
            AuthHandle authHandale = Factory.getAuthHandale();


            String appID = Constant.APPID;
            String port = Constant.PORT;
            String IP = Constant.URL;
            String secret = Constant.SECRET;

            String urlLogin = "https://" + IP + ":" + port + "/iocm/app/sec/v1.1.0/login";

            lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.HttpsUtil httpsUtil = new lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.HttpsUtil();

            httpsUtil.initSSLConfigForTwoWay();

            Map<String, String> param = new HashMap();
            param.put("appId", appID);
            param.put("secret", secret);

            lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, param);

            //resolve the value of accessToken from responseLogin.
            Map<String, String> data = new HashMap();
//        System.out.println("reach 1 " + responseLogin.getContent());

            data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
//            System.out.println(data.toString());
            String state = String.valueOf(responseLogin.getStatusLine());
            String[] stat_code = state.split(" ");

            data.put("state", stat_code[1]);


            authHandale.setaccessToken(data.get("accessToken"));
//            System.out.println(data);

            return (HashMap) data;
      
    }

    public HashMap refreshToken(String IP, String port, Object appID, Object secret, Object refreshToken) throws Exception {

        String urlRefreshToken = "https://" + IP + ":" + port + "/iocm/app/sec/v1.1.0/refreshToken";
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, Object> param_reg = new HashMap<>();
        param_reg.put("appId", appID);
        param_reg.put("secret", secret);
        param_reg.put("refreshToken", refreshToken);

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
        StreamClosedHttpResponse bodyRefreshToken = httpsUtil.doPostJsonGetStatusLine(urlRefreshToken, jsonRequest);
        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(bodyRefreshToken.getContent(), data.getClass());

        String state = String.valueOf(bodyRefreshToken.getStatusLine());
        data.put("state", state);

        return (HashMap) data;
    }

    public void logout() throws Exception {
        AuthHandle authHandale = Factory.getAuthHandale();
        String IP = Constant.URL;
        String port = Constant.PORT;
        String accessToken = authHandale.getaccessToken();

        String url = "https://" + IP + ":" + port + "/iocm/app/sec/v1.1.0/logout";

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> hedder = new HashMap<>();
        hedder.put("Content-Type", "application/json");

        Map<String, Object> param_reg = new HashMap<>();
        param_reg.put("accessToken", accessToken);

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);

        StreamClosedHttpResponse bodyRefreshToken = httpsUtil.doPostJsonGetStatusLine(url, hedder, jsonRequest);

        System.out.println(bodyRefreshToken);
    }
}
