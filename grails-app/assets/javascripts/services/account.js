/**
 * Created by mark.mazzitello on 5/3/2015.
 */

'use strict';

angular.module('app').factory('Account', function ($resource) {
    return $resource('api/accounts', {},
        {save: {method: 'POST'}}
    );
});

