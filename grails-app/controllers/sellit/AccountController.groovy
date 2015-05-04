package sellit

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.web.JSONBuilder
import groovy.time.TimeCategory

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON


/**
 * Created by mark.mazzitello on 3/28/2015.
 */
class AccountController extends RestfulController<Account> {
    static allowedMethods = [update: "PUT", show: "GET", save: "POST", delete: "DELETE"]
    static responseFormats = ['json']

    def springSecurityService

    AccountController() {
        super(Account);
    }

    @Secured(['ROLE_USER'])
    def index() {
        def results = Account.list()
        respond results
    }

    @Secured(closure = {
        authentication.principal && authentication.principal.username != "__grails.anonymous.user__"
    }, httpMethod = 'GET')
    def show() {
        def account = Account.findById(params.id)
        if (account == null) {
            response.sendError(404)
            return
        }
        if (account != springSecurityService.currentUser as Account) {
            response.sendError(403, 'you must be logged in as your own account in order to view details')
            return
        }
        respond account
    }

    @Secured(closure = {
        authentication.principal && authentication.principal.username != "__grails.anonymous.user__"
    }, httpMethod = 'PUT')
    def update() {
        def account = Account.findById(params.id)
        if (account == null) {
            response.sendError(404)
            return
        }
        if (account != springSecurityService.currentUser as Account) {
            response.sendError(403, 'you must be logged in as your own account in order to edit')
            return
        }
        account.properties = getObjectToBind()
        account.validate()
        if (account.hasErrors()) {
            respond account.errors, view:'edit'
            return
        }
        account.save(flush: true)
        respond account
    }

    def save() {
        def account = createResource();
        account.validate()
        if (account.hasErrors()) {
            respond account.errors, view:'edit'
            return
        }
        account.save(flush: true)
        respond account
    }

    def delete() {
        response.sendError(401, 'deleting accounts is not permitted')
        return
    }
}
