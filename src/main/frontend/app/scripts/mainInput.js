import {inputAsset} from './inputAsset';


export const mainInput = (function () {

    //critical has to be tested!
    let configInput = {},
        configInputObservable = [];

    $(function () {
        let inputContainer = $('#input-container');
        inputContainer.load('/app/views/inputAsset.html', function () {
            inputAsset.init();
            updateInputConfig();
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