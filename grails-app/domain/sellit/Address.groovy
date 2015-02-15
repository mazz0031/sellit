package sellit

class Address {

    String addressLine1
    String addressLine2
    String city
    String stateAbbr
    String postalCode
    String country

    static constraints = {
        addressLine1 blank: false
        city blank: false
        stateAbbr size: 2..2
        postalCode size: 5..10
    }
}
