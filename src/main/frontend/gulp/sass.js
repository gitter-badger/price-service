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
            .pipe(gulp.dest(data.path.ROOT + '.tmp/frontend/app'));
    });

    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src('./**/all.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.ROOT + '.styleguide/styles'));
    });
};
