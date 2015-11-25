/**
 * Created by zk0z8ok on 11/18/2015.
 */

module.exports = function(config) {
    config.set({
        basePath: "../",
        files: [
            "node_modules/angular/angular.js",
            "node_modules/angular-mocks/angular-mocks.js",
            "public/javascripts/angularjs-tutorial-controllers.js",
            "test/unit/**/*.js"
        ],
        autoWatch: true,
        frameworks: ["jasmine"],
        browsers: ["Chrome"],
        plugins: [ "karma-chrome-launcher", "karma-firefox-launcher", "karma-jasmine" ],
        junitReporter: {
            outputFile: "test_out/unit.xml",
            suite: "unit"
        }
    });
};