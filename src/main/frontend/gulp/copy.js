module.exports = function (gulp, data, util, taskName) {


    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src([
            'app/images/icon.png',
            'app/fonts/**'
        ], {base: './'})
            .pipe(gulp.dest(data.path.STYLEGUIDE));
    });
};
