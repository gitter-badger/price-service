import $ from 'jquery';

import {priceService} from './priceService'

export const priceAsset = (function () {

    return {init: init};

    function init() {
        console.log('init price Asset');
        priceService.registerNotification(onChange);
    }


    function onChange(change) {
        $('#price-integer').text(Math.floor((Math.random() * 1000) + 1));
        $('#price-float').text(Math.floor((Math.random() * 19) + 1) * 5);
        console.log('changed', change, change.price);
    }
})();