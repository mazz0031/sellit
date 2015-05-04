/**
 * Created by mark.mazzitello on 4/30/2015.
 */

'use strict';

angular.module('app').controller('loginController', function ($rootScope, $scope, $modal, $http) {

    var authenticate = function (credentials, callback) {

        var headers = credentials ? {
            authorization: "Basic "
            + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('api/account', {headers: headers})
            .success(function (data) {
                if (data.username) {
                    $rootScope.authenticated = true;
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });

    }

    //authenticate();

    $scope.credentials = {};

    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/sellit");
            } else {
                $scope.alerts.push({type: 'danger', msg: 'error logging in'});
            }
        });
    };

    $scope.alerts = [];
});