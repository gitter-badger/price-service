export const priceService = (function () {
    var links = {};

    return {
        createProduct: createProduct,
        getPrice: getPrice,
        setLinks: setLinks
    };


    function createProduct(obj) {
        return new Promise(function (resolve, reject) {
            if (links.create_product) {

                postHelper(links.create_product.href, obj)
                    .then(success, fail);

                function success(response) {
                    //do not navigate into product
                    //setLinks(response);
                    resolve(response);
                }

                function fail() {
                    reject('Failed to create Product');
                }

            } else {
                resolve('could not create Product');
            }
        });
    }

    function getPrice() {
        return new Promise(function (resolve, reject) {
            if (links.update_price) {

                patchHelper(links.update_price.href, {})
                    .then(success, fail);

                function success(response) {
                    //do not navigate into product
                    //setLinks(response);
                    resolve(response);
                }

                function fail() {
                    reject('Failed to get Product');
                }

            } else {
                resolve('could not get Price Product');
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

    function patchHelper(url, payload) {
        return $.ajax({
            method: 'PATCH',
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