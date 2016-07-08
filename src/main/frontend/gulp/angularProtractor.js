module.exports = function (gulp, data, util, taskName) {
    var gulp = require('gulp'),
        angularProtractor = require('gulp-angular-protractor'),
        connect = require('gulp-connect');

    gulp.task(taskName, function () {

        gulp.src([data.path.E2E_TESTS + 'suites/*.js'])
            .pipe(angularProtractor({
                'configFile': data.path.E2E_TESTS + 'protractor.config.js'
            }))
            .on('end', function () {
                connect.serverClose();
            })
            .on('error', function (e) {
                throw e
            })
    });
};

