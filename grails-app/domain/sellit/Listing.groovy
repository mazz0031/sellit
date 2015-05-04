package sellit

class Listing {

    String name
    String description
    Date startDate
    Integer listingDays
    Date endDate
    Boolean completed
    Float startingPrice
    String deliverOption
    Account sellerAccount
    Float currentHighBid
    Account highBidAccount


    static constraints = {
        name blank: false
        description blank: false
        startDate blank: false
        listingDays blank: false, min: 3, max: 10
        endDate nullable: true //shouldn't need to make this nullable since it is calculated in the controller before being saved?
        startingPrice blank: false, scale: 2,  min: 0.0F
        deliverOption blank: false, inList: ["US Only", "Worldwide", "Pick Up Only"]
        sellerAccount blank: false
        currentHighBid nullable: true
        highBidAccount nullable: true
        completed nullable: true
    }

}
