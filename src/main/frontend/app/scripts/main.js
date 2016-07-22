import $ from 'jquery';
import {inputAsset} from './inputAsset';
import {priceAsset} from './priceAsset';


export const main = (function () {

    //critical has to be tested!
    let priceContainer = $('script').last().parent();

    let configInput = {},
        configInputObservable = [];

    $(function () {
        let inputContainer = $('#input-container');
        console.log(priceContainer);
        inputContainer.load('/app/views/inputAsset.html', function () {
            inputAsset.init();
            updateInputConfig();
        });

        priceContainer.load('/app/views/priceAsset.html', function () {
            priceAsset.init();
        });
    });

    return {
        config: configInput,
        registerConfigInputUpdate: registerConfigInputUpdate,
        updateInputConfig: updateInputConfig
    };

    function registerConfigInputUpdate(observer) {
        configInputObservable.push(observer)
    }

    function updateInputConfig() {
        let inputContainer = $('#input-container');

        const CONTAINER_DATA = inputContainer.data();
        for (const OBJ in CONTAINER_DATA) {
            configInput[OBJ] = CONTAINER_DATA[OBJ];
        }
        notifyConfigInputObservers(configInput);
    }


    function notifyConfigInputObservers(config) {
        configInputObservable.forEach(fu=>fu(config));
    }
})();