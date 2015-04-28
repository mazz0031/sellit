/**
 * Created by mark.mazzitello on 4/21/2015.
 */

'use strict';

angular.module('app').controller('ListingController', function ($scope, $modal, $http) {
    var getListings = function() {
        if ($scope.cbShowCompleted) {
            return getAllListings();
        }
        else {
            return getActiveListings();
        }
    }

    var getActiveListings = function () {
        return $http.get('api/listings/').then(function (response) {
            $scope.listings = response.data;
        });
    };

    var getAllListings = function() {
        return $http.get('api/listings?showCompleted=true').then(function (response) {
            $scope.listings = response.data;
        });
    }

    getListings();

    $scope.alerts = [];
});



