var superagent = require('superagent');
var localDbService = require('../services/localDbService');

module.exports = {
	auth: function(code, callback) {
        superagent
            .get('https://slack.com/api/oauth.access')  
            .query({ client_id: process.env.CLIENT_ID, client_secret: process.env.CLIENT_SECRET, code: code })
            .end(callback);
    }
};