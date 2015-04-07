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
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static responseFormats = ['json']

    def springSecurityService

    ReviewController() {
        super(Review);
    }

    @Secured(closure = {
        authentication.principal && authentication.principal.username != "__grails.anonymous.user__"
    }, httpMethod = 'POST')
    def save() {
        def account = springSecurityService.currentUser as Account
        def listing = Listing.findById(params.listingId)

        if (listing.endDate >= new Date() || listing.highBidAccount == null) {
            response.sendError(401, "Reviews are only permitted for completed listings with a winning bidder")
            return
        }

        if (account == listing.sellerAccount) {
            def existingReview = Review.find {listedItem == listing && wasSeller == false}
            if (existingReview == null) {
                //then this seller has not reviewed the buyer yet; save the review
                def review = createResource();
                review.listedItem = listing
                review.reviewedAccount = listing.highBidAccount
                review.wasSeller = false
                review.validate()
                if (review.hasErrors()) {
                    respond review.errors, view:'edit'
                    return
                }
                review.save(flush: true)
                respond review
            }
            else {
                response.sendError(401, "you have already reviewed the buyer for this listing")
                return
            }
        } else if (account == listing.highBidAccount) {
            def existingReview = Review.find {listedItem == listing && wasSeller == true}
            if (existingReview == null) {
                //then this buyer has not reviewed the seller yet; save the review
                def review = createResource();
                review.listedItem = listing
                review.reviewedAccount = listing.sellerAccount
                review.wasSeller = true
                review.validate()
                if (review.hasErrors()) {
                    respond review.errors, view:'edit'
                    return
                }
                review.save(flush: true)
                respond review
            }
            else {
                response.sendError(401, "you have already reviewed the seller for this listing")
                return
            }
        } else {
        response.sendError(401, 'only the seller or winning bidder may create a Review')
        return
        }
    }

    def update() {
        response.sendError(401, 'updating submitted Review is not permitted')
        return
    }

    def delete() {
        response.sendError(401, 'deleting Reviews is not permitted')
        return
    }

}
