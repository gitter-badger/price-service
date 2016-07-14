var gulp = require('gulp'),
    config = require('load-gulp-config'),
    runSequence = require('run-sequence');

// Specifics of npm's package.json handling.
// @see https://docs.npmjs.com/files/package.json
var pack = config.util.readJSON('package.json');

config(gulp, {
    // path to task's files, defaults to gulp dir.
    configPath: config.util.path.join('gulp', '*.js'),
    // data passed into config task.
    data: Object.assign({
            path: {
                E2E_TESTS: '../../test/e2e/',
                PROD: '../resources/static/',
                DEV: '.dev/',
                E2E_SOURCE: '.e2e/',
                UNIT_TESTS: 'unitTests/',
                FRONTEND: './',
                STYLEGUIDE: '.styleguide/'
            },
            anyValue: 1,
            anyParams: []
        },
        pack)
});

gulp.task('default', function (callback) {
    runSequence(
        'Dev',
        callback
    );
});

gulp.task('Prod', function (callback) {
    runSequence(
        ['clean:Prod'],
        ['webpack:Prod'],
        callback
    );
});

gulp.task('Dev', function (callback) {
    runSequence(
        ['clean:Dev'],
        ['webpack:Dev'],
        ['connect:Dev'],
        callback
    );
});

gulp.task('Styleguide', function (callback) {
    runSequence(
        ['clean:Styleguide'],
        ['sass:Styleguide'],
        ['styleguide'],
        ['copy:Styleguide', 'connect:Styleguide'],
        callback
    );
});









