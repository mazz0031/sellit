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
class ListingController extends RestfulController<Listing> {
    static allowedMethods = [update: "PUT", save: "POST", delete: "DELETE"]
    static responseFormats = ['json']

    def springSecurityService

    ListingController() {
        super(Listing);
    }

    @Secured(closure = {
        authentication.principal.username != "__grails.anonymous.user__"
    }, httpMethod = 'POST')
    def save() {
        def listing = createResource();
        def account = springSecurityService.currentUser as Account
        listing.sellerAccount = account
        listing.validate()
        if (listing.hasErrors()) {
            respond listing.errors, view:'edit'
            return
        }
        listing.save(flush: true)
        respond listing
    }

    @Secured(closure = {
        def listingId = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)
        def account = Listing.findById(listingId.toInteger()).sellerAccount
        authentication.principal.username == account.username
    }, httpMethod = 'PUT')
    def update() {
        def listing = Listing.findById(params.id)
        listing.properties = getObjectToBind()
        listing.validate()
        if (listing.hasErrors()) {
            respond listing.errors, view:'edit'
            return
        }
        listing.save(flush: true)
        respond listing
    }

    @Secured(closure = {
        def listingId = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)
        def account = Listing.findById(listingId.toInteger()).sellerAccount
        authentication.principal.username == account.username
    }, httpMethod = 'DELETE')
    def delete() {
        def listing = Listing.findById(params.id)
        if (listing == null)
        {
            return
        }
        listing.delete(flish: true)
        redirect action:"index", method:"GET"
    }

}
