var app = angular.module('tasks', []);

app.controller("TasksController1", function ($scope, $http) {

    $scope.successGetSchoolsCallback = function (response) {
        $scope.tasksList = response.data;
        $scope.errormessage="";
    };

    $scope.errorGetSchoolsCallback = function (error) {
        console.log(error);
        $scope.errormessage="Unable to get list of schools";
    };

    $scope.getTasks = function () {
        $http.get('api/getTasksList').then($scope.successGetSchoolsCallback, $scope.errorGetSchoolsCallback);
    };

    $scope.successGetSchoolCallback = function (response) {
        var data = response.data;
        var lastitem = data[data.length - 1];
        $scope.tasksList.splice(-1, 0, lastitem);
        $scope.errormessage="";
    };

    $scope.errorGetSchoolCallback = function (error) {
        console.log(error);
        $scope.errormessage="Unable to get information on school number "+$scope.inputnumber;
    };

    $scope.successAddSchoolCallback = function (response) {
        $http.get('api/getTasksList').then($scope.successGetSchoolCallback, $scope.errorGetSchoolCallback);
        $scope.errormessage="";
    };

    $scope.errorAddSchoolCallback = function (error) {
        console.log(error);
        $scope.errormessage="Impossible to add new school; check if it's number is unique";
    };

    $scope.addTask = function () {
        var taskEntity ={
            title : $scope.title,
            description : $scope.description
    };
        $http.post('api/createTask', taskEntity).then($scope.successAddSchoolCallback, $scope.errorAddSchoolCallback);
    };

});
