module.exports = function (gulp, data, util, taskName) {
    var gulp = require('gulp'),
        angularProtractor = require('gulp-angular-protractor'),
        connect = require('gulp-connect');

    gulp.task(taskName, function () {

        gulp.src([data.path.E2E + 'suites/*.js'])
            .pipe(angularProtractor({
                'configFile': data.path.E2E + 'protractor.config.js'
            }))
            .on('end', function () {
                connect.serverClose();
            })
            .on('error', function (e) {
                throw e
            })
    });
};

