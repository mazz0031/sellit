package sellit

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import sellit.pages.LoginPage
import spock.lang.Specification
import spock.lang.Stepwise


/**
 * Created by mark.mazzitello on 2/21/2015.
 */

@Stepwise
class AccountFunctionalSpec extends GebSpec {

    @Delegate static FunctionalTestUtils utils = new FunctionalTestUtils()

    def setupSpec() {
        def remote = new SellitRemoteControl()
        remote {
            def address = new Address(addressLine1: "123 Main Street", city: "Bedrock", stateAbbr: "CA", postalCode: "12345")
            address.save(flush: true, failOnError: true)
            def account = new Account(username: 'account-test', email: 'test@me.com', password: 'password1', address: address)
            account.save(flush: true, failOnError: true)
            AccountRole.create(account, Role.findByAuthority('USER_ROLE'), true)
            return account.id
        }
        to LoginPage
        login('account-test', 'password1')
    }

    def cleanupSpec() {
        def remote = new SellitRemoteControl()
        remote {
            Account.findByUsername('account-test').delete()
            Account.findByUsername('Joe-Rockhead').delete()
        }
    }

    def "create an Account (login obviously not required)"() {
        when: 'REST call is made to Account Save (POST) api'
        def resp = doJsonPost('api/accounts', [username: "Joe-Rockhead", email: "chief@bedrockfd.gov", password: "password1", address: [id: "1"]])

        then: 'Server returns a status of Created'
        resp.status == 201

        and: 'The saved Account has an id value'
        resp.data.id
    }

    def "get account details (logged in as requested Account)" () {
        when: 'REST call is made to Account GET api for known Account'
        def resp = doGet('api/accounts/account-test')

        then: 'server returns a status of OK'
        resp.status == 200

        and: 'The returned Account is the one asked for'
        resp.data.username == 'account-test'
    }

    def "cannot access account details for different user" () {
        when: 'REST call is made to Account GET api for other Account'
        def resp = doGet('api/accounts/Joe-Rockhead')

        then: 'server returns a status of Unauthorized'
        resp.status == 403
    }

    def "update account email (logged in as requested Account)" () {
        when: 'REST call is made to Account PUT api for known Account'
        def resp = doJsonPut('api/accounts/account-test', [email: 'test@yahoo.com'])

        then: 'server returns a status of OK'
        resp.status == 200

        and: 'The returned Account has the updated email'
        resp.data.email == 'test@yahoo.com'
    }

    def "update account email denied for different user" () {
        when: 'REST call is made to Account PUT api for other Account'
        def resp = doJsonPut('api/accounts/Joe-Rockhead', [email: 'test@yahoo.com'])

        then: 'server returns a status of OK'
        resp.status == 403
    }
}