package sellit.pages

import geb.Page

/**
 * Created by mark.mazzitello on 3/27/2015.
 */
class LoginPage extends Page {

    static url = 'login/auth'

    static content = {
        username { $("#username") }
        password { $("#password ") }
        submit { $("#submit") }
    }

    def login(String un, String pw) {
        username.value(un)
        password.value(pw)
        submit.click()
    }
}