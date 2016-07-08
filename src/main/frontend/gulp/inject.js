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
    
    gulp.task(taskName + ':E2e', function () {
        return gulp.src(data.path.E2E_SOURCE + 'index.html')
            .pipe(inject(gulp.src(data.path.E2E_SOURCE + 'app/scripts/vendor/*.js', {read: false}), {relative: true}))
            .pipe(gulp.dest(data.path.E2E_SOURCE));
    });
};

