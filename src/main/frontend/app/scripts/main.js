import $ from 'jquery';
import {inputAsset} from './inputAsset';
import {priceAsset} from './priceAsset';


export const main = (function () {
    let configInput = {},
        configPrice = {},
        configInputObservable = [];

    $(function () {
        let inputContainer = $('#input-container');
        let priceContainer = $('#price-container');

        inputContainer.load('/app/views/inputAsset.html', function () {
            inputAsset.init();
            updateInputConfig();
        });

        priceContainer.load('/app/views/priceAsset.html', function () {
            priceAsset.init();
            updatePriceConfig();
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


    function updatePriceConfig() {
        let priceContainer = $('#price-container');

        const CONTAINER_DATA = priceContainer.data();
        for (const OBJ in CONTAINER_DATA) {
            configPrice[OBJ] = CONTAINER_DATA[OBJ];
        }
    }

    function notifyConfigInputObservers(config) {
        configInputObservable.forEach(fu=>fu(config));
    }
})();