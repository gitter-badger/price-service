module.exports = function (gulp, data, util, taskName) {
    var gulp = require('gulp'),
        inject = require('gulp-inject');

    gulp.task(taskName + ':Dev', function () {
        return gulp.src(data.path.DEV + 'index.html')
            .pipe(inject(gulp.src(data.path.DEV + 'app/scripts/vendor/*.js', {read: false}), {relative: true}))
            .pipe(gulp.dest(data.path.DEV));
    });

    gulp.task(taskName + ':Prod', function () {
        return gulp.src(data.path.PROD + 'index.html')
            .pipe(inject(gulp.src(data.path.PROD + 'app/scripts/vendor/*.js', {read: false}), {relative: true}))
            .pipe(gulp.dest(data.path.PROD));
    });
};

