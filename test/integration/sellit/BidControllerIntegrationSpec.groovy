package sellit

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(BidController)
class BidControllerIntegrationSpec extends Specification {

    def address
    def account
    def listing

    def setup() {
        address = new Address(addressLine1: "123 Main Street", city: "Watts", stateAbbr: "CA", postalCode: "99999")
        address.save(failOnError: true)
        account = new Account(name: "Fred Sanford", password: "aaaa1234", email: "fred@junk.com", address: address)
        account.save(failOnError: true)
        listing = new Listing(name: "junk", description: "first piece of junk", startingPrice: 100,
                startDate: new Date(), listingDays: 5, deliverOption: "US Only", sellerAccount: account)
        listing.save(failOnError: true)
    }

    def 'saving Bid persists new Bid in database'() {
        setup:
        def bid = new Bid(listedItem: listing, bidAmount: 102, biddingAccount: account)

        when:
        bid.save(failOnError: true)

        then:
        bid.errors.errorCount == 0
        bid.id
        Bid.get(bid.id).bidAmount == 102
    }

    def 'saving bid not more than .50 greater than highest bid fails validation'() {
        setup:
        def bid = new Bid(listedItem: listing, bidAmount: 110, biddingAccount: account)
        controller.save(bid)
        def badBid = new Bid(listedItem: listing, bidAmount: 110.10, biddingAccount: account)

        when:
        //badBid.save()
        controller.save(badBid)

        then:
        badBid.hasErrors()
    }

    def 'creating Bid with missing parameters fails validation'() {
        setup:
        def bid = new Bid()

        when:
        def result = bid.save()

        then:
        !result
        bid.hasErrors()
    }
}
