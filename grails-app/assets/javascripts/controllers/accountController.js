/**
 * Created by mark.mazzitello on 4/28/2015.
 */

'use strict';

angular.module('app').controller('AccountController', function ($scope, $modal, $http, $location, Account) {

    $scope.newAccount = {};
    $scope.alerts = [];

    $scope.saveAccount = function () {
        $scope.clearAlerts();
        if (!txtUsername.validity.valid) {
            $scope.alerts.push({type: 'danger', msg: 'invalid username'});
        }
        ;
        if (!txtPassword1.validity.valid) {
            $scope.alerts.push({type: 'danger', msg: 'first password entry is required'});
        }
        ;
        if (!txtPassword2.validity.valid) {
            $scope.alerts.push({type: 'danger', msg: 'second password entry is required'});
        }
        ;
        if (!txtEmail.validity.valid) {
            $scope.alerts.push({type: 'danger', msg: 'invalid email'});
        }
        ;

        if (txtPassword1.value != txtPassword2.value) {
            $scope.alerts.push({type: 'danger', msg: 'password entries do not match'});
        }
        ;

        if ($scope.alerts.length < 1) {
            var account = $scope.newAccount;
            account.username = txtUsername.value;
            account.password = txtPassword1.value;
            account.email = txtEmail.value;
            account.address = txtAddress.value;

            Account.save(account).$promise.then(function(account) {
                $scope.alerts.push({type: 'success', msg: 'account added: ' + account.username});
            }, function(error) {
                    $scope.alerts.push({type: 'danger', msg: 'error creating account: ' + error.data});
                }
            );
        }
    };

    $scope.cancelAccount = function () {
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

