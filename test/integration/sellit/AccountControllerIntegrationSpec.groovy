package sellit

import spock.lang.Specification

class AccountControllerIntegrationSpec extends Specification {

    def address

    def setup() {
        address = new Address(addressLine1: "123 Main Street", city: "Bedrock", stateAbbr: "CA", postalCode: "99999")
        address.save(failOnError: true)
    }

    def 'saving Account persists new Account in database'() {
        setup:
        def account = new Account(username: "Joe Rockhead", password: "aaaa1234", email: "chief@bedrockfd.gov", address: address)

        when:
        account.save(failOnError: true)

        then:
        account.errors.errorCount == 0
        account.id
        account.dateCreated
        Account.get(account.id).email == 'chief@bedrockfd.gov'
    }

    def 'updating Account data updates the database'() {
        setup:
        def account = new Account(username: "Barney Rubble", password: "aaaa1234", email: "brubble@gmail.com", address: address)
        account.save(failOnError: true)

        when:
        def accountFromDB = Account.get(account.id)
        accountFromDB.email = 'barney@yahoo.com'
        accountFromDB.save(failOnError: true)

        then:
        Account.get(account.id).email == 'barney@yahoo.com'
    }

    def 'creating Account with invalid password fails validation'() {
        setup:
        def account = new Account(username: "Fred Flintstone", password: "nogood", email: "fred@gmail.com", address: address)

        when:
        def result = account.save()

        then:
        !result
        account.hasErrors()
    }

    def 'creating Account with missing parameters fails validation'() {
        setup:
        def account = new Account()

        when:
        def result = account.save()

        then:
        !result
        account.hasErrors()
    }

    def 'deleting Account removes it from the database'() {
        setup:
        def account = new Account(username: "Pearl Slaghoople", password: "aaaa1234", email: "pearl@gmail.com", address: address)
        account.save(failOnError: true)

        when:
        account.delete()

        then:
        !Account.exists(account.id)
    }
}
