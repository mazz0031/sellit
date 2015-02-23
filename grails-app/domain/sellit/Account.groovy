package sellit

class Account {

    String name
    String email
    String password
    Address address
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name blank: false
        email email: true, blank: false, unique: true
        password password: true, blank: false, minSize: 8, maxSize: 16, matches: "^.*(?=.*\\d)(?=.*[a-zA-Z]).*\$"
        address blank: false
    }
}
