import $ from 'jquery';

import {priceService} from './priceService'

export const priceAsset = (function () {

    let configPrice = {};

    return {init: init};

    function init() {
        updatePriceConfig();
        registerEvents();
        /*
         priceService.registerPriceNotification(onChange);
         */
    }


    function updatePriceConfig() {
        let priceContainer = $('#price-container');

        const CONTAINER_DATA = priceContainer.data();
        for (const OBJ in CONTAINER_DATA) {
            configPrice[OBJ] = CONTAINER_DATA[OBJ];
        }
    }


    function registerEvents() {
        $(document).on(configPrice.key, function (e) {
            /*  var price = change.price.toString().split('.');
             $('#price-integer').text(price[0]);
             //TODO refactor fill one decimal to two (5 --> 50)
             $('#price-float').text(price[1] + ((price[1] < 8) ? '0' : ''));*/
            alert("something");
        });
    }

})();