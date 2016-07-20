import $ from 'jquery';

import {main} from './main'
import {priceService} from './priceService'
import {Address} from './Address'
import {Customer} from './Customer'

export const inputAsset = (function () {
    let config = {};

    return {init: init};

    function init() {
        registerButtonEvents();
        main.registerConfigUpdate(onConfigUpdate);
    }

    function onConfigUpdate(newConfig) {
        config = newConfig;
        console.log(config);
    }


    function registerButtonEvents() {
        $('#save-personals').on('click', function () {
                event.preventDefault();
                let form = $('#input-asset').serializeArray(),
                    address = new Address('Gockhausen', 'Dübendorf', '191', '8044', '00'),
                    customer = new Customer(
                        form.find(obj => obj.name === 'dateOfBirth').value,
                        form.find(obj => obj.name === 'gender').value,
                        address);

                priceService.createCustomer(customer)
                    .then(function (response) {
                        console.log(response);
                    });
            }
        );


        //temp
        $('#add-product').on('click', function () {
                const PRODUCT = {
                    "productNumber": config.productNumber,
                    "unfall": config.unfall,
                    "franchise": config.franchise
                };

                priceService.createProduct(PRODUCT)
                    .then(function (response) {
                        console.log(response);
                    });
            }
        );
        $('#update-config').on('click', function () {
                console.log('franchise', $('#franchise'), $('#franchise').val());
                let inputContainer = $('#input-container');
                inputContainer.data('franchise', $('#franchise').val());
                inputContainer.data('unfall', $('#unfall').val());
                inputContainer.data('product-number', $('#product-number').val());
                main.updateConfig();
            }
        );
    }
})();