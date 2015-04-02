package sellit

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.web.JSONBuilder

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON

import org.springframework.security.core.context.SecurityContextHolder


/**
 * Created by mark.mazzitello on 3/28/2015.
 */
class BidController extends RestfulController<Bid> {
    static allowedMethods = [save: "POST"]
    static responseFormats = ['json']

    def springSecurityService

    BidController() {
        super(Bid);
    }

    @Secured(closure = {
        authentication.principal.username != "__grails.anonymous.user__"
    }, httpMethod = 'POST')
    def save() {
        def bid = createResource();
        def account = springSecurityService.currentUser as Account
        bid.biddingAccount = account
        bid.validate()
        if (bid.hasErrors()) {
            respond bid.errors, view:'edit'
            return
        }
        bid.save(flush: true)
        respond bid
    }

}
