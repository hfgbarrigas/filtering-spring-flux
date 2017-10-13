const bunyan = require('bunyan');

function getBunyanStreams(streams) {
	if (streams.length === 0) {
		streams.push({
			level: 'info',
			stream: process.stdout
		});
	}

	return streams;
}

module.exports = (config) => {
	return bunyan.createLogger({
		name: config.bunyan.name,
		streams: getBunyanStreams(config.bunyan.streams)
	});
};
