package sellit

import grails.rest.RestfulController

/**
 * Created by mark.mazzitello on 3/13/2015.
 */
class AccountRestController extends RestfulController<Account> {

    static responseFormats = ['json', 'xml']

    AccountRestController() {
        super(Account)
    }


}
