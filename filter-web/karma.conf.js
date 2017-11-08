var istanbul = require('browserify-istanbul');
var path = require('path');

module.exports = function (config) {
	config.set({

		basePath: '.',
		frameworks: ['jasmine', 'browserify'],
		browserify: {
			extensions: ['.js'],
			transform: ["babelify", "stringify",
				istanbul({
					ignore: ['**/*.css'],
					embedSource: true
				})],
			debug: true,
			plugin: ["browserify-ignore-css"]
		},
		files: [
			'node_modules/jquery/dist/jquery.min.js',
			'node_modules/angular/angular.min.js',
			'node_modules/angular-mocks/angular-mocks.js',
			'lib/frontend/**/*.js',
			'test/**/**/*.js'
		],
		exclude: [
			'lib/frontend/*.html',
			'lib/frontend/templates/*.html'
		],
		preprocessors: {
			'lib/frontend/**/*.js': ['browserify']
		},

		reporters: ["coverage-istanbul", "progress"],

		// any of these options are valid: https://github.com/istanbuljs/istanbul-api/blob/47b7803fbf7ca2fb4e4a15f3813a8884891ba272/lib/config.js#L33-L38
		coverageIstanbulReporter: {

			// reports can be any that are listed here: https://github.com/istanbuljs/istanbul-reports/tree/590e6b0089f67b723a1fdf57bc7ccc080ff189d7/lib
			reports: ['html', 'lcovonly', 'json'],

			// base output directory. If you include %browser% in the path it will be replaced with the karma browser name
			dir: path.join(__dirname, 'test/coverage'),

			// if using webpack and pre-loaders, work around webpack breaking the source path
			fixWebpackSourcePaths: true,

			// stop istanbul outputting messages like `File [${filename}] ignored, nothing could be mapped`
			skipFilesWithNoCoverage: true,

			// Most reporters accept additional config options. You can pass these through the `report-config` option
			'report-config': {
				// all options available at: https://github.com/istanbuljs/istanbul-reports/blob/590e6b0089f67b723a1fdf57bc7ccc080ff189d7/lib/html/index.js#L135-L137
				html: {
					// outputs the report in ./coverage/html
					subdir: 'html'
				}
			},

			// enforce percentage thresholds
			// anything under these percentages will cause karma to fail with an exit code of 1 if not running in watch mode
			thresholds: {
				emitWarning: false, // set to `true` to not fail the test command when thresholds are not met
				global: { // thresholds for all files
					statements: 100,
					lines: 100,
					branches: 81,
					functions: 100
				},
				each: { // thresholds per file
					statements: 100,
					lines: 100,
					branches: 75,
					functions: 100,
					overrides: {}
				}
			}
		},
		port: 9876,
		colors: true,
		logLevel: config.LOG_INFO,
		autoWatch: false,
		browsers: ['PhantomJS2'],
		singleRun: true,
		concurrency: Infinity,
		babelPreprocessor: {
			options: {
				presets: ['es2015']
			}
		}
	})
};
