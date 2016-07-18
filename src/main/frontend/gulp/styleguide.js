module.exports = function (gulp, data, util, taskName) {

    var postcss = require('gulp-postcss');

    gulp.task(taskName, function () {
        return gulp.src(data.path.STYLEGUIDE + 'app/all.css').pipe( // any files have to existing
            postcss([
                require('mdcss')({
                    theme: require('mdcss-theme-engage')({
                        logo: 'app/images/icon.png',
                        examples: {
                            css: ['./all.css']
                        }
                    }),
                    destination: data.path.STYLEGUIDE // to create mdcss
                })
            ])
        ).pipe(
            gulp.dest(data.path.STYLEGUIDE) // dest des styles
        );
    });
};
