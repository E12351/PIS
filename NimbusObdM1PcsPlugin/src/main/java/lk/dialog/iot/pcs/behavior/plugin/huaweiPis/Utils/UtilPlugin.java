package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Utils;

import java.util.HashMap;

public class UtilPlugin {
    public int responceCode(HashMap responce){
        String state = (String) responce.get("state");
        String [] state_code=state.split(" ");
//        logger.info("state code : "+state_code[1]);

        return Integer.parseInt(state_code[1]);
    }
}
