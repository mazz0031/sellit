package sellit

import grails.util.BuildSettingsHolder

/**
 * Created by mark.mazzitello on 3/27/2015.
 */
import grails.util.Holders
import groovyx.remote.client.RemoteControl
import groovyx.remote.transport.http.HttpTransport

// This class is a simple re-implementation of the RemoteControl class that helps get around the issue
// in the default implementation of running functional tests within IntelliJ when the server is already
// running.  In this case grails doesn't know what the servlet context is, so errors will result when
// trying to connect to url http://localhost8080:null.  The simple fix is to hard code this context when it
// happens to be /sellit/
class SellitRemoteControl extends RemoteControl {
    static public final RECEIVER_PATH = "grails-remote-control"

    SellitRemoteControl() {
        super(new HttpTransport(getFunctionalTestReceiverAddress(), Thread.currentThread().contextClassLoader), Thread.currentThread().contextClassLoader)
    }

    private static String getFunctionalTestReceiverAddress() {
        def base = getFunctionalTestBaseUrl()
        if (!base) {
            throw new IllegalStateException("Cannot get receiver address for functional testing as functional test base URL is not set. Are you calling this from a functional test?")
        }

        base.endsWith("/") ? base + RECEIVER_PATH : base + "/" + RECEIVER_PATH
    }

    static String getFunctionalTestBaseUrl() {
        BuildSettingsHolder.settings?.functionalTestBaseUrl.replace('null', '/sellit/') ?: 'http://localhost:8080/sellit/'
    }
}