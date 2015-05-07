package sellit

import geb.spock.GebSpec
import spock.lang.Stepwise

/**
 * Created by mark.mazzitello on 4/2/2015.
 */

@Stepwise
class ReviewFunctionalSpec extends GebSpec {

    @Delegate static HttpUtils httpUtils = new HttpUtils()

    def setupSpec() {
        def remote = new SellitRemoteControl()
        remote {
            def account1 = new Account(username: 'seller1-test', email: 'test1@me.com', password: 'password1')
            account1.save(flush: true, failOnError: true)
            AccountRole.create(account1, Role.findByAuthority('USER_ROLE'), true)
            def account2 = new Account(username: 'bidder1-test', email: 'test2@me.com', password: 'password1')
            account2.save(flush: true, failOnError: true)
            AccountRole.create(account2, Role.findByAuthority('USER_ROLE'), true)
            def listing = new Listing(deliverOption: "US Only", description: "test-listing", name: "test listing 2", listingDays: "8",
                    startingPrice: "10", startDate: "2015-03-04T01:04:59Z", endDate: "2015-03-14T01:04:59Z", sellerAccount: account1, currentHighBid: "25", highBidAccount: account2)
            listing.save(flush: true, failOnError: true)
            def bid = new Bid(listedItem: listing, biddingAccount: account2, bidAmount: "25")
            bid.save(flush: true, failOnError: true)
            return 1
        }
    }

    def cleanupSpec() {
        def remote = new SellitRemoteControl()
        remote {
            def listing = Listing.findByName('test listing 2')
            def reviews = Review.where {listedItem == listing}.toList()
            reviews.each() {it.delete(flush: true)}
            def bids = Bid.where {listedItem == listing}.toList()
            bids.each() {it.delete(flush: true)}
            listing.delete(flush: true)
            Account.findByUsername('seller1-test').delete()
            Account.findByUsername('bidder1-test').delete()
        }
    }

    def "cannot access create review endpoint if not logged in" () {
        when: 'REST call is made to Review Save (POST) api'
        def listing = Listing.findByName('test listing 2')
        def resp = doJsonPost('api/reviews', [listedItem: listing.id, thumbsUp: true, reviewDescription: 'description'])

        then:
        resp.status == 302 //redirect to login page
    }

    def "can create a review for a completed listing when logged in as the high bidder" () {
        when: 'REST call is made to Review Save (POST) api'
        def response = httpUtils.doFormPost('j_spring_security_check', [j_username: 'bidder1-test', j_password: 'password1'])
        assert response.status == 302
        def listing = Listing.findByName('test listing 2')
        def resp = doJsonPost('api/reviews', [listedItem: listing.id, thumbsUp: true, reviewDescription: 'description'])

        then: 'Server returns a status of OK'
        resp.status == 200

        and: 'The reviewed account is the seller for the Listing'
        def sellerAccount = Listing.findByName('test listing 2').sellerAccount
        resp.data.reviewedAccount.id == sellerAccount.id
    }

    def "can create a review for a completed listing when logged in as the seller" () {
        when: 'REST call is made to Review Save (POST) api'
        doJsonPut('logout', null)
        def response = httpUtils.doFormPost('j_spring_security_check', [j_username: 'seller1-test', j_password: 'password1'])
        assert response.status == 302
        def listing = Listing.findByName('test listing 2')
        def resp = doJsonPost('api/reviews', [listedItem: listing.id, thumbsUp: true, reviewDescription: 'description'])

        then: 'Server returns a status of OK'
        resp.status == 200

        and: 'The reviewed account is the high bidder for the Listing'
        def bidderAccount = Listing.findByName('test listing 2').highBidAccount
        resp.data.reviewedAccount.id == bidderAccount.id
    }

}
