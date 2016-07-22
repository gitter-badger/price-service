import {priceAsset} from './priceAsset';


export const main = (function () {

    //critical has to be tested!
    $(this).attr('class', 'test');

    let priceContainer = $('script').last().parent(),
        containerId = undefined;

    $(function () {
        containerId = Math.random().toString(36).substring(7);
        priceContainer.attr('id', containerId);
        priceContainer.load('/app/views/priceAsset.html', function () {
            priceAsset.init();
        });
    });

    return {
        getContainerId: getContainerId
    };

    function getContainerId() {
        return containerId;
    }
})();