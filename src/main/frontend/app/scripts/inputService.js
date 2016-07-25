export const inputService = (function () {
    let links = {};

    let getApi = {
        "_links": {
            "customers": {
                "href": "http://localhost:8080/api/customers"
            },
            "self": {
                "href": "http://localhost:8080/api"
            }
        }
    };

    let getCustomers = {
        "customerResourceList": [],
        "_links": {
            "self": {
                "href": "http://localhost:8080/api/customers"
            },
            "create_customer": {
                "href": "http://localhost:8080/api/customers"
            },
            "list_customers": {
                "href": "http://localhost:8080/api/customers"
            }
        }
    };

    let postCustomer = {
        "dateOfBirth": "1990-09-09T00:00:00.000+0000",
        "gender": "m",
        "address": {
            "municipality": "Dübendorf",
            "municipality_nr": "191",
            "postal_code": "8044",
            "postal_code_addition": "00",
            "locality": "Gockhausen"
        },
        "_links": {
            "self": {
                "href": "http://localhost:8080/api/customers/1"
            },
            "list_products": {
                "href": "http://localhost:8080/api/customers/1/products"
            },
            "create_product": {
                "href": "http://localhost:8080/api/customers/1/products"
            },
            "create_deeplink": {
                "href": "http://localhost:8080/api/customers/1/deeplink"
            }
        }
    };

    (function () {
        return new Promise(function (resolve, reject) {
            setLinks(getApi);
            console.log('got API');
            customer()
                .then(
                    function (response) {
                        resolve(response);
                    }
                );


            /*$.getJSON('api', {})
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
             })*/
        });
    })();

    return {
        createCustomer: createCustomer
    };

    function customer() {
        return new Promise(function (resolve, reject) {
            if (links.customers) {
                setLinks(getCustomers);
                console.log('got Customer');
                resolve(getCustomers);
                /*$.getJSON(links.customers.href, {})
                 .done(success)
                 .fail(fail);

                 function success(response) {
                 setLinks(response);
                 resolve(response);
                 }

                 function fail() {
                 reject('Failed to get Customer');
                 }*/
            } else {
                resolve('Clould not get Customer');
            }
        });
    }

    function createCustomer(obj) {
        return new Promise(function (resolve, reject) {

            if (links.create_customer) {

                setLinks(postCustomer);
                console.log('customer created');
                resolve(postCustomer);

                /*   postHelper(links.create_customer.href, obj)
                 .then(success, fail);

                 function success(response) {
                 setLinks(response);
                 resolve(response);
                 }

                 function fail() {
                 reject('Failed to create Customer');
                 }*/
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