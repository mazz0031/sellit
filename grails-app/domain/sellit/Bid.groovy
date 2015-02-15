package sellit

class Bid {

    Float bidAmount
    Account biddingAccount
    Listing listedItem

    static constraints = {
        bidAmount blank: false, scale: 2
        biddingAccount blank: false
        listedItem blank: false
    }
}
