'use strict';
(function() {
    angular.module("maintenance", ["ngRoute"]);
})();

(function() {
    angular.module("maintenance").controller("adminController", ["currentSpot", AdminController]);

/* no need if we use ng-route
    function AdminController() {
        this.activeMenu = "";
    }

    AdminController.prototype.isActive = function(menuId) {
        return this.activeMenu == menuId;
    };
    AdminController.prototype.setView = function(view, menuId) {
        this.view = view;
        this.activeMenu = menuId;
    };
    AdminController.prototype.showMain = function() { this.setView("main", ""); };
    AdminController.prototype.showLocations = function() { this.setView("locations", "Locations"); };
    AdminController.prototype.showSites = function() { this.setView("sites", "Sites"); };
*/

    function AdminController(currSpot) {
        this.currSpot = currSpot;
    }
    AdminController.prototype.isActive = function(menuId) {
        return this.currSpot.getActiveMenu() == menuId;
    };
    AdminController.prototype.getTitle = function() {
        return this.currSpot.getTitle();
    };

/*
    angular.module("maintenance").controller("mainController", ["currentSpot", MainController]);
    function MainController(currentSpot) {
        console.log("In MainController before calling currentSpot.setCurrentSpot");
        currentSpot.setCurrentSpot("", "");
    }

    angular.module("maintenance").controller("locationsController", ["currentSpot", LocationsController]);
    function LocationsController(currentSpot) {
        console.log("In LocationsController before calling currentSpot.setCurrentSpot");
        currentSpot.setCurrentSpot("Locations", "Manage the list of diving locations");
    }

    angular.module("maintenance").controller("sitesController", ["currentSpot", SitesController]);
    function SitesController(currentSpot) {
        console.log("In SitesController before calling currentSpot.setCurrentSpot");
        currentSpot.setCurrentSpot("Sites", "Manage the list of dive sites");
    }
*/

    angular.module("maintenance").controller("mainController", MainController);
    function MainController() {}

    angular.module("maintenance").controller("locationsController", LocationsController);
    function LocationsController() {}

    angular.module("maintenance").controller("sitesController", SitesController);
    function SitesController() {}
})();

(function() {
    angular.module("maintenance").factory("currentSpot", currentSpot);
    function currentSpot() {
        var activeMenuId = "";
        var titleText = "";

        return {
            setCurrentSpot: function(menuId, title) {
                activeMenuId = menuId;
                titleText = title;
            },
            getActiveMenu: function() {
                return activeMenuId;
            },
            getTitle: function() {
                return titleText;
            }
        }
    }
})();

(function() {
    angular.module("maintenance").config(function($routeProvider) {
        $routeProvider
            .when("/locations", {
                templateUrl: "/partial/locations.html",
                controller: "locationsController",
                controllerAs: "locations"
            })
            .when("/sites", {
                templateUrl: "/partial/sites.html",
                controller: "sitesController",
                controllerAs: "sites"
            })
            .otherwise({
                templateUrl: "/partial/main.html",
                controller: "mainController",
                controllerAs: "main"
            });
    });
})();

(function() {
    angular.module("maintenance").directive("ywActiveMenu", ["currentSpot", ywActiveMenu]);
    function ywActiveMenu(currSpot) {
        return function(scope, element, attrs) {
            var activeMenuId = attrs["ywActiveMenu"];  // yw-active-menu doesn't work
            var activeTitle = attrs["ywActiveTitle"];
            console.log("function for the directive is called. activeMenuId = " + activeMenuId + " activeTitle = " +
                activeTitle);
            currSpot.setCurrentSpot(activeMenuId, activeTitle);
        };
    }
})();