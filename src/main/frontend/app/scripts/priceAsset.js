import $ from 'jquery';

import {priceService} from './priceService'

export const priceAsset = (function () {

    return {init: init};

    function init() {
/*
        priceService.registerPriceNotification(onChange);
*/
    }


   /* function onChange(change) {
        var price = change.price.toString().split('.');
        $('#price-integer').text(price[0]);
        //TODO refactor fill one decimal to two (5 --> 50)
        $('#price-float').text(price[1] + ((price[1] < 8) ? '0' : ''));
    }*/
})();