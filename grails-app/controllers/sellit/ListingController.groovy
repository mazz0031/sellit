package sellit

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.web.JSONBuilder
import groovy.time.TimeCategory

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON

import org.springframework.security.core.context.SecurityContextHolder


/**
 * Created by mark.mazzitello on 3/28/2015.
 */
class ListingController extends RestfulController<Listing> {
    static allowedMethods = [index: "GET", update: "PUT", save: "POST", delete: "DELETE"]
    static responseFormats = ['json']

    def springSecurityService

    ListingController() {
        super(Listing);
    }

    def index() {
        def results
        if (params.showCompleted == 'true') {
            results = Listing.findAll().toList()
        } else {
            def now = new Date()
            results = Listing.where { endDate >= now }.toList()
        }
        respond results
    }

    def show() {
        def listing = Listing.findById(params.id)
        if (listing == null) {
            response.sendError(404)
            return
        }
        def now = new Date()
        if (listing.endDate < now)
        {
            listing.completed = true;
        }
        respond listing
    }

    @Secured(closure = {
        authentication.principal && authentication.principal.username != "__grails.anonymous.user__"
    }, httpMethod = 'POST')
    def save() {
        def listing = createResource();
        def account = springSecurityService.currentUser as Account
        listing.sellerAccount = account

        //had to add this code because after a whole day of trying I still could not get the javascript/html date to parse into a grails date
        if (listing.startDate == null) {
            listing.startDate = new Date()
        }

        use(TimeCategory) {
            listing.endDate = listing.startDate + listing.listingDays.days
        }
        listing.validate()
        if (listing.hasErrors()) {
            respond listing.errors, view:'edit'
            return
        }
        listing.save(flush: true)
        respond listing
    }

    def update() {
        def listing = Listing.findById(params.id)
        if (listing == null) {
            response.sendError(404)
            return
        }
        if (listing.sellerAccount != springSecurityService.currentUser as Account) {
            response.sendError(401, 'only the seller is permitted to update a listing')
            return
        }
        listing.properties = getObjectToBind()
        use(TimeCategory) {
            listing.endDate = listing.startDate + listing.listingDays.days
        }
        listing.validate()
        if (listing.hasErrors()) {
            respond listing.errors, view:'edit'
            return
        }
        listing.save(flush: true)
        respond listing
    }

    def delete() {
        def listing = Listing.findById(params.id)
        if (listing == null) {
            response.sendError(404)
            return
        }
        if (listing.sellerAccount != springSecurityService.currentUser as Account) {
            response.sendError(401, 'only the seller is permitted to delete a listing')
            return
        }
        Bid.where {listedItem == listing}.toList().each() {it.delete(flush: true)}
        Review.where {listedItem == listing}.toList().each() {it.delete(flush: true)}
        listing.delete(flush: true)

        response.sendError(200)
        return
    }
}
