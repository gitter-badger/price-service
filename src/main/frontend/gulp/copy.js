module.exports = function (gulp, data, util, taskName) {


    gulp.task(taskName + ':Styleguide', function () {
        return gulp.src([
            'app/images/icon.png',
            'app/fonts/**'
        ], {base: './'})
            .pipe(gulp.dest(data.path.STYLEGUIDE));
    });

    gulp.task(taskName + ':AppProd', function () {
        return gulp.src([
            '!./**/*.ts',
            '!./**/*.scss',
            '!./app/styles/**',
            './app/**',
            './index.html'
        ], {base: './'})
            .pipe(gulp.dest(data.path.PROD));
    });

    gulp.task(taskName + ':AppDev', function () {
        return gulp.src([
            '!./**/*.ts',
            '!./**/*.scss',
            '!./app/styles/**',
            './app/**',
            './index.html'
        ], {base: './'})
            .pipe(gulp.dest(data.path.DEV));
    });

    gulp.task(taskName + ':E2eApp', function () {
        return gulp.src([
            '!./**/*.ts',
            '!./**/*.scss',
            '!./app/styles/**',
            './app/**',
            './index.html'
        ], {base: './'})
            .pipe(gulp.dest(data.path.E2E_SOURCE));
    });

    gulp.task(taskName + ':ScriptsProd', function () {
        return gulp.src([
            'node_modules/core-js/client/shim.min.js',
            'node_modules/systemjs/dist/system.src.js',
            'node_modules/zone.js/dist/zone.js'
        ])
            .pipe(gulp.dest(data.path.PROD + 'app/scripts/vendor'));
    });

    gulp.task(taskName + ':ScriptsDev', function () {
        return gulp.src([
            'node_modules/core-js/client/shim.min.js',
            'node_modules/systemjs/dist/system.src.js',
            'node_modules/zone.js/dist/zone.js'
        ])
            .pipe(gulp.dest(data.path.DEV + 'app/scripts/vendor'));
    });

    gulp.task(taskName + ':E2eScripts', function () {
        return gulp.src([
            'node_modules/core-js/client/shim.min.js',
            'node_modules/systemjs/dist/system.src.js',
            'node_modules/zone.js/dist/zone.js'
        ])
            .pipe(gulp.dest(data.path.E2E_SOURCE + 'app/scripts/vendor'));
    });
};
