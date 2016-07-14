var webpackMerge = require('webpack-merge');
var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var commonConfig = require('./webpack.common.js');

const ENV = process.env.NODE_ENV = process.env.ENV = 'development';


module.exports = webpackMerge(commonConfig, {
    devtool: 'cheap-module-eval-source-map',

    entry: {
        'polyfills': './app/polyfills.ts',
        'vendor': './app/vendor.ts',
        'app': './app/main.ts'
    },

    output: {
        filename: '[name].js',
        chunkFilename: '[id].chunk.js'
    },

    htmlLoader: {
        minimize: false // workaround for ng2
    },

    plugins: [
        new ExtractTextPlugin('[name].css'),
        new webpack.optimize.CommonsChunkPlugin({
            name: ['app', 'vendor', 'polyfills']
        })
    ],
    module: {
        loaders: [
            {
                test: /\.(png|jpe?g|gif|svg([?].+)?|woff([?].+)?|woff2|ttf([?].+)?|eot([?].+)?|ico)$/,
                loader: 'file?name=assets/[name].[hash].[ext]'
            },
            // global scss
            {
                test: /all.scss$/,
                loader: ExtractTextPlugin.extract('style', 'css!sass')
            }
        ]
    },

    devServer: {
        historyApiFallback: true,
        stats: 'minimal'
    }
});
