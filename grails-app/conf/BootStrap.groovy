import sellit.Account
import sellit.Address
import sellit.Bid
import sellit.Listing

class BootStrap {

    def init = { servletContext ->
        environments {
            development {
                createSampleData()
            }
        }
    }

    def destroy = {
    }

    void createSampleData() {
        def address1 = new Address(addressLine1: "6772 East Fish Lake Road", city: "Maple Grove", stateAbbr: "MN", postalCode: "55369")
        address1.save(failOnError: true)
        def address2 = new Address(addressLine1: "1810 Jackson Street", city: "St Paul", stateAbbr: "MN", postalCode: "55117")
        address2.save(failOnError: true)

        def account1 = new Account(name: "Mark", email: "mtmazz@hotmail.com", password: "xxxxx123", address: address1)
        account1.save(failOnError: true)
        def account2 = new Account(name: "John", email: "john@hotmail.com", password: "xxxxx123", address: address2)
        account2.save(failOnError: true)

        def listing1 = new Listing(name: "Television", description: "19-inch color CRT", startDate: new Date(),
                listingDays: 6, startingPrice: 52.00, deliverOption: "US Only", sellerAccount: account1)
        listing1.save(failOnError: true)
        def listing2 = new Listing(name: "Radio", description: "boombox", startDate: new Date(year: 2015, month: 02, date: 18),
                listingDays: 5, startingPrice: 10.00, deliverOption: "US Only", sellerAccount: account2)
        listing2.save(failOnError: true)

        def bid1 = new Bid(listedItem: listing1, biddingAccount: account2, bidAmount: 53.25)
        bid1.save(failOnError: true)
        def bid2 = new Bid(listedItem: listing1, biddingAccount: account1, bidAmount: 55)
        bid2.save(failOnError: true)
        def bid3 = new Bid(listedItem: listing1, biddingAccount: account2, bidAmount: 57.43)
        bid3.save(failOnError: true)
        def bid4 = new Bid(listedItem: listing1, biddingAccount: account1, bidAmount: 60.90)
        bid4.save(failOnError: true)


    }










}
