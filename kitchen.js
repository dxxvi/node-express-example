'use strict';

var exec        = require('child_process').exec;
var Promise     = require('promise');

var express     = require('express');
var application = express();
var router      = express.Router();

/**
 * @param stdout in this format:
 *        Simple mixer control 'Master',0
 *          Capabilities: pvolume pvolume-joined pswitch pswitch-joined
 *          Playback channels: Mono
 *          Limits: Playback 0 - 42
 *          Mono: Playback 35 [83%] [-10.50dB] [on]
 *
 * @return -1 if error
 */
var _getMasterVolume = function(stdout) {
    var lines = stdout.split("\n").filter(function(line, index, array) {
        return line.indexOf('Playback') > 0 && line.indexOf('[') > 0 && line.indexOf(']') > 0;
    });

    if (lines.length == 0) {
        return -1;
    }

    var ws = lines[0].split(/[\[|\]]/);

    if (ws.length < 2) {
        console.log('lines[0]: ' + lines[0]);
        return -2;
    }

    return parseInt(ws[1].replace(/%/, ''));
};

/**
 * @return a promise
 */
var getMasterVolume = function() {
    return new Promise(function(fulfill, reject) {
        exec("amixer sget Master", function(error, stdout, stderr) {
            if (error !== null) {
                reject(-1);
            }
            else {
                fulfill(_getMasterVolume(stdout));
            }
        });
    });
};

/**
 * @param volume an integer from 0 to 100
 * @return a promise containing the volume after set
 */
var setMasterVolume = function(volume) {
    return new Promise(function(fulfill, reject) {
        exec("amixer sset Master " + volume + "%", function(error, stdout, stderr) {
            getMasterVolume().then(
                function(v) {
                    console.log("1. v is " + v);
                    fulfill(v);
                },
                function(v) {
                    console.log("2. v is " + v);
                    reject(v);
                }
            );
        });
    });
};

/*
getMasterVolume().then(
    function(v) {
        console.log("1. v = " + v);
        process.exit();
    },
    function(v) {
        console.log("2. v = " + v);
        process.exit();
    }
);
*/

setMasterVolume(82);


