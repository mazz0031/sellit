package sellit

class Account {

    String name
    String email
    String password
    Address address

    static constraints = {
        name blank: false
        email email: true, blank: false, unique: true
        password password: true, blank: false
        address blank: false
    }
}
