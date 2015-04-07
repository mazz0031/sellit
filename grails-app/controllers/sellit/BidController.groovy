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
    static allowedMethods = [show: "GET", save: "POST", update: "PUT", delete: "DELETE"]
    static responseFormats = ['json']

    def springSecurityService

    BidController() {
        super(Bid);
    }

    def index() {
        if (!params.listingId || !params.listingId.isInteger()) {
            response.sendError(405, 'Bid list can only be viewed from within a Listing; please provide listingId parameter')
            return
        }
        def listing = Listing.findById(params.listingId)
        if (listing == null) {
            response.sendError(404, 'listing not found')
            return
        }
        respond Bid.where {listedItem == listing}.toList()
    }

    def show() {
        def bid = Bid.findById(params.id)
        if (bid == null) {
            response.sendError(404)
            return
        }
        if (bid.biddingAccount != springSecurityService.currentUser as Account) {
            response.sendError(401, 'only the bidder may view the details of a bid')
            return
        }
        return bid
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
        def listing = bid.listedItem
        listing.highBidAccount = account
        listing.currentHighBid = bid.bidAmount
        listing.save(flush: true)
        respond bid
    }

    def update() {
        response.sendError(401, 'modifying a bid after it is submitted is not permitted')
    }

    def delete() {
        response.sendError(401, 'deleting bids is not permitted')
    }

}
