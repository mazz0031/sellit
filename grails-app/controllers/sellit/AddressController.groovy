package sellit

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON

class AddressController extends RestfulController<Address> {

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]
    static responseFormats = ['json']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Address.list(params), model: [addressInstanceCount: Address.count()]
    }

    def show(Address addressInstance) {
        respond addressInstance
    }

    def create() {
        respond new Address(params)
    }

    @Transactional
//    @Secured(value=["hasRole('ROLE_USER')"])
    def save(Address addressInstance) {
        if (addressInstance == null) {
            return
        }

        addressInstance.validate()

        if (addressInstance.hasErrors()) {
            respond addressInstance.errors, view:'create'
            return
        }

        addressInstance.save(flush:true)

        redirect action: "index", method: "GET"
    }

    @Transactional
//    @Secured(value=["hasRole('ROLE_USER')"])
    def update(Address addressInstance) {

        if (addressInstance == null) {
            return
        }

        addressInstance.addressLine1 = params.addressLine1
        addressInstance.addressLine2 = params.addressLine2
        addressInstance.city = params.city
        addressInstance.country = params.country
        addressInstance.postalCode = params.postalCode
        addressInstance.stateAbbr = params.stateAbbr
        addressInstance.validate()

        if (addressInstance.hasErrors()) {
            respond addressInstance.errors, view:'edit'
            return
        }

        addressInstance.save(flush: true)

        redirect action: "index", method: "GET"
    }

    @Transactional
//    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def delete(Address addressInstance) {

        if (addressInstance == null) {
            return
        }

        addressInstance.delete(flush:true)

        redirect action: "index", method: "GET"
    }
}
