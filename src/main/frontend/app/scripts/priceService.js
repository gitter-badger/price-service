export const priceService = (function () {
    var links = {};

    let postProduct = {
        "description": null,
        "productNumber": "PRO_x0BAS__HEL_IG",
        "unfall": "COD_ausgeschlossen_HEL",
        "franchise": "COD_Franchise_KVG-O_Erwachsener_2500_HEL",
        "drittesKind": null,
        "price": 0,
        "_links": {
            "self": {
                "href": "http://localhost:8080/api/products/0"
            },
            "update_price": {
                "href": "http://localhost:8080/api/products/0"
            }
        }
    };

    let getProductPrice = {
        "description": null,
        "productNumber": "PRO_x0BAS__HEL_IG",
        "unfall": "COD_ausgeschlossen_HEL",
        "franchise": "COD_Franchise_KVG-O_Erwachsener_2500_HEL",
        "drittesKind": null,
        "price": 278.50,
        "_links": {
            "self": {
                "href": "http://localhost:8080/api/products/0"
            }
        }
    };

    return {
        createProduct: createProduct,
        getPrice: getPrice,
        setLinks: setLinks
    };


    function createProduct(obj) {
        return new Promise(function (resolve, reject) {
            if (links.create_product) {

                resolve(postProduct);
                /*   postHelper(links.create_product.href, obj)
                 .then(success, fail);

                 function success(response) {
                 //do not navigate into product
                 //setLinks(response);
                 resolve(response);
                 }

                 function fail() {
                 reject('Failed to create Product');
                 }*/

            } else {
                resolve('could not create Product');
            }
        });
    }

    function getPrice() {
        return new Promise(function (resolve, reject) {
            resolve(getProductPrice);
            if (links.update_price) {


                /*     postHelper(links.update_price.href, obj)
                 .then(success, fail);

                 function success(response) {
                 //do not navigate into product
                 //setLinks(response);
                 resolve(response);
                 }

                 function fail() {
                 reject('Failed to get Product');
                 }*/

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

    function setLinks(response) {
        if (response && response._links) {
            links = response._links;
        }
    }

})();