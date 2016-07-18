module.exports = function (gulp, data, util, taskName) {

    var sass = require('gulp-sass');

    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src('./app/styles/all.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.STYLEGUIDE + 'app'));
    });

    gulp.task(taskName + ':Dev', function () {
        return gulp.src('./app/**/*.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.DEV + 'app'));
    });
};
