var webpackMerge = require('webpack-merge');
var webpack = require('webpack');

var ExtractTextPlugin = require('extract-text-webpack-plugin');
var commonConfig = require('./webpack.common.js');

const ENV = process.env.NODE_ENV = process.env.ENV = 'production';


module.exports = webpackMerge(commonConfig, {
    devtool: 'cheap-module-eval-source-map', //--> source-map for prod
    entry: {
        'vendor': './app/prod.vendor.ts',
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
        new webpack.optimize.CommonsChunkPlugin({
            name: ['app', 'vendor']
        })
    ],
    module: {
        loaders: [
            {
                test: /\.(png|jpe?g|gif|svg([?].+)?|woff([?].+)?|woff2|ttf([?].+)?|eot([?].+)?|ico)$/,
                loader: 'url?name=assets/[name].[hash].[ext]'
            },
            // global scss
            {
                test: /all.scss$/,
                loader: 'style!css!sass'
            }
        ]
    },

    devServer: {
        historyApiFallback: true,
        stats: 'minimal'
    }
});
