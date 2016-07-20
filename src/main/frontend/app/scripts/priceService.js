import $ from 'jquery';

export const priceService = (function () {
    var links = {},
        priceObservable = [];

    (function () {
        return new Promise(function (resolve, reject) {
            $.getJSON('api', {})
                .done(function (data) {
                    setLinks(data);
                    customer()
                        .then(function (response) {
                            setLinks(response);
                            resolve(response);
                        });
                })
                .fail(function () {
                    reject('Failed to get API');
                })
        });
    })();

    return {
        createCustomer: createCustomer,
        createProduct: createProduct,
        registerPriceNotification: registerPriceNotification
    };

    function customer() {
        return new Promise(function (resolve, reject) {
            if (links.customers) {

                $.getJSON(links.customers.href, {})
                    .done(success)
                    .fail(fail);

                function success(response) {
                    setLinks(response);
                    resolve(response);
                }

                function fail() {
                    reject('Failed to get Customer');
                }
            } else {
                resolve('Clould not get Customer');
            }
        });
    }

    function createCustomer(obj) {
        return new Promise(function (resolve, reject) {

            if (links.create_customer) {

                postHelper(links.create_customer.href, obj)
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
            if (links.create_product) {

                postHelper(links.create_product.href, obj)
                    .then(success, fail);

                function success(response) {
                    //do not navigate into product
                    //setLinks(response);
                    resolve(response);
                    notifyPriceObservers(response);
                }

                function fail() {
                    reject('Failed to create Product');
                }

            } else {
                resolve('could not create Product');
            }
        });
    }

    function registerPriceNotification(observer) {
        priceObservable.push(observer);
    }

    function notifyPriceObservers(response) {
        priceObservable.forEach(fu => fu(response));
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