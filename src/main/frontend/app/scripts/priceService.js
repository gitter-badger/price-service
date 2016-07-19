import $ from 'jquery';

export const priceService = (function () {
    var links = {},
        observable = [];

    (function () {
        return new Promise(function (resolve, reject) {
            $.getJSON('api', {})
                .done(function (data) {
                    setLinks(data);
                    resolve(data);
                })
                .fail(function () {
                    reject('Failed to get API');
                })
        });
    })();

    return {
        createCustomer: createCustomer,
        createProduct: createProduct,
        registerNotification: registerNotification
    };

    function createCustomer(obj) {
        return new Promise(function (resolve, reject) {

            if (links.customers) {

                postHelper(links.customers.href, obj)
                    .then(success, fail);

                function success(response) {
                    setLinks(response);
                    resolve(response);
                }

                function fail() {
                    reject('Failed to create Customer');
                }
            } else {
                resolve('Clould not create Customer');
            }
        });
    }

    function createProduct(obj) {
        return new Promise(function (resolve, reject) {
            if (links.create_products) {

                postHelper(links.create_products.href, obj)
                    .then(success, fail);

                function success(response) {
                    setLinks(response);
                    resolve(response);
                    notifyObservers(response);
                }

                function fail() {
                    reject('Failed to create Product');
                }

            } else {
                resolve('could not create Product');
            }
        });
    }

    function registerNotification(observer) {
        console.log(observer.name, 'registered');
        observable.push(observer);
    }

    function notifyObservers(response) {
        console.log('notify Observers');
        observable.forEach(obj => obj(response));
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