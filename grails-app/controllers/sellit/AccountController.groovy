package sellit

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AccountController {

  static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

  def index(Integer max) {
    params.max = Math.min(max ?: 10, 100)
    respond Account.list(params), model: [accountInstanceCount: Account.count()]
  }

  def show(Account accountInstance) {
      accountInstance.reviewList = Review.where {reviewedAccount == accountInstance}.toList()
      respond accountInstance
  }

  def create() {
    respond new Account(params)
  }

  @Transactional
  def save(Account accountInstance) {
    if (accountInstance == null) {
      notFound()
      return
    }

    accountInstance.validate()
    if (accountInstance.hasErrors()) {
      respond accountInstance, view: 'create'
      return
    }
    accountInstance.save flush: true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.created.message', args: [message(code: 'account.label', default: 'Account'), accountInstance.id])
        redirect accountInstance
      }
      '*' { respond accountInstance, [status: CREATED] }
    }
  }

  def edit(Account accountInstance) {
    respond accountInstance
  }

  @Transactional
  def update(Account accountInstance) {
    if (accountInstance == null) {
      notFound()
      return
    }

    if (accountInstance.hasErrors()) {
      respond accountInstance.errors, view: 'edit'
      return
    }

    accountInstance.save flush: true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.updated.message', args: [message(code: 'account.label', default: 'Account'), accountInstance.id])
        redirect accountInstance
      }
      '*' { respond accountInstance, [status: OK] }
    }
  }

  @Transactional
  def delete(Account accountInstance) {

    if (accountInstance == null) {
      notFound()
      return
    }

    accountInstance.delete flush: true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'Account.label', default: 'Account'), accountInstance.id])
        redirect action: "index", method: "GET"
      }
      '*' { render status: NO_CONTENT }
    }
  }

  protected void notFound() {
    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])
        redirect action: "index", method: "GET"
      }
      '*' { render status: NOT_FOUND }
    }
  }

  boolean validatePassword(String pass) {
    return (pass) && (pass =~ /\p{Alpha}/) && (pass =~ /\p{Digit}/)
  }
}
