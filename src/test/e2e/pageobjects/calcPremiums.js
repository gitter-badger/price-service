module.exports = (function () {

    var title = element(by.css('[e2e-id="title"]'));

    return {
        getTitle: function () {
            return title;
        },
        getUrl: function () {
            return 'http://localhost:9001/';
        }
    }
})();
