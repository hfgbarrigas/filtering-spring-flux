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
        contentBase: './dist',
        hot: true
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
            title: 'Filtering',
            template: './lib/static/index.html',
            inject: true
        })
    ]
};