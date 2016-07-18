import $ from 'jquery';
import {inputAsset} from './inputAsset';


$(function () {
    var inputContainer = $('#input-container');

    inputContainer.load('/app/views/inputAsset.html', function () {
        inputAsset.init();
    });
});
