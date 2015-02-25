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
            //ToDo: add error to bidInstance.Errors that bid must be $.50 greater than highest bid
        }

        if (bidInstance.hasErrors()) {
            respond bidInstance.errors, view:'create'
            return
        }

        bidInstance.save flush:true

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
        //ToDo: get existing bids, determine of there are any and if so then the highest amount
        //ToDo: if ((noBids && bid.bidAmount >= bid.listedItem.startingPrice) || (bid.bidAmount > highBid + .50)) return true else return false
        return true;
    }
}
