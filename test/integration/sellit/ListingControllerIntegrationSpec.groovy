package sellit

import spock.lang.Specification

class ListingControllerIntegrationSpec extends Specification {

    def address
    def account

    def setup() {
        address = new Address(addressLine1: "123 Main Street", city: "Watts", stateAbbr: "CA", postalCode: "99999")
        address.save(failOnError: true)
        account = new Account(name: "Fred Sanford", password: "aaaa1234", email: "fred@junk.com", address: address)
        account.save(failOnError: true)
    }

    def 'saving Listing persists new Listing in database'() {
        setup:
        def listing = new Listing(name: "junk", description: "first piece of junk", startingPrice: 100,
                startDate: new Date(), listingDays: 5, deliverOption: "US Only", sellerAccount: account)

        when:
        listing.save(failOnError: true)

        then:
        listing.errors.errorCount == 0
        listing.id
        Listing.get(listing.id).name == 'junk'
    }

    def 'creating Listing with missing parameters fails validation'() {
        setup:
        def listing = new Listing()

        when:
        def result = listing.save()

        then:
        !result
        listing.hasErrors()
    }

    def 'deleting Listing removes it from the database'() {
        setup:
        def listing = new Listing(name: "more junk", description: "second piece of junk", startingPrice: 100,
                startDate: new Date(), listingDays: 5, deliverOption: "US Only", sellerAccount: account)
        listing.save(failOnError: true)

        when:
        listing.delete()

        then:
        !Listing.exists(listing.id)
    }
}

