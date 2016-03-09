package com.yonyou.mcloud.monitor;

import Ice.Communicator;
import IceGrid.*;
import com.yonyou.mcloud.monitor.observer.*;

/**
 * Created by duduchao on 16/3/8
 */
public class Monitor {

    public void start() throws PermissionDeniedException, ObserverAlreadyRegisteredException {

        Communicator ic = Ice.Util.initialize(new String[]{"--Ice.Default.Locator=IceGrid/Locator:tcp -h 10.10.5.224 -p 4061:tcp -h 10.10.5.225 -p 4061"});

        RegistryPrx r = RegistryPrxHelper.checkedCast(ic.stringToProxy("IceGrid/Registry"));

        AdminSessionPrx sessionPrx = r.createAdminSession("test", "test");

        Ice.ObjectAdapter _adapter = ic.createObjectAdapter(r.ice_getAdapterId());

        _adapter.activate();

        sessionPrx.ice_getConnection().setAdapter(_adapter);

        String category = "observer";

        _applicationObserverIdentity.name = "application-" + java.util.UUID.randomUUID().toString();
        _applicationObserverIdentity.category = category;
        _adapterObserverIdentity.name = "adapter-" + java.util.UUID.randomUUID().toString();
        _adapterObserverIdentity.category = category;
        _objectObserverIdentity.name = "object-" + java.util.UUID.randomUUID().toString();
        _objectObserverIdentity.category = category;
        _registryObserverIdentity.name = "registry-" + java.util.UUID.randomUUID().toString();
        _registryObserverIdentity.category = category;
        _nodeObserverIdentity.name = "node-" + java.util.UUID.randomUUID().toString();
        _nodeObserverIdentity.category = category;

        _adapter.add(
                new ApplicationObserverI() {
                }, _applicationObserverIdentity);


        _adapter.add(
                new AdapterObserverI(), _adapterObserverIdentity);


        _adapter.add(
                new ObjectObserverI(), _objectObserverIdentity);

        _adapter.add(
                new RegistryObserverI(), _registryObserverIdentity);


        _adapter.add(
                new NodeObserverI(), _nodeObserverIdentity);

        sessionPrx.setObserversByIdentity(
                _registryObserverIdentity,
                _nodeObserverIdentity,
                _applicationObserverIdentity,
                _adapterObserverIdentity,
                _objectObserverIdentity);

        ic.waitForShutdown();

//        AdminPrx admin = sessionPrx.getAdmin();
//
//        System.out.println(admin.getObjectInfosByType(null).length);
//
//        System.out.println(Arrays.toString(admin.getAllAdapterIds()));
//
//        sessionPrx.destroy();
//
//        ic.shutdown();
//        ic.destroy();
    }

    public static void main(String[] args) throws PermissionDeniedException, ObserverAlreadyRegisteredException {
        Monitor monitor = new Monitor();
        monitor.start();
    }


    private Ice.Identity _applicationObserverIdentity = new Ice.Identity();
    private Ice.Identity _adapterObserverIdentity = new Ice.Identity();
    private Ice.Identity _objectObserverIdentity = new Ice.Identity();
    private Ice.Identity _registryObserverIdentity = new Ice.Identity();
    private Ice.Identity _nodeObserverIdentity = new Ice.Identity();

}
