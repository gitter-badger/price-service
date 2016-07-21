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
            addProduct();
        });
    }

    function addProduct() {
        const PRODUCT = {
            "productNumber": configPrice.productNumber,
            "unfall": configPrice.unfall,
            "franchise": configPrice.franchise
        };

        priceService.createProduct(PRODUCT)
            .then(function (response) {
                console.log(response);
                updatePrice(response)
            });
    }

    function updatePrice(response) {
        if (response && typeof response.price !== 'undefined') {
            var price = response.price.toString().split('.');
            $('#price-integer').text(price[0]);
            //TODO refactor fill one decimal to two (5 --> 50)
            $('#price-float').text(price[1] + ((price[1] < 8) ? '0' : ''));
        }
    }
})();