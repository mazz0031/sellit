/**
 * Created by mark.mazzitello on 5/8/2015.
 */

describe('ListingsController', function() {

    var $controller, $httpBackend, $scope;

    var listings = [
        {deliverOption: "US Only", description: "test-listing", name: "test listing 1",
            listingDays: "8", startingPrice: "10", startDate: "2015-04-16T01:04:59Z",
            sellerAccount: {username: 'seller1-test', email: 'test1@me.com', password: 'password1'}},
        {deliverOption: "US Only", description: "test-listing", name: "test listing 2",
            listingDays: "8", startingPrice: "1000", startDate: "2015-05-07T01:04:59Z",
            sellerAccount: {username: 'seller2-test', email: 'test2@me.com', password: 'password1'}}
    ];

    beforeEach(module('app'));

    beforeEach(inject(function($injector) {
        $controller = $injector.get('$controller');
        $httpBackend = $injector.get('$httpBackend');
        $scope = $injector.get('$rootScope').$new();
    }));

    describe('initializes scope', function () {

        it('loads listings data into scope', function () {
            $httpBackend.expectGET('listings/').respond(200, listings);
            $controller('ListingsController', {$scope: $scope});
            $httpBackend.flush();

            expect($scope.listings).toEqual(listings);
        });

    });

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });


});
