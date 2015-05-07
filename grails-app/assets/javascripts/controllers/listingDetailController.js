/**
 * Created by mark.mazzitello on 5/3/2015.
 */

'use strict';

angular.module('app').controller('ListingDetailController', function ($scope, $routeParams, $location, $http, Bid, Review, loggedInUser) {
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

        var bid = $scope.newBid;
        bid.bidAmount = txtBidAmount.value;
        bid.biddingAccount = loggedInUser;
        bid.listedItem = $scope.listing;

        Bid.save(bid).$promise.then(function(bid) {
                $scope.alerts.push({type: 'success', msg: 'bid saved'});
                delete $scope.newBid;
                getListingDetail();
            }, function(error) {
                $scope.alerts.push({type: 'danger', msg: 'error creating bid: ' + error.data});
            }
        );
    };

    $scope.cancelBid = function() {
        $scope.clearAlerts();
        delete $scope.newBid;
    };

    $scope.createReview = function(isSeller){
        $scope.newReview = {};
        $scope.sellerIsReviewing = isSeller;
    };

    $scope.saveReview = function() {
        $scope.clearAlerts();

        var review = $scope.newReview;
        review.wasSeller = $scope.sellerIsReviewing;
        review.reviewedAccount = $scope.listing.sellerAccount;
        review.listedItem = $scope.listing;
        review.thumbsUp = cbThumbsUp.checked;
        review.reviewDescription = txtReviewDescription.value;

        Review.save(review).$promise.then(function(review) {
                $scope.alerts.push({type: 'success', msg: 'review saved'});
                delete $scope.newReview;
                delete $scope.sellerIsReviewing;
                getListingDetail();
            }, function(error) {
                $scope.alerts.push({type: 'danger', msg: 'error creating review: ' + error.data});
            }
        );
    };

    $scope.cancelReview = function() {
        $scope.clearAlerts();
        delete $scope.newReview;
    };

    $scope.returnToListings = function() {
        $location.path("/listings");
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
