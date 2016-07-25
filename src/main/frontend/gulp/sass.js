module.exports = function (gulp, data, util, taskName) {

    var sass = require('gulp-sass');

    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src('./app/styles/all.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.STYLEGUIDE + 'app'));
    });

    gulp.task(taskName + ':Dev', function () {
        var input = gulp.src('./app/**/inputAsset.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.DEV + 'app'));

        var price = gulp.src('./app/**/priceAsset.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.DEV + 'app'));
        return Promise.all([price, input])
    });

    gulp.task(taskName + ':Prod', function () {
        var input = gulp.src('./app/**/inputAsset.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.PROD + 'app'));

        var price = gulp.src('./app/**/priceAsset.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(data.path.PROD + 'app'));

        return Promise.all([price, input])
    });
};
