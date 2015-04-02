package sellit

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

/**
 * Created by mark.mazzitello on 3/13/2015.
 */
class RoleController extends RestfulController<Role> {
    static allowedMethods = [save: "POST"]
    static responseFormats = ['json', 'xml']

    RoleController() {
        super(Role)
    }

    @Secured(closure = {
        authentication.principal.username == 'Admin'
    }, httpMethod = 'POST')
    def save() {
        def role = createResource();
        role.save(flush: true)
        respond role
    }
}
