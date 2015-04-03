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

    //ToDo: figure out how to get the seller account username from the listingID at the end of the URL to compare here
//    @Secured(closure = {
//        def listingId = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)
//        def account = Listing.findById(listingId.toInteger()).sellerAccount
//        authentication.principal.username == account.username
//    }, httpMethod = 'PUT')
    def update() {
        def listing = Listing.findById(params.id)
        if (listing.sellerAccount != springSecurityService.currentUser as Account) {
            response.sendError(401, 'only the seller is permitted to update a listing')
            return
        }
        listing.properties = getObjectToBind()
        listing.validate()
        if (listing.hasErrors()) {
            respond listing.errors, view:'edit'
            return
        }
        listing.save(flush: true)
        respond listing
    }

    //ToDo: figure out how to get the seller account username from the listingID at the end of the URL to compare here
//    @Secured(closure = {
//        def listingId = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)
//        def account = Listing.findById(listingId.toInteger()).sellerAccount
//        authentication.principal.username == account.username
//    }, httpMethod = 'DELETE')
    def delete() {
        def listing = Listing.findById(params.id)
        if (listing == null)
        {
            return
        }
        if (listing.sellerAccount != springSecurityService.currentUser as Account) {
            response.sendError(401, 'only the seller is permitted to delete a listing')
            return
        }
        //ToDo: figure out why the f**k delete doesn't work here
        listing.delete()
        respond Listing.list()
    }

}
