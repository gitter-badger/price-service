module.exports = function (gulp, data, util, taskName) {

    var connect = require('gulp-connect');

    gulp.task(taskName + ':Dev', function () {
        return connect.server({
            root: [data.path.DEV, data.path.FRONTEND],
            port: 9000
        });
    });

    gulp.task(taskName + ':E2e', function () {
        return connect.server({
            root: [data.path.ROOT + '.tmp/frontend', 'node_modules'],
            port: 9001
        });
    });

    gulp.task(taskName + ':Styleguide', function () {
        return connect.server({
            root: [data.path.ROOT + '.styleguide'],
            port: 8000
        });
    });
};
