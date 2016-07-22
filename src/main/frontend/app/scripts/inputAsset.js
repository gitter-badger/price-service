import {main} from './main'
import {inputService} from './inputService'
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

                inputService.createCustomer(customer)
                    .then(function (response) {
                        console.log(response);
                        $(document).trigger(config.key, [response]);
                    });
            }
        );
    }
})();