export const inputService = (function () {
    var links = {};

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
        createCustomer: createCustomer
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