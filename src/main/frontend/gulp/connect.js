module.exports = function (gulp, data, util, taskName) {

    var connect = require('gulp-connect');

    gulp.task(taskName + ':Dev', function () {
        return connect.server({
            root: [data.path.DEV],
            port: 9000
        });
    });

    gulp.task(taskName + ':E2e', function () {
        return connect.server({
            root: [data.path.E2E_SOURCE, data.path.FRONTEND],
            port: 9001
        });
    });

    gulp.task(taskName + ':Styleguide', function () {
        return connect.server({
            root: [data.path.STYLEGUIDE],
            port: 8000
        });
    });

    gulp.task(taskName + ':UnitTests', function () {
        return connect.server({
            root: [data.path.UNIT_TESTS, data.path.FRONTEND],
            port: 9011
        });
    });
};
