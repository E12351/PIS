/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.dialog.iot.pcs.behavior.plugin.huaweiPis.factory;

import lk.dialog.iot.pcs.behavior.plugin.huaweiPis.Parameter.AuthHandle;

/**
 *
 * @author Dinuka_08966
 */
public class Factory {

    private static AuthHandle authHandle;

    public static AuthHandle getAuthHandale() {
        if (authHandle == null) {
            authHandle = new AuthHandle();
        }
        return authHandle;
    }
}
