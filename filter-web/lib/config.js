const fs = require('fs');
const util = require('util');

module.exports = (file, callbackFunc) => {
	util.log('Reading config file: ' + file);

	return fs.readFile(file, (err, data) => {
		callbackFunc(err, JSON.parse(data));
	});
};

