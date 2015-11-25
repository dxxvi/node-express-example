"use strict";

var _       = require("underscore");
var utils   = require("./utilities");
var express = require("express");

var application = express();

var router = express.Router();

/*
 * router.use is called "middleware", it accepts /user/:name/age/:age although we don't write /age/:age, but it doesn't
 * build the request.params object from the path provided by the browser.
 */
router.use("/user/:name", function(request, response, next) {
    // log everything related to request and response
    utils.explore("request object", request);
    console.log("name in middleware is: " + request.params.name);
    if (!request.params.name.substr(0, 1).match(/[a-z]/i)) {  // not start with a letter
        response.json({message: "name should start with a letter"});
    }
    else {
        next();                        // go to the next routes
    }
});

router.use("/book/:title", function(request, response, next) {
    console.log("/book/:title");
    next();
});

/*
router.get("/", function(request, response) {
    response.sendFile(__dirname + "/public/index.html");
});
router.get("/index-controller-as.html", function(request, response) {
    response.sendFile(__dirname + "/public/index-controller-as.html");
});
router.get("/site-admin.html", function(request, response) {
    response.sendFile(__dirname + "/public/site-admin.html");
});
*/

/*
 * this is the real router and it doesn't accept parts we don't declare, e.g. /user/:name/age/:age
 */
router.get("/user/:name", function(request, response) {
    console.log("name in router is: " + request.params.name);
    response.json({message: "this is the home page"});
});

router.get("/book/:title", function(request, response) {
    response.json({message: "your book title is " + request.params.title});
});

router.get("/dives", function(request, response) {
    response.json([
        { site: 'Abu', location: 'Egypt', depth: 72, time: 44 },
        { site: 'Ponte', location: 'Mauritius', depth: 55, time: 28 },
        { site: 'Molnar', location: 'Hungary', depth: 97, time: 52 },
        { site: 'Himalya', location: 'Japan', depth: 1, time: 19}
    ]);
});

//application.use("/", router);
application.use("/", express.static(__dirname + "/public"));
application.use("/api", router);
application.use("/node_modules", express.static(__dirname + "/node_modules"));
application.use("/partial", express.static(__dirname + "/partial"));
application.use("*", function(request, response) {
    response.status(404).send("404: " + request.originalUrl + " not found");
});

application.listen(3000, function() {
    console.log("listening at port 3000.");
});
