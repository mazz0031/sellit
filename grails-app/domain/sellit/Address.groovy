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
        stateAbbr blank: false, size: 2
        postalCode blank: false, size: 5..10
    }
}
