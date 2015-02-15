package sellit

class Listing {

    String name
    String description
    Date startDate
    Integer listingDays
    Float startingPrice
    String deliverOption
    Account sellerAccount


    static constraints = {
        name blank: false
        description blank: false
        startDate blank: false, min: new Date()
        listingDays blank: false, min: 3, max: 10
        startingPrice blank: false, scale: 2,  min: 0.0F
        deliverOption blank: false, inList: ["US Only", "Worldwide", "Pick Up Only"]
        sellerAccount blank: false
    }

}
