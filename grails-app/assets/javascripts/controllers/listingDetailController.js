/**
 * Created by mark.mazzitello on 5/3/2015.
 */

'use strict';

angular.module('app').controller('ListingDetailController', function ($scope, $routeParams, $location, $http, loggedInUser) {
    $scope.loggedInUser = loggedInUser;
    $scope.alerts = [];

    var getListingDetail = function() {
        return $http.get('api/listings/' + $routeParams.id).then(function(response) {
            $scope.listing = response.data;
        });
    };

    getListingDetail();

    $scope.createBid = function() {
        $scope.newBid = {};
    };

    $scope.saveBid = function() {
        $scope.clearAlerts();




    };

    $scope.cancelBid = function() {
        $scope.clearAlerts();
        delete $scope.newBid;
    };

    $scope.returnToListings = function() {
        $location.path("/listings");
    };

    $scope.clearAlerts = function () {
        $scope.alerts.forEach(function (index) {
            $scope.alerts.splice(index);
        });
    };
});
