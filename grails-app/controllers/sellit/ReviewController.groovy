package sellit



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ReviewController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Review.list(params), model:[reviewInstanceCount: Review.count()]
    }

    def show(Review reviewInstance) {
        respond reviewInstance
    }

    def create() {
        respond new Review(params)
    }

    @Transactional
    def save(Review reviewInstance) {
        if (reviewInstance == null) {
            notFound()
            return
        }

        if (reviewInstance.hasErrors()) {
            respond reviewInstance.errors, view:'create'
            return
        }

        reviewInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'review.label', default: 'Review'), reviewInstance.id])
                redirect reviewInstance
            }
            '*' { respond reviewInstance, [status: CREATED] }
        }
    }

    def edit(Review reviewInstance) {
        respond reviewInstance
    }

    @Transactional
    def update(Review reviewInstance) {
        if (reviewInstance == null) {
            notFound()
            return
        }

        if (reviewInstance.hasErrors()) {
            respond reviewInstance.errors, view:'edit'
            return
        }

        reviewInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Review.label', default: 'Review'), reviewInstance.id])
                redirect reviewInstance
            }
            '*'{ respond reviewInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Review reviewInstance) {

        if (reviewInstance == null) {
            notFound()
            return
        }

        reviewInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Review.label', default: 'Review'), reviewInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'review.label', default: 'Review'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
