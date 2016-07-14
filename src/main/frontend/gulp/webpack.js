module.exports = function (gulp, data, util, taskName) {
    var webpack = require('webpack-stream');


    gulp.task(taskName + ':Prod', function () {
        // run webpack
        return gulp.src('../webpack.prod.js')
            .pipe(webpack(require('../webpackConfig/webpack.prod.js')))
            .pipe(gulp.dest(data.path.PROD));
    });

    gulp.task(taskName + ':Dev', function () {
        // run webpack
        return gulp.src('../webpack.dev.js')
            .pipe(webpack(require('../webpackConfig/webpack.dev.js')))
            .pipe(gulp.dest(data.path.DEV));
    });
};