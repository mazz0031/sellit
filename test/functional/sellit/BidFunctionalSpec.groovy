package sellit

import geb.spock.GebSpec
import spock.lang.Stepwise

/**
 * Created by mark.mazzitello on 4/2/2015.
 */

@Stepwise
class BidFunctionalSpec extends GebSpec {

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
            def listing = new Listing(deliverOption: "US Only", description: "test-listing", name: "test listing 2", listingDays: "8", startingPrice: "10", startDate: "2015-04-16T01:04:59Z", sellerAccount: account1)
            listing.save(flush: true, failOnError: true)
            return 1
        }
    }

    def cleanupSpec() {
        def remote = new SellitRemoteControl()
        remote {
            def listing = Listing.findByName('test listing 2')
            def bids = Bid.where {listedItem == listing}.toList()
            bids.each() {it.delete(flush: true)}
            listing.delete(flush: true)
            Account.findByUsername('seller1-test').delete()
            Account.findByUsername('bidder1-test').delete()
        }
    }

    def "cannot create a bid when not logged in as a user (redirected to login)" () {
        when: 'REST call is made to Bid Save (POST) api'
        def listingId = Listing.findByName('test listing 2').id
        def resp = doJsonPost('api/bids', [bidAmount: "20", listedItem: {id: listingId}])

        then:
        resp.status == 302 //redirect to login page
    }

    def "create a new bid sets the logged in user as the seller" () {
        when: 'REST call is made to Bid Save (POST) api'
        def response = httpUtils.doFormPost('j_spring_security_check', [j_username: 'bidder1-test', j_password: 'password1'])
        assert response.status == 302
        def listingId = Listing.findByName('test listing 2').id
        def resp = doJsonPost('api/bids', [bidAmount: "20", listedItem: [id: listingId]])

        then: 'Server returns a status of OK'
        resp.status == 200

        and: 'The saved Bid has the logged in account as the bidder'
        resp.data.biddingAccount.id == Account.findByUsername('bidder1-test').id
    }

    def "get list of bids for a Listing" () {
        when: 'REST call is made to Bid root (index) api'
        def listingId = Listing.findByName('test listing 2').id
        def resp = doGet('api/bids?listingId=' + listingId)

        then: 'Server returns a status of OK'
        resp.status == 200

        and: 'List size is one'
        resp.data.size == 1

        and: 'Listed item is the one passed in the parameter'
        resp.data[0].listedItem.id == listingId
    }
}
