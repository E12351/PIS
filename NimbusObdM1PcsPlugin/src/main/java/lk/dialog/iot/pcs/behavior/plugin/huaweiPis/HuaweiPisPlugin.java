package lk.dialog.iot.pcs.behavior.plugin.huaweiPis;

import java.util.HashMap;
import java.util.Map;

import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request.AuthReq;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request.DataCollection;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Request.DeviceManagement;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.UtilPlugin;

import lk.dialog.iot.pcs.dto.MqttPublishDto;
import lk.dialog.iot.pcs.service.impl.PluginServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.HuaweiPisConstants;

import lk.dialog.iot.pcs.exception.impl.PluginBehaviorException;
import lk.dialog.iot.pcs.service.PluginBehavior;

import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

public class HuaweiPisPlugin extends Plugin {

    public HuaweiPisPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class HuaweiPisPluginInner implements PluginBehavior {

        AuthReq authreq = new AuthReq();
        DataCollection datacollection = new DataCollection();
        DeviceManagement devicemanagement = new DeviceManagement();

        UtilPlugin util = new UtilPlugin();

        private Logger logger = LoggerFactory.getLogger(this.getClass());
        private boolean isDebigEnable = logger.isDebugEnabled();

//        private RestTemplate restTemplate = new RestTemplate();
//        private ObjectMapper objectMapper = new ObjectMapper();
//        private HttpEntity<MultiValueMap<String, String>> accessTokenHttpEntity = null;
//        private Long lastTokenGeneratedTime = null;
//        private String lastGeneratedToken = null;
        @Override
        public String toString() {
            return HuaweiPisConstants.PLUGIN_NAME;
        }

        public HashMap<String, String> pluginOperation(Map<String, Object> receivedMap) throws PluginBehaviorException {

            HashMap<String, String> responseMap = null;

            if (isDebigEnable) {
                logger.debug("Plugin received map : {}.", receivedMap);
            }

            if (receivedMap == null) {
                logger.info("Plugin received null map");
                throw new PluginBehaviorException("Plugin received null map");
            }

            logger.info("MessageObj : {}", receivedMap.get("messageObject"));

            logger.info(String.valueOf(receivedMap.get("messageObject")));

            try {
                Map<String, String> data = new HashMap();
                data = lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.JsonUtil.jsonString2SimpleObj(String.valueOf(receivedMap.get("messageObject")), data.getClass());

                String method = data.get("method");

                logger.info("Method : {}", method);

                switch (method) {
                    case "direct": {
                        responseMap = directMethod(data);
                        break;

                    }
                    case "NoNdirect": {
                        logger.info("Method : NoNdirect executed successfully.");
                        responseMap.put("state", "failed.");
                        break;
                    }
                }

            } catch (Exception e) {
                logger.error("Exception : {}", e.getMessage());
            }


            publishRequestedNimbusObdStatus(responseMap);
            return responseMap;
        }

        private void publishRequestedNimbusObdStatus(HashMap<String, String> receivedMap) throws PluginBehaviorException {

            logger.info("mqtt published.");

            MqttPublishDto mqttPublishActionDto = new MqttPublishDto();
            mqttPublishActionDto.setTopic("Reg");
            mqttPublishActionDto.setMessage("Hello Test");

            PluginServiceProvider pluginServiceProvider = PluginServiceProvider.getPluginServiceProvider();
            pluginServiceProvider.getMttqPublisherService().publishMessage(mqttPublishActionDto);

        }
//

        private HashMap<String,String> directMethod(Map<String, String> data) throws Exception {

            logger.info("Method : direct executed successfully.");

            String mac = data.get("mac");

            HashMap<String,String> huaweiRes = new HashMap();

            HashMap responce = devicemanagement.regDirectDevice(mac);
            int state_code = util.responceCode(responce);
            logger.info("responce code : " + state_code);

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
                huaweiRes.put("deviceId" , deviceId);
            }
            if (state_code == 401) {
                huaweiRes.put("state", String.valueOf(state_code));
            }

            return huaweiRes;
        }
    }

}
