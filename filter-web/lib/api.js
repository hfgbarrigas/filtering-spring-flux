const express = require('express');
const path = require('path');
const compression = require('compression');

module.exports = (config, log) => {
	const app = express();

	app.disable('x-powered-by');

	app.get('/filtering/health', (req, res) => res.json({status: 'UP'}));

	// generic error handler
	app.use((err, req, res, next) => {
		log.error({
			msg: err,
			stack: err.stack,
			message: 'Service fail'
		});
		res.status(500).send({error: 'Something went wrong, call a monkey!'});
	});

	app.use(compression());

	//static files
	app.use('/filtering', express.static(path.join(__dirname, '../dist')));

	//default to index
	app.get('/filtering', function (req, res) {
		res.sendFile(path.join(__dirname, '../dist/index.html'));
	});

	return app;
};