var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var helpers = require('./helpers');

module.exports = {
    entry: {
        'polyfills': './app/polyfills.ts',
        'vendor': './app/vendor.ts',
        'app': './app/main.ts'
    },

    resolve: {
        modulesDirectories: ['node_modules', 'app'],
        extensions: ['', '.js', '.ts', '.html', '.scss', '.css']
    },

    module: {
        loaders: [
            {
                test: /\.ts$/,
                loaders: ['ts', 'angular2-template-loader']
            },
            {
                test: /\.scss$/,
                loader: 'raw!sass'
            },
            {
                test: /\.html$/,
                loader: 'html'
            },
            {
                test: /\.(png|jpe?g|gif|svg([?].+)?|woff([?].+)?|woff2|ttf([?].+)?|eot([?].+)?|ico)$/,
                loader: 'file?name=assets/[name].[hash].[ext]'
            }
        ]
    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin({
            name: ['app', 'vendor', 'polyfills']
        }),

        new HtmlWebpackPlugin({
            template: 'index.html'
        })
    ]
};
