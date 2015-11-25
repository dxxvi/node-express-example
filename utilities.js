var _ = require("underscore");
var exports = module.exports = {
    explore: function(message, object) {
        var objectToString = function(obj, level) {
            var s = "{" + (level > 1 ? " " : "\n");
            var first = true;
            _.each(obj, function(value, key) {
                if (first) {
                    first = false;
                }
                else {
                    s += "," + (level == 1? "\n" : " ");
                }
                s += key + ": ";
                if (typeof value == "function") {
                    s += "__function__";
                }
                else if (typeof value == "object") {
                    s += level == 1 ? objectToString(value, level + 1) : "__object__";
                }
                else {
                    s += value;
                }
            });
            return s + (level > 1 ? " }" : "\n}");
        };
        console.log(message + ": " + objectToString(object, 1));
    }
};