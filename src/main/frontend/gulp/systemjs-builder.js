module.exports = function (gulp, data, util, taskName) {
    var gulp = require('gulp'),
        path = require("path"),
        Builder = require('systemjs-builder');

    var builder = new Builder(data.path.FRONTEND,
        data.path.FRONTEND + 'app/scripts/systemjs.config.js');


    gulp.task(taskName, function () {

        return builder
            .bundle(data.path.FRONTEND + 'app/main.js - [' + data.path.FRONTEND + 'app/**]',
                data.path.PROD + 'app/scripts/vendor/vendor.bundle.js')
            .then(function () {
                console.log('Build complete');
            })
            .catch(function (err) {
                console.log('Build error');
                console.log(err);
            });
    });
};

