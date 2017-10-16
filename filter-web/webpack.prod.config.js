const config = require('./webpack.config.js');
const webpack = require('webpack');
//const StringReplacePlugin = require('string-replace-webpack-plugin');

config.plugins.push(
	new webpack.DefinePlugin({
		"process.env": {
			"NODE_ENV": JSON.stringify("production")
		}
	})
);

config.plugins.push(
	new webpack.optimize.UglifyJsPlugin({
		compress: {
			warnings: false
		}
	})
);

//config.plugins.push(
//	new StringReplacePlugin()
//);
//
//config.module.loaders.push({
//		test: /.js$/,
//		loaders: [StringReplacePlugin.replace({
//			replacements: [{
//				pattern: /(localhost)/ig,
//				replacement: function (match, p1, p2) {
//					return 'filter-service'
//				}
//			}]
//		})]
//	}
//);

module.exports = config;