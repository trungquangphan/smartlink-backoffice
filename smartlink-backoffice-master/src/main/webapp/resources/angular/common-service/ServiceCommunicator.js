/**
 * Created by doomphantom on 26/01/2016.
 */
boAngularApp.service('serviceCommunicator', function ($http, $q) {
    var service = {};

    service.postData = function (uri, requestData) {
        var defferedObj = $q.defer();
        $http.post(uri, requestData).success(function (data) {
            defferedObj.resolve(data);
        }).error(function () {
            console.log("there was an error!");
        });
        return defferedObj.promise;
    };

    service.getData = function (uri) {
        var deffered = $q.defer();
        $http.get(uri).success(function (data) {
            deffered.resolve(data);
        }).error(function () {
            console.log("there was an error!");
        });
        return deffered.promise;
    }
    return service;
});