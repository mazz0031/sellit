package sellit



import grails.test.mixin.*
import spock.lang.*

@TestFor(BidController)
@Mock([Bid, Listing])
class BidControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        def address = new Address(addressLine1: "123 Main Street", city: "Nowhere", stateAbbr: "AK", postalCode: "12345")
        def account = new Account(name: "Joe Rockhead", email: "joe@bedrockfd.gov", password: "xxxx1234", address: address)
        def listing = new Listing(name: "Truck", description: "1995 Ford F-350", startDate: new Date(year: 2015, month: 02, date: 28), listingDays: 7, startingPrice: 2000, deliverOption: "US Only", sellerAccount: account)
        params["bidAmount"] = 2100
        params["biddingAccount"] = account
        params["listedItem"] = listing
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.bidInstanceList
            model.bidInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.bidInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def bid = new Bid()
            bid.validate()
            controller.save(bid)

        then:"The create view is rendered again with the correct model"
            model.bidInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            bid = new Bid(params)

            controller.save(bid)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/bid/show/1'
            controller.flash.message != null
            Bid.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def bid = new Bid(params)
            controller.show(bid)

        then:"A model is populated containing the domain instance"
            model.bidInstance == bid
    }
}
