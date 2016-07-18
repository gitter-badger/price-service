module.exports = function (gulp, data, util, taskName) {


    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src([
            'app/images/icon.png',
            'app/fonts/**'
        ], {base: './'})
            .pipe(gulp.dest(data.path.STYLEGUIDE));
    });


    gulp.task(taskName + ':Dev', function () {
        return gulp.src([
            '!./**/*.scss',
            '!./**/*.js',
            '!./app/styles/**',
            './app/**',
            './index.html'
        ], {base: './'})
            .pipe(gulp.dest(data.path.DEV));
    });


    gulp.task(taskName + ':Prod', function () {
        return gulp.src([
            '!./**/*.scss',
            '!./**/*.js',
            '!./app/styles/**',
            './app/**',
            './index.html'
        ], {base: './'})
            .pipe(gulp.dest(data.path.PROD));
    });
};
