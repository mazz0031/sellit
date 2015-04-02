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
    static allowedMethods = [update: "PUT", show: "GET", delete: "DELETE"]
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
        def username = params.id
        respond Account.findByUsername(username)
    }

    @Secured(closure = {
        def username = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)
        authentication.principal.username == username
    }, httpMethod = 'PUT')
    def update() {
        def username = params.id
        def account = Account.findByUsername(username)
        account.properties = getObjectToBind()
        account.validate()
        if (account.hasErrors()) {
            respond account.errors, view:'edit'
            return
        }
        account.save(flush: true)
        respond account
    }

    @Secured(closure = {
        def username = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)
        authentication.principal.username == username
    }, httpMethod = 'DELETE')
    def delete() {
        def username = params.id
        def account = Account.findByUsername(username)
        if (account == null)
        {
            return
        }
        account.delete(flish: true)
        redirect action:"index", method:"GET"
    }


}
