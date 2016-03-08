package com.yonyou.mcloud.monitor;

import Ice.Communicator;
import IceGrid.*;

import java.util.Arrays;

/**
 * Created by duduchao on 16/3/8
 */
public class Monitor {

    public static void main(String[] args) throws PermissionDeniedException {
        Communicator ic = Ice.Util.initialize(new String[]{"--Ice.Default.Locator=IceGrid/Locator:tcp -h 10.10.5.224 -p 4061:tcp -h 10.10.5.225 -p 4061"});

        RegistryPrx r = RegistryPrxHelper.checkedCast(ic.stringToProxy("IceGrid/Registry"));

        AdminSessionPrx sessionPrx = r.createAdminSession("test","test");

        AdminPrx admin = sessionPrx.getAdmin();

        System.out.println(admin.getObjectInfosByType(null).length);

        System.out.println(Arrays.toString(admin.getAllAdapterIds()));

        sessionPrx.destroy();

        ic.shutdown();
        ic.destroy();
    }

}
