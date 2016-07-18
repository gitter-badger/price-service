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
                    address = new Address(),
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

    }
})();