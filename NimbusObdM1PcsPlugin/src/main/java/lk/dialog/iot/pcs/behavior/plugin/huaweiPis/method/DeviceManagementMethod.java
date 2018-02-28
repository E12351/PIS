/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.method;

import java.util.HashMap;
import java.util.Map;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request.AuthReq;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request.DeviceManagement;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.UtilPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dinuka_08966
 */
public class DeviceManagementMethod {

    private static DeviceManagement devicemanagement;
    private static AuthReq authreq;
    private static UtilPlugin util;

    static {
        devicemanagement = new DeviceManagement();
        authreq = new AuthReq();
        util = new UtilPlugin();
    }

    public static HashMap<String, String> directMethod(Map<String, String> data, Logger logger) throws Exception {

        logger.info("Method : direct executed successfully.");

        String mac = data.get("mac");

        HashMap<String, String> huaweiRes = new HashMap();

        HashMap responce = devicemanagement.regDirectDevice(mac);

        int state_code = util.responceCode(responce);
        logger.info("responce code : " + state_code);

        if (state_code == 200){
            String verifyCode = (String) responce.get("verifyCode");
            String psk = (String) responce.get("psk");
            String deviceId = (String) responce.get("deviceId");

            logger.info("verifyCode    : " + verifyCode);
            logger.info("psk           : " + psk);
            logger.info("deviceId 	   : " + deviceId);

            logger.info("Responce 	   : " + responce.get("state"));

            huaweiRes.put("state", String.valueOf(state_code));
            huaweiRes.put("verifyCode", verifyCode);
            huaweiRes.put("deviceId", deviceId);
        }
        if (state_code == 403) {
            logger.info("responce code : " + state_code + " refreshed");
            authreq.login();
            responce = devicemanagement.regDirectDevice(mac);
            state_code = util.responceCode(responce);

            String verifyCode = (String) responce.get("verifyCode");
            String psk = (String) responce.get("psk");
            String deviceId = (String) responce.get("deviceId");

            logger.info("verifyCode    : " + verifyCode);
            logger.info("psk           : " + psk);
            logger.info("deviceId 	   : " + deviceId);

            logger.info("Responce 	   : " + responce.get("state"));

            huaweiRes.put("state", String.valueOf(state_code));
            huaweiRes.put("verifyCode", verifyCode);
            huaweiRes.put("deviceId", deviceId);
        }
        if (state_code == 401) {
            huaweiRes.put("state", String.valueOf(state_code));
        }

        return huaweiRes;
    }

}
