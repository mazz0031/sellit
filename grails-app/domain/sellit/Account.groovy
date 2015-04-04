package sellit

class Account {

	transient springSecurityService

	String username
	String password
    String email
    String address
	String city
	String state
	String postalCode
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    Date dateCreated
    Date lastUpdated

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		//password password: true, blank: false //, minSize: 8, maxSize: 16, matches: "^.*(?=.*\\d)(?=.*[a-zA-Z]).*\$"
        password validator: { String password, account ->
            if (password && password.length() >= 8 && password.length() <= 16 &&
                    (!password.matches('^.*\\p{Alpha}.*$') ||
                            !password.matches('^.*\\p{Digit}.*$'))) {
                return 'user.password.error.username'
            }
        }
		email email: true, blank: false
		address nullable: true
		city nullable: true
		state nullable: true
		postalCode nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		AccountRole.findAllByAccount(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
