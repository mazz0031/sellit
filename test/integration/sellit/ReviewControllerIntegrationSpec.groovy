package sellit

import spock.lang.Specification

class ReviewControllerIntegrationSpec extends Specification {

    def address
    def account
    def listing

    def setup() {
        address = new Address(addressLine1: "123 Main Street", city: "Springfield", stateAbbr: "IL", postalCode: "99999")
        address.save(failOnError: true)
        account = new Account(name: "Homer Simpson", password: "aaaa1234", email: "homer@springfieldnuclear.com", address: address)
        account.save(failOnError: true)
        listing = new Listing(name: "uranium", description: "spent U235 rod", startingPrice: 100,
                startDate: new Date(), listingDays: 5, deliverOption: "US Only", sellerAccount: account)
        listing.save(failOnError: true)
    }

    def 'saving Review persists new Review in database'() {
        setup:
        def review = new Review(listedItem: listing, reviewedAccount: account, wasSeller: true, thumbsUp: true, reviewDescription: "hot seller")

        when:
        review.save(failOnError: true)

        then:
        review.errors.errorCount == 0
        review.id
        Review.get(review.id).reviewDescription == 'hot seller'
    }

    def 'creating Review with missing parameters fails validation'() {
        setup:
        def review = new Review()

        when:
        def result = review.save()

        then:
        !result
        review.hasErrors()
    }



}
