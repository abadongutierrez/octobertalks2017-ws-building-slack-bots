var express = require('express');
var router = express.Router();
var localDbService = require('../services/localDbService');
var botService = require('../services/botService');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

// TODO: add router.get('/install', ...) to render views/install.pug

// TODO: add router.get('/thanks', ...) to render views/thanks.pug

// TODO: add router.post('/slack', ...) to render "Ok" text

module.exports = router;
