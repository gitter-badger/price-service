import {mainInput} from './mainInput'
import {inputService} from './inputService'
import {Address} from './Address'
import {Customer} from './Customer'

export const inputAsset = (function () {
    let configInput = {};

    return {init: init};

    function init() {
        makeRadioButtonUnique();
        registerEvents();
        mainInput.registerConfigInputUpdate(onConfigUpdate);
    }

    function onConfigUpdate(newConfig) {
        configInput = newConfig;
        console.log('Config', mainInput.getContainerId(), configInput);
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
    }
})();