import $ from 'jquery';
import {inputAsset} from './inputAsset';
import {priceAsset} from "./priceAsset";

$(function () {
    var inputContainer = $('#input-container');

    inputContainer.load('/app/views/inputAsset.html', function () {
        inputAsset.init();
        priceAsset.init();
    });
});
