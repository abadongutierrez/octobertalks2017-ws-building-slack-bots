var express = require('express');
var router = express.Router();
var localDbService = require('../services/localDbService');
var botService = require('../services/botService');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
