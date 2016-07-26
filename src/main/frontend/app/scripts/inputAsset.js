import Handlebars from 'handlebars';


import {mainInput} from './mainInput'
import {inputService} from './inputService'
import {Address} from './Address'
import {Customer} from './Customer'
import {zipService} from "./zipService";

export const inputAsset = (function () {
    let configInput = {};

    const inputDateRegex = '(^[1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])[-,.]([1-9]|[0][1-9]|1[0-2])[-,.]((19|20)[0-9]{2}$)';

    return {init: init};

    function init() {
        makeRadioButtonUnique();
        registerEvents();
        mainInput.registerConfigInputUpdate(onConfigUpdate);
    }

    function onConfigUpdate(newConfig) {
        configInput = newConfig;
    }

    function makeRadioButtonUnique() {
        let container = $('#' + mainInput.getContainerId());
        let genderButtons = container.find('[data-input-gender]'),
            randomGenderGroup = Math.random().toString(36).substring(7) + '-input-gender';
        genderButtons.attr('name', randomGenderGroup);

        for (let i = 0; i < genderButtons.length; i++) {
            let btn = genderButtons.eq(i),
                btnId = Math.random().toString(36).substring(7);
            btn.attr('name', randomGenderGroup);
            btn.attr('id', btnId);
            btn.next().attr('for', btnId)
        }
    }


    function registerEvents() {
        let container = $('#' + mainInput.getContainerId());
        container.find('[data-input-save]').on('click', function () {
                event.preventDefault();
                let inputAsset = container.find('[data-input-form]');
                let form = inputAsset.serializeArray(),
                    address = new Address('Gockhausen', 'Dübendorf', '191', '8044', '00'),
                    customer = new Customer(
                        form.find(obj => obj.name === 'dateOfBirth').value,
                        form.find(obj => obj.name.includes('input-gender')).value,
                        address);

                inputService.createCustomer(customer)
                    .then(function (response) {
                        $(document).trigger(configInput.key, [response]);
                    });
            }
        );

        container.find('[data-input-zip]').on('keyup', function (event, next) {
                let value = $(this).val().toString();
                if (value.length > 1) {
                    zipService.getZip(value)
                        .then(data=> {
                            if (data && data.results) {
                                renderView(data.results);
                            }
                        })
                }
                else {
                    renderView([]);
                }
            }
        );
    }

    function renderView(results) {

        let container = $('#' + mainInput.getContainerId());

        var source = container.find('[data-input-selection-template]').html();
        console.log(source);
        var template = Handlebars.compile(source);

        console.log(container.find('[data-input-selection]'));
        container.find('[data-input-selection]').html(template({
            results: results
        }));
    }
})();