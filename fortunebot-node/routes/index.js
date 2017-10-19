var express = require('express');
var router = express.Router();
var localDbService = require('../services/localDbService');
var botService = require('../services/botService');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/install', function(req, res, next) {
  res.render('install', { title: 'FortuneBot' });
});

router.get('/thanks', function(req, res, next) {
  res.render('thanks', { title: 'Thanks!'});
});

router.post('/slack', function(req, res, next) {
  res.send("Ok");
});

module.exports = router;
