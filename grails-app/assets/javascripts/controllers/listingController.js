/**
 * Created by mark.mazzitello on 4/21/2015.
 */

'use strict';

angular.module('app').controller('ListingController', function ($scope, $modal, $http, $location, Listing, loggedInUser) {
    $scope.loggedInUser = loggedInUser;

    var getActiveListings = function () {
        return $http.get('api/listings/').then(function (response) {
            $scope.listings = response.data;
        });
    };

    var getAllListings = function() {
        return $http.get('api/listings?showCompleted=true').then(function (response) {
            $scope.listings = response.data;
        });
    };

    getActiveListings();
    $scope.alerts = [];

    $scope.getListings = function($event, id) {
        var checkbox = $event.target;
        if (checkbox.checked) {
            return getAllListings();
        }
        else {
            return getActiveListings();
        }
    };

    $scope.addListing = function() {
        $scope.newListing = {};
    };

    $scope.saveListing = function() {
        $scope.clearAlerts();

        var listing = $scope.newListing;
        listing.name = txtListingName.value;
        listing.description = txtListingDesc.value;
        //had to comment out this line because could not get HTML/javascript date to format in a way grails would accept
        //listing.startDate = txtStartDate.value;
        listing.listingDays = txtDays.value;
        listing.startingPrice = txtStartingPrice.value;
        listing.deliverOption = pklDeliverOption.value;

        Listing.save(listing).$promise.then(function(listing) {
                $scope.alerts.push({type: 'success', msg: 'listing added: ' + listing.name});
                delete $scope.newListing;
                getActiveListings();
                $location.path("/listings");
            }, function(error) {
                $scope.alerts.push({type: 'danger', msg: 'error creating listing: ' + error.data});
            }
        );
    };

    $scope.cancelListing = function() {
        delete $scope.newListing;
    };

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.clearAlerts = function () {
        $scope.alerts.forEach(function (index) {
            $scope.alerts.splice(index);
        });
    };
});



