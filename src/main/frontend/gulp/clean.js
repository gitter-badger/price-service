module.exports = function (gulp, data, util, taskName) {

    var clean = require('gulp-clean');
    gulp.task(taskName + ':Prod', function () {
        return gulp.src(
            [
                data.path.PROD + '/*'
            ],
            {read: false})
            .pipe(clean({force: true}));
    });

    gulp.task(taskName + ':Dev', function () {
        return gulp.src(
            [
                data.path.DEV
            ],
            {read: false})
            .pipe(clean({force: true}));
    });

    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src(data.path.STYLEGUIDE, {read: false})
            .pipe(clean({force: true}));
    });
};
