import $ from 'jquery';
import {priceService} from './priceService'
import {Address} from './Address'
import {Customer} from './Customer'

export const inputAsset = (function () {

    return {init: init};

    function init() {
        registerButtonEvents();
    }


    function registerButtonEvents() {
        $('#save-personals').on('click', function () {
                event.preventDefault();
                var form = $('#input-asset').serializeArray(),
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
        $('#price').on('click', function () {
                event.preventDefault();
                var product = {
                    "productNumber": "PRO_x0BAS__HEL_IG",
                    "description": "Product one",
                    "unfall": "COD_ausgeschlossen_HEL",
                    "franchise": "COD_Franchise_KVG-O_Erwachsener_300_HEL"
                };

                priceService.createProduct(product)
                    .then(function (response) {
                        console.log(response);
                    });
            }
        );

    }
})();