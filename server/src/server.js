var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
const fileUpload = require('express-fileupload');

const errorHandler = require('./middleware/errorHandler');

var api = require('./routes/api');
// var users = require('./controllers/user')
// var meters = require('./controllers/meter')
// var values = require('./controllers/value')

var app = express();

app.use(fileUpload({
    createParentPath: true
}));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false, limit:'50mb' }));

app.use('/api', api);
// app.use('/api/users', users);
// app.use('/api/meters', meters)
// app.use('/api/values', values)
app.use('/uploads', express.static(process.cwd() + '/uploads'))

app.use(errorHandler);

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

app.set('port', process.env.PORT || 3333);

app.listen(app.get('port'), function () {
  console.info('Express server listening on port ' + app.get('port'));
});
// require('dns').lookup(require('os').hostname(), function (err, add, fam) {
//     console.log('addr: '+add);
// })