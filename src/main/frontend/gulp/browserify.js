module.exports = function (gulp, data, util, taskName) {
    var browserify = require("browserify"),
        babelify = require("babelify"),
        source = require('vinyl-source-stream');

    gulp.task(taskName + ':Dev', function () {
        return browserify({debug: true})
            .transform("babelify", {presets: ["es2015"]})
            .require(data.path.FRONTEND + "app/main.js", {entry: true})
            .bundle()
            .pipe(source('bundle.js'))
            .pipe(gulp.dest(data.path.DEV + 'app/scripts'));
    });


    gulp.task(taskName + ':Prod', function () {
        return browserify({debug: true})
            .transform("babelify", {presets: ["es2015"]})
            .require(data.path.FRONTEND + "app/main.js", {entry: true})
            .bundle()
            .pipe(source('bundle.js'))
            .pipe(gulp.dest(data.path.PROD + 'app/scripts'));
    });
};
