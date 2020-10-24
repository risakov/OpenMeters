var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');

var api = require('./routes/api');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use('/api', api);

// Catch 404 and forward to error handler
app.use(function (req, res, next) {
    res.status(404);
    res.json({
        error: 'Not found'
    });
    return;
});

// Error handlers
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.json({
        error: err.message
    });
    return;
});

app.set('port', process.env.PORT || 3000);

var server = app.listen(app.get('port'), function () {
  console.info('Express server listening on port ' + app.get('port'));
});