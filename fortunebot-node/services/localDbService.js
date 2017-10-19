var obj = {
    setBotAuthInfo: function(info) {
  		obj.info = info;  	
    },

    getBotAuthInfo: function() {
    	return obj.info;
    }
};

module.exports = obj;