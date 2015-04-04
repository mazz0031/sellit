package sellit

import geb.spock.GebSpec
import spock.lang.Stepwise

/**
 * Created by mark.mazzitello on 4/2/2015.
 */

@Stepwise
class ListingFunctionalSpec extends GebSpec {

    @Delegate static HttpUtils httpUtils = new HttpUtils()

    def setupSpec() {
        def remote = new SellitRemoteControl()
        remote {
            def account1 = new Account(username: 'seller1-test', email: 'test1@me.com', password: 'password1')
            account1.save(flush: true, failOnError: true)
            AccountRole.create(account1, Role.findByAuthority('USER_ROLE'), true)
            def account2 = new Account(username: 'seller2-test', email: 'test2@me.com', password: 'password1')
            account2.save(flush: true, failOnError: true)
            AccountRole.create(account2, Role.findByAuthority('USER_ROLE'), true)
            def listing = new Listing(deliverOption: "US Only", description: "test-listing", name: "test listing 2", listingDays: "8", startingPrice: "1000", startDate: "2015-04-16T01:04:59Z", sellerAccount: account2)
            listing.save(flush: true, failOnError: true)
            return 1
        }
    }

    def cleanupSpec() {
        def remote = new SellitRemoteControl()
        remote {
            Listing.findByName('test listing 2').delete()
            Account.findByUsername('seller1-test').delete()
            Account.findByUsername('seller2-test').delete()
        }
    }

    def "cannot create a listing when not logged in as a user (redirected to login)" () {
        when: 'REST call is made to Listing Save (POST) api'
        def resp = doJsonPost('api/listings', [deliverOption: "US Only", description: "test-listing", name: "test listing 1", listingDays: "8", startingPrice: "10", startDate: "2015-04-16T01:04:59Z"])

        then:
        resp.status == 302 //redirect to login page
    }

    def "create a new listing sets the logged in user as the seller" () {
        when: 'REST call is made to Listing Save (POST) api'
        def response = httpUtils.doFormPost('j_spring_security_check', [j_username: 'seller1-test', j_password: 'password1'])
        assert response.status == 302
        def resp = doJsonPost('api/listings', [deliverOption: "US Only", description: "test-listing", name: "test listing 1", listingDays: "8", startingPrice: "10", startDate: "2015-04-16T01:04:59Z"])

        then: 'Server returns a status of OK'
        resp.status == 200

        and: 'The saved Listing has the logged in account as the seller'
        resp.data.sellerAccount.id == Account.findByUsername('seller1-test').id
    }

    def "can update a listing when logged in as the seller account" () {
        when: 'REST call is made to Listing Update (PUT) api'
        def listingId = Listing.findByName('test listing 1').id
        def resp = doJsonPut('api/listings/'+listingId, [description: "different description"])

        then: 'Server returns a status of OK'
        resp.status == 200

        and: 'the listing description has been updated'
        resp.data.description == 'different description'
    }

    def "can not update a listing when logged in as user other than the seller account" () {
        when: 'REST call is made to Listing Update (PUT) api'
        def listingId = Listing.findByName('test listing 2').id
        def resp = doJsonPut('api/listings/'+listingId, [description: "different description"])

        then: 'Server returns a status of Denied'
        resp.status == 401
    }

    def "can delete a listing when logged in as seller account" () {
        when: 'REST call is made to Listing delete (DELETE) api'
        def listingId = Listing.findByName('test listing 1').id
        def resp = doJsonDelete('api/listings/'+listingId)

        then: 'Server returns a status of OK'
        resp.status == 200

        and: 'the listing is no longer in the database'
        !Listing.findByName('test listing 1')
    }

    def "cannot delete a listing when not logged in as the seller account" () {
        when: 'REST call is made to Listing delete (DELETE) api'
        def listingId = Listing.findByName('test listing 2').id
        def resp = doJsonDelete('api/listings/'+listingId)

        then: 'Server returns a status of Denied'
        resp.status == 401

        and: 'the listing is still in the database'
        Listing.findByName('test listing 2')
    }
}
