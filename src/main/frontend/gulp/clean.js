module.exports = function (gulp, data, util, taskName) {

    var clean = require('gulp-clean');
    gulp.task(taskName + ':Dist', function () {
        return gulp.src(
            [
                data.path.dist + 'app',
                data.path.dist + 'index.html'
            ],
            {read: false})
            .pipe(clean({force: true}));
    });

    gulp.task(taskName + ':E2e', function () {
        return gulp.src(data.path.root + '.tmp', {read: false})
            .pipe(clean({force: true}));
    });

    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src(data.path.root + '.styleguide', {read: false})
            .pipe(clean({force: true}));
    });
};
