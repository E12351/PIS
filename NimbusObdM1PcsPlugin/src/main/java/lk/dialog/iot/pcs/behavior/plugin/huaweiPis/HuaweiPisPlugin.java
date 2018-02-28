package lk.dialog.iot.pcs.behavior.plugin.huaweiPis;

import java.util.HashMap;
import java.util.Map;

import lk.dialog.iot.pcs.dto.MqttPublishDto;
import lk.dialog.iot.pcs.service.impl.PluginServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.HuaweiPisConstants;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.method.DataCollectionMethod;
import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.method.DeviceManagementMethod;

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

            String operation = String.valueOf(receivedMap.get("operation"));
            logger.info("Operation : " + operation);

            HashMap<String, String> responseMap = null;

            if (isDebigEnable) {
                logger.debug("Plugin received map : {}.", receivedMap);
            }

            if (receivedMap == null) {
                logger.info("Plugin received null map");
                throw new PluginBehaviorException("Plugin received null map");
            }

//            logger.info("MessageObj : {}", receivedMap.get("messageObject"));
//            logger.info(String.valueOf(receivedMap.get("messageObject")));

            HashMap<String, String> data = new HashMap();
            data = lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils.JsonUtil.jsonString2SimpleObj(String.valueOf(receivedMap.get("messageObject")), data.getClass());

            if(operation == "httpCalltoBroker"){
                publishRequested(data);

            }else {
                try {
                    String method = data.get("method");

                    switch (method) {
                        case "direct": {
                            responseMap = DeviceManagementMethod.directMethod(data, logger);
                            break;
                        }
                        case "NoNdirect": {
                            logger.info("Method : NoNdirect executed successfully.");
                            responseMap.put("state", "failed.");
                            break;
                        }
                        case "LastData": {
                            logger.info("Method : LastData executed successfully.");
                            responseMap = DataCollectionMethod.LastData(data, logger);
                            break;
                        }
                    }

                    publishRequested(responseMap);

                } catch (Exception e) {
                    logger.error("Exception : {}", e.getMessage());
                }
            }



            return responseMap;
        }

        private void publishRequested(HashMap responseMap) throws PluginBehaviorException {

            logger.info("mqtt published." + responseMap);

            MqttPublishDto mqttPublishActionDto = new MqttPublishDto();
            mqttPublishActionDto.setTopic("Reg");
            mqttPublishActionDto.setMessage(String.valueOf(responseMap));

            PluginServiceProvider pluginServiceProvider = PluginServiceProvider.getPluginServiceProvider();
            pluginServiceProvider.getMttqPublisherService().publishMessage(mqttPublishActionDto);

        }

//

        private HashMap<String, String> NoNdirectMethod(Map<String, String> data) throws Exception {
            HashMap<String, String> huaweiRes = new HashMap();

            //Should be implemented.
            return huaweiRes;
        }

    }

}
