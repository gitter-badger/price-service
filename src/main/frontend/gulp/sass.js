module.exports = function (gulp, data, util, taskName) {

    var sass = require('gulp-sass');

    gulp.task(taskName + ':Prod', function () {
        return gulp.src('./app/**/*.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.PROD + 'app'));
    });

    gulp.task(taskName + ':Dev', function () {
        return gulp.src('./app/**/*.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.DEV + 'app'));
    });

    gulp.task(taskName + ':E2e', function () {
        return gulp.src('./app/**/*.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.E2E_SOURCE + 'app'));
    });

    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src('./app/styles/all.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.STYLEGUIDE + 'app'));
    });
};
