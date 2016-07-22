import {inputAsset} from './inputAsset';


export const mainInput = (function () {

    let configInput = {},
        configInputObservable = [],
        // has to be tested!
        inputContainer = $('script').last().parent(),
        containerId = undefined;

    $(function () {
        containerId = Math.random().toString(36).substring(7);
        inputContainer.attr('id', containerId);
        inputContainer.load('/app/views/inputAsset.html', function () {
            inputAsset.init();
            updateInputConfig();
        });
    });

    return {
        getContainerId: getContainerId,
        config: configInput,
        registerConfigInputUpdate: registerConfigInputUpdate,
        updateInputConfig: updateInputConfig
    };

    function registerConfigInputUpdate(observer) {
        configInputObservable.push(observer)
    }

    function updateInputConfig() {
        let inputContainer = $('#' + containerId);

        const CONTAINER_DATA = inputContainer.data();
        for (const OBJ in CONTAINER_DATA) {
            configInput[OBJ] = CONTAINER_DATA[OBJ];
        }
        notifyConfigInputObservers(configInput);
    }


    function notifyConfigInputObservers(config) {
        configInputObservable.forEach(fu=>fu(config));
    }

    function getContainerId() {
        return containerId;
    }
})();