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
        updateInputConfig();

        //load view if no data-key-open is set
        if (configInput && !configInput.openKey) {
            loadView();
        }
        //open view on data-key-open event
        else {
            $(document).on(configInput.openKey, function () {
                loadView();
            });
        }

        //temp should be in parentview
        $('#open').on('click', function () {
            $(document).triggerHandler(configInput.openKey);
        });
    });

    return {
        getContainerId: getContainerId,
        config: configInput,
        registerConfigInputUpdate: registerConfigInputUpdate,
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

    function loadView() {
        $('#' + getContainerId()).load('/app/views/inputAsset.html', function () {
            inputAsset.init();
            updateInputConfig();
        });
    }
})();