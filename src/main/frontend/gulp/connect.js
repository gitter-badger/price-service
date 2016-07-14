module.exports = function (gulp, data, util, taskName) {

    var connect = require('gulp-connect');

    gulp.task(taskName + ':Dev', function () {
        return connect.server({
            root: [data.path.DEV],
            port: 9000
        });
    });

    gulp.task(taskName + ':Styleguide', function () {
        return connect.server({
            root: [data.path.STYLEGUIDE],
            port: 8000
        });
    });
};
