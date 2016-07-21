import $ from 'jquery';

import {main} from './main'
import {priceService} from './priceService'
import {Address} from './Address'
import {Customer} from './Customer'

export const inputAsset = (function () {
    let config = {};

    const inputDateRegex = '(^[1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])[-,.]([1-9]|[0][1-9]|1[0-2])[-,.]((19|20)[0-9]{2}$)';

    return {init: init};

    function init() {
        registerEvents();
        main.registerConfigInputUpdate(onConfigUpdate);
    }

    function onConfigUpdate(newConfig) {
        config = newConfig;
        console.log(config);
    }


    function registerEvents() {
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


                $(document).trigger(config.key);
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
                main.updateInputConfig();
            }
        );
    }
})();