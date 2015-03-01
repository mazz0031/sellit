package sellit

/**
 * Created by mark.mazzitello on 2/21/2015.
 */
class CreateAccountSpec {

        void sampleTest() {
            def address = new Address(addressLine1: "123 Main Street", city: "Nowhere", stateAbbr: "AK", postalCode: "12345")
            address.save(failOnError: true)





        }


}
