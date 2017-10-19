var express = require('express');
var router = express.Router();
var localDbService = require('../services/localDbService');
var botService = require('../services/botService');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/install', function(req, res, next) {
  res.render('install', { title: 'FortuneBot', clientId: process.env.CLIENT_ID});
});

router.get('/thanks', function(req, res, next) {
  botService.auth(req.query.code, function (err, resService) {
    if (err) {
      throw err;
    }
    localDbService.setBotAuthInfo(resService.body);
    res.render('thanks', { title: 'Thanks!'});
  });
});

router.post('/slack', function(req, res, next) {
  res.send("Ok");
});

module.exports = router;
