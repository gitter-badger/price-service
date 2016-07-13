module.exports = function (gulp, data, util, taskName) {
    var webpack = require('webpack-stream');


    gulp.task(taskName + ':Prod', function () {
        // run webpack
        return gulp.src('../webpack.prod.js')
            .pipe(webpack(require('../webpack.config.js')))
            .pipe(gulp.dest(data.path.PROD));
    });
};