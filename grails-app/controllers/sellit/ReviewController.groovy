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
class ReviewController extends RestfulController<Review> {
    static allowedMethods = [save: "POST"]
    static responseFormats = ['json']

    def springSecurityService

    ReviewController() {
        super(Review);
    }

    @Secured(closure = {
        def listingId = request.requestURI.substring(request.requestURI.lastIndexOf('/')+1)
        def listing = Listing.findById(listingId)
        (authentication.principal.username == listing.sellerAccount || authentication.principal.username == listing.highBidAccount)
    }, httpMethod = 'POST')
    def save() {
        def review = createResource();
        def account = springSecurityService.currentUser as Account
        def listing = Listing.findById(params.id)

        if (account == listing.sellerAccount) {
            def existingReview = Review.findByListedItem(listing).where {wasSeller == false}
            if (!existingReview) {
                //then this seller has not reviewed the buyer yet; save the review
                review.validate()
                if (review.hasErrors()) {
                    respond review.errors, view:'edit'
                }
                review.save(flush: true)
                respond review
            }
            else {
                respond "you have already reviewed the buyer for this listing", view:'edit'
            }
        }

        if (account == listing.highBidAccount) {
            def existingReview = Review.findByListedItem(listing).where {wasSeller == true}
            if (!existingReview) {
                //then this buyer has not reviewed the seller yet; save the review
                review.validate()
                if (review.hasErrors()) {
                    respond review.errors, view:'edit'
                }
                review.save(flush: true)
                respond review
            }
            else {
                respond "you have already reviewed the seller for this listing", view:'edit'
            }
        }
    }

}
