package sellit

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class AddressController extends RestfulController<Account> {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static responseFormats = ['json', 'xml']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Address.list(params), model: [addressInstanceCount: Address.count()]
    }

    def show(Address addressInstance) {
        respond addressInstance
    }

    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def create() {
        respond new Address(params)
    }

    @Transactional
    def save(Address addressInstance) {
        if (addressInstance == null) {
            notFound()
            return
        }

        if (addressInstance.hasErrors()) {
            respond addressInstance.errors, view:'create'
            return
        }

        addressInstance.save(flush:true)

        redirect action: "index", method: "GET"
    }

    @Transactional
    def delete(Address addressInstance) {

        if (addressInstance == null) {
            notFound()
            return
        }

        addressInstance.delete flush:true

        redirect action: "index", method: "GET"
    }
}
