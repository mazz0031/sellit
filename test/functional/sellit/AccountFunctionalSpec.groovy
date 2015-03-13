package sellit

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import sellit.pages.AccountPage
import spock.lang.Stepwise


/**
 * Created by mark.mazzitello on 2/21/2015.
 */

@Stepwise
class AccountFunctionalSpec extends GebSpec {

    def remote = new RemoteControl()

    def accountId
//    def addressId

    void setup() {
        /*addressId = remote {
            def address = new Address(addressLine1: "123 Main Street", city: "Bedrock", stateAbbr: "CA", postalCode: "12345")
            address.save(failOnError: true)
            address.id
        }*/

        accountId = remote {
            def address = new Address(addressLine1: "123 Main Street", city: "Bedrock", stateAbbr: "CA", postalCode: "12345")
            address.save(failOnError: true)

            def account = new Account(username: "Joe Rockhead", email: "chief@bedrockfd.gov", password: "abcd1234", address: address)
            account.save(failOnError: true)
            account.id
        }
    }

    void cleanup() {
        remote {
            Account.findByUsername('Joe Rockhead').delete()
            Address.findByAddressLine1('123 Main Street').delete()
        }
    }

    def "get account info"() {
        when:
        to AccountPage, id: accountId

        then:
        username.text() == "Joe Rockhead"
        email.text() == "chief@bedrockfd.gov"
    }
}