/**
 * Created by mark.mazzitello on 5/6/2015.
 */

'use strict';

angular.module('app').factory('Bid', function ($resource) {
    return $resource('api/bids', {},
        {save: {method: 'POST'}}
    );
});