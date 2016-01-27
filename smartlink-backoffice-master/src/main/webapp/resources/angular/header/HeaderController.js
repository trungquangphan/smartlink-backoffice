/**
 * Created by doomphantom on 25/01/2016.
 */
boAngularApp.controller('HeaderController', ["$scope", "$http", "serviceCommunicator", function ($scope, $http, serviceCommunicator) {
    $scope.menus = {};

    $scope.init = function (data) {
        $scope.menus = JSON.parse(data).menuItems;
    };

}])