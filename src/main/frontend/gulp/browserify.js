module.exports = function (gulp, data, util, taskName) {
    var browserify = require("browserify"),
        babelify = require("babelify"),
        source = require('vinyl-source-stream');

    gulp.task(taskName + ':Dev', function () {

        var input = browserify({debug: true})
            .transform("babelify", {presets: ["es2015"]})
            .require(data.path.FRONTEND + "app/scripts/main.js", {entry: true})
            .bundle()
            .pipe(source('bundleInput.js'))
            .pipe(gulp.dest(data.path.DEV + 'app/scripts'));

        var price = browserify({debug: true})
            .transform("babelify", {presets: ["es2015"]})
            .require(data.path.FRONTEND + "app/scripts/mainPrice.js", {entry: true})
            .bundle()
            .pipe(source('bundlePrice.js'))
            .pipe(gulp.dest(data.path.DEV + 'app/scripts'));

        return Promise.all([input, price]);
    });


    gulp.task(taskName + ':Prod', function () {
        return browserify({debug: true})
            .transform("babelify", {presets: ["es2015"]})
            .require(data.path.FRONTEND + "app/scripts/main.js", {entry: true})
            .bundle()
            .pipe(source('bundle.js'))
            .pipe(gulp.dest(data.path.PROD + 'app/scripts'));
    });
};
