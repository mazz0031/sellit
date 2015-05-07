/**
 * Created by mark.mazzitello on 5/6/2015.
 */

'use strict';

angular.module('app').factory('Review', function ($resource) {
    return $resource('api/reviews', {},
        {save: {method: 'POST'}}
    );
});