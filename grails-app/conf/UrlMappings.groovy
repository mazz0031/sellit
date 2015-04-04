class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/$controller/$id?"{
            action = [GET:"show", POST:"save", PUT:"update", DELETE:"delete"]
        }

        "/"(view:"/index")
        "500"(view:'/error')

        // RESTService api
        "/api/role"(resources: 'role')
        "/api/accounts"(resources: 'account')
        "/api/listings"(resources: 'listing')
        "/api/bids"(resources: 'bid')
        "/api/reviews"(resources: 'review')
	}
}

