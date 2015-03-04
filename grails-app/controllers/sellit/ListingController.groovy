package sellit

import org.h2.api.DatabaseEventListener

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import groovy.time.TimeCategory

@Transactional(readOnly = true)
class ListingController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def now = new Date()
        def results = Listing.where {endDate >= now}
        respond results.list(params), model: [listingInstanceCount: results.size()], view: 'index'
    }

    def show(Listing listingInstance) {
        respond listingInstance
    }

    def create() {
        respond new Listing(params)
    }

    def search(String searchTerm, boolean showCompleted, Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def now = new Date()
        if (showCompleted) {
            def results = Listing.where {(name =~ "%${params.searchTerm}%" || description =~ "%${params.searchTerm}%") && endDate < now}
            respond results.list(params), model: [listingInstanceCount: results.size()], view: 'index'
        }
        else {
            def results = Listing.where {(name =~ "%${params.searchTerm}%" || description =~ "%${params.searchTerm}%") && endDate >= now}
            respond results.list(params), model: [listingInstanceCount: results.size()], view: 'index'
        }
    }

    @Transactional
    def save(Listing listingInstance) {
        if (listingInstance == null) {
            notFound()
            return
        }

        setEndDate(listingInstance)

        if (listingInstance.hasErrors()) {
            respond listingInstance.errors, view: 'create'
            return
        }

        listingInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'listing.label', default: 'Listing'), listingInstance.id])
                redirect listingInstance
            }
            '*' { respond listingInstance, [status: CREATED] }
        }
    }

    def edit(Listing listingInstance) {
        respond listingInstance
    }

    @Transactional
    def update(Listing listingInstance) {
        if (listingInstance == null) {
            notFound()
            return
        }

        setEndDate(listingInstance)

        if (listingInstance.hasErrors()) {
            respond listingInstance.errors, view: 'edit'
            return
        }

        listingInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Listing.label', default: 'Listing'), listingInstance.id])
                redirect listingInstance
            }
            '*' { respond listingInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Listing listingInstance) {

        if (listingInstance == null) {
            notFound()
            return
        }

        listingInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Listing.label', default: 'Listing'), listingInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    protected void setEndDate(Listing listingInstance) {
        listingInstance.endDate = listingInstance.startDate.plus(listingInstance.listingDays)
    }
}
