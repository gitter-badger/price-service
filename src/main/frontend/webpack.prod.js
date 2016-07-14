var webpackMerge = require('webpack-merge');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var commonConfig = require('./webpack.common.js');

const ENV = process.env.NODE_ENV = process.env.ENV = 'production';


module.exports = webpackMerge(commonConfig, {
    devtool: 'cheap-module-eval-source-map', //--> source-map for prod

    output: {
        publicPath: '',
        filename: 'app/[name].js',
        chunkFilename: '[id].chunk.js'
    },

    htmlLoader: {
        minimize: false // workaround for ng2
    },

    plugins: [
        new ExtractTextPlugin('app/[name].css')
    ],

    devServer: {
        historyApiFallback: true,
        stats: 'minimal'
    }
});
