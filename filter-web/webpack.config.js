const webpack = require('webpack');
const path = require('path');
const fs = require('fs');
var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: [
        './lib/frontend/app.js'
    ],
    target: 'web',
    module: {
        loaders: [
            {
                test: /\.jsx|.js?$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            },
            {
                test: /\.(eot|svg|ttf|woff|woff2)$/,
                loader: 'file-loader?name=public/fonts/[name].[ext]'
            },
            {
                test: /\.html$/,
                loader: 'html-loader'
            },
            {
                test: /\.css$/,
                loaders: ['style-loader', 'css-loader']
            }
        ]
    },
    resolve: {
        extensions: ['.js', '.scss']
    },
    output: {
        path: path.join(__dirname, '/dist'),
        publicPath: '/filtering/',
        filename: 'webapp-bundle.js'
    },
    devtool: 'source-map-support',
    devServer: {
        contentBase: path.join(__dirname, "dist"),
	    port: 9000
    },
    plugins: [
        new webpack.optimize.OccurrenceOrderPlugin(),
        new webpack.NoEmitOnErrorsPlugin(),
        new webpack.ProvidePlugin({
            // Automatically detect jQuery and $ as free var in modules
            // and inject the jquery library
            // This is required by many jquery plugins
            jQuery: 'jquery',
            $: 'jquery'
        }),
        new webpack.DefinePlugin({
            __HIDE_DATA__: !!process.env.HIDE_DATA
        }),
        new HtmlWebpackPlugin({
            template: './lib/static/index.html',
            inject: true
        })
    ]
};