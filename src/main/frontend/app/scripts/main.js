import $ from 'jquery';
import {inputAsset} from './inputAsset';
import {priceAsset} from './priceAsset';


export const main = (function () {
    let config = {},
        configObservable = [];

    $(function () {
        let inputContainer = $('#input-container');

        inputContainer.load('/app/views/inputAsset.html', function () {
            inputAsset.init();
            priceAsset.init();
            updateConfig();
        });
    });

    return {
        config: config,
        registerConfigUpdate: registerConfigUpdate,
        updateConfig: updateConfig
    };

    function registerConfigUpdate(observer) {
        configObservable.push(observer)
    }

    function updateConfig() {
        let inputContainer = $('#input-container');

        const CONTAINER_DATA = inputContainer.data();
        for (const OBJ in CONTAINER_DATA) {
            config[OBJ] = CONTAINER_DATA[OBJ];
        }
        notifyConfigObservers(config);
    }

    function notifyConfigObservers(config) {
        configObservable.forEach(fu=>fu(config));
    }
})();