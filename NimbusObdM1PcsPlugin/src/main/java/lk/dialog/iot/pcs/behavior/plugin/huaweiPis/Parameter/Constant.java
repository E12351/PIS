package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter;

public class Constant {

    //please replace the IP and Port, when you use the demo.
    public static final String URL = "211.25.75.100";
    public static final String PORT = "8743";

    public static final String CALLBACK = "http://iotconnect.dialog.lk/inbound/huaweiOBD/";

    //please replace the appId and secret, when you use the demo.
    public static final String APPID = "L0sBaFLJKiXfdyXyxzPN1PMY5Q8a";
    public static final String SECRET = "fEL5Fjc8eIOR7Gj2pCzoC2E_voga";

    //Paths of certificates.
    public static String SELFCERTPATH   = "/cert/outgoing.CertwithKey.pkcs12";
    public static String TRUSTCAPATH    = "/cert/ca.jks";

    //Password of certificates.
    public static String SELFCERTPWD    = "IoM@1234";
    public static String TRUSTCAPWD     = "Huawei@123";

    //*************************** The following constants are related with mqtt client *******************************//
    public static String BROKER         = "tcp://127.0.0.1:1883";
    public static String BROKER_PSWRD   = "rabbit";
    public static String BROKER_USER    = "rabbit";
//    public static String BROKER_SUB     = "+/custom/pcs/common";

    //*************************** The following constants are related with Service ID  *******************************//
    /*
     * notify Type
     * serviceInfoChanged|deviceInfoChanged|LocationChanged|deviceDataChanged|deviceDatasChanged
     * deviceAdded|deviceDeleted|messageConfirm|commandRsp|deviceEvent|ruleEvent
     */
    public static final String DEVICE_ADDED = "deviceAdded";
    public static final String DEVICE_INFO_CHANGED = "deviceInfoChanged";
    public static final String DEVICE_DATA_CHANGED = "deviceDataChanged";
    public static final String DEVICE_DELETED = "deviceDeleted";
    public static final String MESSAGE_CONFIRM = "messageConfirm";
    public static final String SERVICE_INFO_CHANGED = "serviceInfoChanged";
    public static final String COMMAND_RSP = "commandRsp";
    public static final String DEVICE_EVENT = "deviceEvent";
    public static final String RULE_EVENT = "ruleEvent";
    public static final String DEVICE_DATAS_CHANGED = "deviceDatasChanged";

//    //*************************** The following constants do not need to be modified *********************************//
//
//    /*
//     * request header
//     * 1. HEADER_APP_KEY
//     * 2. HEADER_APP_AUTH
//     */
//    public static final String HEADER_APP_KEY = "app_key";
//    public static final String HEADER_APP_AUTH = "Authorization";
//
//    /*
//     * Application Access Security:
//     * 1. APP_AUTH
//     * 2. REFRESH_TOKEN
//     */
//    public static final String APP_AUTH = BASE_URL + "/iocm/app/sec/v1.1.0/login";
//    public static final String REFRESH_TOKEN = BASE_URL + "/iocm/app/sec/v1.1.0/refreshToken";
//
//
//    /*
//     * Device Management:
//     * 1. REGISTER_DEVICE
//     * 2. MODIFY_DEVICE_INFO
//     * 3. QUERY_DEVICE_ACTIVATION_STATUS
//     * 4. DELETE_DEVICE
//     * 5. DISCOVER_INDIRECT_DEVICE
//     * 6. REMOVE_INDIRECT_DEVICE
//     */
//    public static final String REGISTER_DEVICE = BASE_URL + "/iocm/app/reg/v1.1.0/devices";
//    public static final String MODIFY_DEVICE_INFO = BASE_URL + "/iocm/app/dm/v1.1.0/devices";
//    public static final String QUERY_DEVICE_ACTIVATION_STATUS = BASE_URL + "/iocm/app/reg/v1.1.0/devices";
//    public static final String DELETE_DEVICE = BASE_URL + "/iocm/app/dm/v1.1.0/devices";
//    public static final String DISCOVER_INDIRECT_DEVICE = BASE_URL + "/iocm/app/signaltrans/v1.1.0/devices/%s/services/%s/sendCommand";
//    public static final String REMOVE_INDIRECT_DEVICE = BASE_URL + "/iocm/app/signaltrans/v1.1.0/devices/%s/services/%s/sendCommand";
//
//    /*
//     * Data Collection:
//     * 1. QUERY_DEVICES
//     * 2. QUERY_DEVICE_DATA
//     * 3. QUERY_DEVICE_HISTORY_DATA
//     * 4. QUERY_DEVICE_CAPABILITIES
//     * 5. SUBSCRIBE_NOTIFYCATION
//     */
//    public static final String QUERY_DEVICES = BASE_URL + "/iocm/app/dm/v1.3.0/devices";
//    public static final String QUERY_DEVICE_DATA = BASE_URL + "/iocm/app/dm/v1.3.0/devices";
//    public static final String QUERY_DEVICE_HISTORY_DATA = BASE_URL + "/iocm/app/data/v1.1.0/deviceDataHistory";
//    public static final String QUERY_DEVICE_CAPABILITIES = BASE_URL + "/iocm/app/data/v1.1.0/deviceCapabilities";
//    public static final String SUBSCRIBE_NOTIFYCATION = BASE_URL + "/iocm/app/sub/v1.1.0/subscribe";
//
//
//    /*
//     * Signaling Delivery：
//     * 1. POST_ASYN_CMD
//     * 2. QUERY_DEVICE_CMD
//     * 3. UPDATE_ASYN_COMMAND
//     * 4. CREATE_DEVICECMD_CANCEL_TASK
//     * 5. QUERY_DEVICECMD_CANCEL_TASK
//     *
//     */
//    public static final String POST_ASYN_CMD = BASE_URL + "/iocm/app/cmd/v1.4.0/deviceCommands";
//    public static final String QUERY_DEVICE_CMD = BASE_URL + "/iocm/app/cmd/v1.4.0/deviceCommands";
//    public static final String UPDATE_ASYN_COMMAND = BASE_URL + "/iocm/app/cmd/v1.4.0/deviceCommands/%s";
//    public static final String CREATE_DEVICECMD_CANCEL_TASK = BASE_URL + "/iocm/app/cmd/v1.4.0/deviceCommandCancelTasks";
//    public static final String QUERY_DEVICECMD_CANCEL_TASK = BASE_URL + "/iocm/app/cmd/v1.4.0/deviceCommandCancelTasks";
//
//

}
