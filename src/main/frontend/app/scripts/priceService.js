export const priceService = (function () {
    var links = {};

    return {
        createProduct: createProduct,
        setLinks: setLinks
    };


    function createProduct(obj) {
        console.log(obj);
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