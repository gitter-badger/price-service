import {priceService} from './priceService'
import {main} from './mainPrice'

export const priceAsset = (function () {

    let configPrice = {};

    return {init: init};

    function init() {
        updatePriceConfig();
        registerEvents();
    }


    function updatePriceConfig() {
        let priceContainer = $('#' + main.getContainerId());

        const CONTAINER_DATA = priceContainer.data();
        for (const OBJ in CONTAINER_DATA) {
            configPrice[OBJ] = CONTAINER_DATA[OBJ];
        }
    }


    function registerEvents() {
        $(document).on(configPrice.key + 'Customer', function (e) {
            console.log(e);
        });

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
            let priceContainer = $('#' + main.getContainerId()),
                price = response.price.toString().split('.');

            priceContainer.find('[data-price-integer]').text(price[0]);
            //TODO refactor fill one decimal to two (5 --> 50)
            priceContainer.find('[data-price-float]').text(price[1] + ((price[1] < 8) ? '0' : ''));
        }
    }
})();