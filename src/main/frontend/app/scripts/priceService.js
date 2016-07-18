import $ from 'jquery';

export const priceService = (function () {
    var links = {};

    (function () {
        return new Promise(function (resolve, reject) {
            $.getJSON('api', {})
                .done(function (data) {
                    setLinks(data)
                })
                .fail(function () {
                    reject('Failed to get API');
                })
        });
    })();

    return {
        createCustomer: createCustomer
    };


    function createCustomer(obj) {
        if (links.customers) {
            return postHelper(links.customers.href, obj)
                .then(success, reject
                );

            function success(response) {
                setLinks(response);
            }

            function reject() {
                reject('Failed to create Customer');
            }
        }
        return new Promise();
    }

    function postHelper(url, payload) {
        return $.ajax({
            method: 'POST',
            contentType: 'application/json; charset=utf-8',
            url: url,
            data: JSON.stringify(payload)
        })
    }

    function setLinks(response) {
        if (response && response._links) {
            links = response._links;
        }
    }
})();