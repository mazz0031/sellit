/**
 * Created by mark.mazzitello on 5/3/2015.
 */

'use strict';

angular.module('app').factory('Listing', function ($resource) {
    return $resource('api/listings', {},
        {save: {method: 'POST'}}
    );
});

