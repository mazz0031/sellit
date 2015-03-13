package sellit.pages

import geb.Page

/**
 * Created by mark.mazzitello on 2/27/2015.
 */
class AccountPage extends Page {

    static url = 'account/get'

    static content = {
        id { $("#id") }
        username { $("#username") }
        email { $("#email") }
    }

}
