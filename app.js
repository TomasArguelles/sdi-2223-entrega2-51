let createError = require('http-errors');
let express = require('express');
let path = require('path');
let cookieParser = require('cookie-parser');
let logger = require('morgan');
let app = express();

let rest = require('request');
app.set('rest', rest);

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Credentials", "true");
    res.header("Access-Control-Allow-Methods", "POST, GET, DELETE, UPDATE, PUT");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
    next();
});

let jwt = require('jsonwebtoken');
app.set('jwt', jwt);
let expressSession = require('express-session');

app.use(expressSession({secret: 'abcdefg', resave: true, saveUninitialized: true}));
let crypto = require('crypto');
let fileUpload = require('express-fileupload');
app.use(fileUpload({limits: {fileSize: 50 * 1024 * 1024}, createParentPath: true}));
app.set('uploadPath', __dirname)
app.set('clave', 'abcdefg');
app.set('crypto', crypto);

const {MongoClient} = require("mongodb");
// TODO: Reemplazar por URL mongo Altas
//const url = 'mongodb+srv://admin:sdi@sdi-2223-entrega2-51.287aegb.mongodb.net/?retryWrites=true&w=majority';
const localUrl = 'mongodb://localhost:27017';
app.set('connectionStrings', localUrl);

//---  Logger middleware ------------------------
const customLogger = require('./middlewares/loggerMiddleware');
app.use("/offer/", customLogger.loggerRouter);
app.use("/offers/", customLogger.loggerRouter);

//TODO: Añadir el resto de rutas

// ----------------------------------------------

const userSessionRouter = require('./routes/userSessionRouter');
const adminSessionRouter = require('./routes/adminRouter');

// Auth middleware
app.use("/offers/",userSessionRouter);
app.use("/offer/",userSessionRouter);
app.use("/offers/all",userSessionRouter);
app.use("/offers/buy",userSessionRouter);
app.use("/purchases",userSessionRouter);

// Protección de rutas del usuario administrador
app.use("/admin", adminSessionRouter);
app.use("/logs/delete/all", adminSessionRouter);
app.use("/user/list", adminSessionRouter);

// -- Protección rutas API
const userTokenRouter = require('./routes/api/userTokenRouter');
app.use("/api/v1.0/offers", userTokenRouter);
app.use("/api/v1.0/messages", userTokenRouter);
app.use("/api/v1.0/conversations", userTokenRouter);

let bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

const usersRepository = require("./repositories/usersRepository.js");
usersRepository.init(app, MongoClient);
require("./routes/users.js")(app, usersRepository);

let indexRouter = require('./routes/index');

let commentsRepository = require("./repositories/commentsRepository.js");
commentsRepository.init(app, MongoClient);
require("./routes/comments.js")(app, commentsRepository);

let offersRepository = require("./repositories/offersRepository.js");
offersRepository.init(app, MongoClient);
require("./routes/offers.js")(app, offersRepository, commentsRepository);

// Logs
let logsRepository = require("./repositories/loggingRepository.js");
logsRepository.init(app, MongoClient);
require("./routes/logsRouter.js")(app, logsRepository);

let conversationsRepository = require("./repositories/conversationsRepository");
conversationsRepository.init(app, MongoClient);
let messagesRepository = require("./repositories/messagesRepository");
messagesRepository.init(app, MongoClient);

require("./routes/api/wallapopAPI.js")(app, usersRepository, offersRepository,conversationsRepository,messagesRepository);

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'twig');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
    // set locals, only providing error in development
    console.log("se ha producido un error (app.js)"+ err);
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error');
});

module.exports = app;
