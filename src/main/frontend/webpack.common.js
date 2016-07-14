var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var path = require('path');

module.exports = {


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
          
            // components scss
            {
                test: /\.scss$/,
                include: [/components/],
                loader: 'raw!sass'
            },
            {
                test: /\.html$/,
                loader: 'html'
            }
        ]
    },

    plugins: [

        new HtmlWebpackPlugin({
            template: 'index.html'
        })
    ]
}
;
