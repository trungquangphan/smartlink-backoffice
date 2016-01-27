/**
 * Created by doomphantom on 25/01/2016.
 */
boAngularApp.controller('HeaderController', ["$scope", "$http", "serviceCommunicator", function ($scope, $http, serviceCommunicator) {
    $scope.menus = {};

    $scope.init = function (data) {
        $scope.menus = JSON.parse(data).menuItems;
    };

    $scope.childMenuItemHaveAuthority = function (item) {
        if (item.items.length == 0)
            return false;

        for (var i = 0; i < item.items.length; i++) {
            if (item.items[i].hasAuthority) {
                return true;
            }
        }
    };

}])