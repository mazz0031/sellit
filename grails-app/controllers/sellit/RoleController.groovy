package sellit

import grails.rest.RestfulController

/**
 * Created by mark.mazzitello on 3/13/2015.
 */
class RoleController extends RestfulController<Role> {

    static responseFormats = ['json', 'xml']

    RoleController() {
        super(Role)
    }


}
