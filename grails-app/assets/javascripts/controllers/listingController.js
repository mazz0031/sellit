/**
 * Created by mark.mazzitello on 4/21/2015.
 */

'use strict';

angular.module('app').controller('ListingController', function ($scope, $modal, $http) {
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

    $scope.getListings = function($event, id) {
        var checkbox = $event.target;
        if (checkbox.checked) {
            return getAllListings();
        }
        else {
            return getActiveListings();
        }
    };

    getActiveListings();



    $scope.alerts = [];
});



