const config = require('./config');
const logger = require('./logger');
const app = require('./api');
const util = require('util');
const http = require('http');

/**
 * Reads the config file and bootstraps the API
 */
var configFile = process.argv[2] || './conf/config.json';

if (!process.argv[2] && process.env.ENVIRONMENT) {
    configFile = `./conf/service-${process.env.ENVIRONMENT}.json`;
}

console.log(`Config file: ` + configFile);

config(configFile, function (err, conf) {

    if (err) {
        util.log('Error loading configuration, terminating process');
        process.exit(75);
    }

    // create the logger object
    const log = logger(conf);

    // Prepare the server
    const server = http.createServer(app(conf, log));

    server.listen(conf.api.port, function listenServer() {
        log.info({
            message: 'Filtering server started',
            name: conf.api.serverName,
            port: conf.api.port
        });
    });

    // set max connections
    server.maxConnections = conf.api.maxConnections;
});