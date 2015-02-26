package sellit



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BidController {

    static allowedMethods = [save: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Bid.list(params), model:[bidInstanceCount: Bid.count()]
    }

    def show(Bid bidInstance) {
        respond bidInstance
    }

    def create() {
        respond new Bid(params)
    }

    @Transactional
    def save(Bid bidInstance) {
        if (bidInstance == null) {
            notFound()
            return
        }

        if (!bidIsValid(bidInstance)) {
            bidInstance.errors.rejectValue("bidAmount", "bid is not valid")
        }

        if (bidInstance.hasErrors()) {
            respond bidInstance.errors, view:'create'
            return
        }

        bidInstance.save flush:true
        updateListingHighBid(bidInstance)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bid.label', default: 'Bid'), bidInstance.id])
                redirect bidInstance
            }
            '*' { respond bidInstance, [status: CREATED] }
        }
    }

    def edit(Bid bidInstance) {
        respond bidInstance
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bid.label', default: 'Bid'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    protected boolean bidIsValid(Bid bid) {
        if (bid.listedItem) {
            def bids = bid.where { listedItem == bid.listedItem }.list()
            if ((bids.size() == 0 && bid.bidAmount >= bid.listedItem.startingPrice) || (bid.bidAmount > bids.bidAmount.max() + 0.5)) {
                return true
            }
        }
        return false
    }

    protected void updateListingHighBid(Bid bid) {
        bid.listedItem.currentHighBid = bid.bidAmount
        bid.listedItem.save(failOnError: true)
    }
}
