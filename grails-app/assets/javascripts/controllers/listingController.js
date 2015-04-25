/**
 * Created by mark.mazzitello on 4/21/2015.
 */

'use strict';

angular.module('app').controller('ListingController', function ($scope, $modal, $http) {
    var getListings = function () {
        return $http.get('api/listings/').then(function (response) {
            $scope.listings = response.data;
        });
    };

    getListings();
    $scope.alerts = [];


});



