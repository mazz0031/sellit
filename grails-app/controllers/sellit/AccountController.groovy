package sellit

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.web.JSONBuilder

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON

/**
 * Created by mark.mazzitello on 3/28/2015.
 */
class AccountController extends RestfulController<Account> {
    static responseFormats = ['json']

    def springSecurityService

    AccountController() {
        super(Account);
    }

    @Secured(closure = {
        def username = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)
        authentication.principal.username == username
    }, httpMethod = 'GET')
    def show() {
        [springSecurityService.currentUser as Account]
    }


}
