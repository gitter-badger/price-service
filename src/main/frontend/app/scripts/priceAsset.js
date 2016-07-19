import {priceService} from './priceService'

export const priceAsset = (function () {

    return {init: init};

    function init() {
        console.log('init price Asset');
        priceService.registerNotification(onChange);
    }


    function onChange() {
        console.log('changed');
    }
})();