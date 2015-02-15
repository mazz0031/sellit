package sellit

class Review {

    Listing listedItem
    Account reviewedAccount
    Boolean wasSeller
    Boolean thumbsUp
    String reviewDescription

    static constraints = {
        listedItem blank: false
        reviewedAccount blank: false
        wasSeller blank: false
        reviewDescription size: 0..50
    }

}
