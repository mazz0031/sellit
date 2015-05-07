import sellit.*

class BootStrap {

    def init = { servletContext ->
        environments {
            development {
                setupRoles()
                createAdminUser()
                createSampleData()
            }
            test {
                setupRoles()
                //createAdminUser()
            }
        }
    }

    def destroy = {
    }

    void setupRoles() {
        ['ROLE_ADMIN', 'ROLE_USER'].each {
            if (!Role.findByAuthority(it)) {
                new Role(authority: it).save(flush: true, failOnError: true)
            }
        }
    }

    void createAdminUser() {
        def account = new Account(username: "Admin", email: "admin@sellit.com", password: "password1")
        account.save(flush: true, failOnError: true)
        Role.list().each { Role role ->
            new AccountRole(account: account, role: role).save(failOnError: true)
        }
    }

    void createSampleData() {
        def account1 = new Account(username: "Mark", email: "mark@hotmail.com", password: "password1")
        account1.save(flush: true, failOnError: true)
        def account2 = new Account(username: "John", email: "john@hotmail.com", password: "password1")
        account2.save(failOnError: true)
        Role role = Role.findByAuthority('ROLE_USER')
        new AccountRole(account: account1, role: role).save(failOnError: true)
        new AccountRole(account: account2, role: role).save(failOnError: true)

        def listing1 = new Listing(name: "Television", description: "19-inch color CRT", startDate: new Date().copyWith(year: 2015, month: Calendar.MARCH, dayOfMonth: 10),
                listingDays: 6, endDate: new Date().copyWith(year: 2015, month: Calendar.MARCH, dayOfMonth: 20), startingPrice: 52.00, deliverOption: "US Only", sellerAccount: account1, highBidAccount: account2, currentHighBid: "60.90")
        listing1.save(failOnError: true)
        def listing2 = new Listing(name: "Radio", description: "boombox", startDate: new Date().copyWith(year: 2015, month: Calendar.MAY, dayOfMonth: 5),
                listingDays: 10, endDate: new Date().copyWith(year: 2015, month: Calendar.MAY, dayOfMonth: 15), startingPrice: 10.00, deliverOption: "US Only", sellerAccount: account2)
        listing2.save(failOnError: true)

        def bid1 = new Bid(listedItem: listing1, biddingAccount: account2, bidAmount: 53.25)
        bid1.save(failOnError: true)
        def bid2 = new Bid(listedItem: listing1, biddingAccount: account2, bidAmount: 55)
        bid2.save(failOnError: true)
        def bid3 = new Bid(listedItem: listing1, biddingAccount: account2, bidAmount: 57.43)
        bid3.save(failOnError: true)
        def bid4 = new Bid(listedItem: listing1, biddingAccount: account2, bidAmount: 60.90)
        bid4.save(failOnError: true)

        def review1 = new Review(listedItem: listing2, reviewedAccount: account2, wasSeller: true, thumbsUp: true, reviewDescription: "Good Seller")
        review1.save(failOnError: true)
        def review2 = new Review(listedItem: listing2, reviewedAccount: account1, wasSeller: false, thumbsUp: true, reviewDescription: "Good Buyer")
        review2.save(failOnError: true)
    }
}
