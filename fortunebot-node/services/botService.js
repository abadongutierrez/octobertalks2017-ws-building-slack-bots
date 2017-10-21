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
        	if (event.text.indexOf('fortune') !== -1) {
             this._tellFortune(event);
            } else {
              this._echoText(event);  
            }
        }
    },

    _isEventFromBotUser: function (event) {
        return !event.user || localDbService.getBotAuthInfo().bot.bot_user_id === event.user;
    },

    _echoText: function(event, callback) {
        this._postMessage(event.channel, event.text);
     },

     _postMessage: function(channel, text) {
        superagent
            .post('https://slack.com/api/chat.postMessage')
            .send({ token: localDbService.getBotAuthInfo().bot.bot_access_token, channel: channel, text: text})
            .set('Content-Type', 'application/x-www-form-urlencoded')
            .end(function (err2, res2) {
                if (err2) {
                    console.log(err2);
                }
            });
     }

     _tellFortune: function(event, callback) {
        // TODO: GET https://helloacm.com/api/fortune/ to get the fortune message
        // TIP: Use superagent.get
        // TIP: When you get the respone from Fortune API call _postMessage method with event.channel
        // and the fortune message comes in the response.body
    }
};