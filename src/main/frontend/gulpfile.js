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
                ROOT: '../../../',
                E2E: '../../test/e2e/',
                PROD: '../../../src/main/resources/static/',
                DEV: '../../../src/main/frontend/.dev/',
                FRONTEND: '../../../src/main/frontend/'
            },
            anyValue: 1,
            anyParams: []
        },
        pack)
});


gulp.task('buildProd', function (callback) {
    runSequence(
        ['clean:Prod', 'transpiling'],
        ['systemjs-builder', 'sass:Prod', 'copy:AppProd', 'copy:ScriptsProd'],
        'inject:Prod',
        callback
    );
});

gulp.task('default', function (callback) {
    runSequence(
        ['clean:Dev', 'transpiling'],
        ['sass:Dev', 'copy:AppDev', 'copy:ScriptsDev'],
        'inject:Dev',
        'connect:Dev',
        callback
    );
});

gulp.task('serveStyleGuide', function (callback) {
    runSequence(
        ['clean:Styleguide'],
        ['sass:Styleguide'],
        ['concat:Styleguide'],
        ['styleguide'],
        ['copy:Styleguide', 'connect:Styleguide'],
        callback
    );
});

gulp.task('e2e', function (callback) {

    runSequence(
        'clean:E2e',
        ['transpiling', 'sass:E2e'],
        ['copy:E2eApp', 'copy:E2eScripts'],
        'connect:E2e',
        'angularProtractor',
        callback
    );
});








