/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.method;

import java.util.HashMap;
import java.util.Map;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request.AuthReq;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request.DataCollection;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request.DeviceManagement;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.UtilPlugin;
import org.slf4j.Logger;

/**
 *
 * @author Dinuka_08966
 */
public class DataCollectionMethod {

    private static AuthReq authreq;
    private static UtilPlugin util;
    private static DataCollection datacollection;

    static {
        authreq = new AuthReq();
        datacollection = new DataCollection();
        util = new UtilPlugin();
    }

    public static HashMap<String, String> LastData(Map<String, String> data, Logger logger) {
        HashMap huaweiRes = new HashMap();

        try {
            HashMap responce = datacollection.historicaldata(data.get("123"), data.get(""));
            int state_code = util.responceCode(responce);

            if (state_code == 200) {
                return responce;
            } else if (state_code == 403) {
                logger.info("responce code : " + state_code + " refreshed");
                authreq.login();
                DataCollectionMethod.LastData(data, logger);

                logger.info("responce code : " + state_code);
            } else if (state_code == 401) {
                huaweiRes.put("state", String.valueOf(state_code));
            }

        } catch (Exception e) {
            logger.info("Exception LastData : " + e.getMessage());
        }
        return huaweiRes;
    }
}
