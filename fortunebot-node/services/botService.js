var superagent = require('superagent');
var localDbService = require('../services/localDbService');

module.exports = {
	auth: function(code, callback) {
        superagent
            .get('https://slack.com/api/oauth.access')  
            .query({ client_id: process.env.CLIENT_ID, client_secret: process.env.CLIENT_SECRET, code: code })
            .end(callback);
    },

    processEvent: function(event) {
        if (localDbService.getBotAuthInfo() && event.type === 'message' && event.text && !this._isEventFromBotUser(event)) {
        	this._echoText(event);  
        }
    },

    _isEventFromBotUser: function (event) {
        return !event.user || localDbService.getBotAuthInfo().bot.bot_user_id === event.user;
    },

    _echoText: function(event) {
        // TODO: POST https://slack.com/api/chat.postMessage to echo the Event message in text
        // TIP: Use superagent.post
        // TIP: Don't forget to specify the Content-Type as application/x-www-form-urlencoded
     }
};