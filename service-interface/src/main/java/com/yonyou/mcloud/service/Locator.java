package com.yonyou.mcloud.service;

import Ice.ObjectPrx;
import com.yonyou.mcloud.service.common.IdGeneratorPrx;
import com.yonyou.mcloud.service.common.IdGeneratorPrxHelper;

/**
 * Created by hubo on 16/1/14
 */
public class Locator {

    public <T extends ObjectPrx> T lookup(Class<T> cls) {


        return null;
    }


    public static void main(String[] args) {
        Ice.Communicator ic  = null;
        try{
            ic = Ice.Util.initialize(new String[]{"--Ice.Default.Locator=IceGrid/Locator:tcp -h 192.168.20.17 -p 4061"});

            Ice.ObjectPrx basePrx = ic.stringToProxy("IdGeneratorService@IdGeneratorServiceAdapter");
            IdGeneratorPrx idGeneratorPrx = IdGeneratorPrxHelper.checkedCast(basePrx);

            String id = idGeneratorPrx.nextId();
            System.out.println("id is :" + id);


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(ic != null){
                ic.shutdown();
                ic.waitForShutdown();
                ic.destroy();
            }
        }
    }
}
