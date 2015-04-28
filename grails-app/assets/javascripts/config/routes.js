/**
 * Created by mark.mazzitello on 4/24/2015.
 */

'use strict';

angular.module('app').config(function($routeProvider) {
    $routeProvider.when("/listings", {templateUrl: "templates/listings.html"});
    $routeProvider.when("/login", {templateUrl: "templates/login.html"});

    // Default route: listings screen
    $routeProvider.otherwise({templateUrl: "templates/listings.html"});
})

