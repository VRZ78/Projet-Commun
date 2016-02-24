/**
 * Created by Victor on 20/02/2016.
 */
angular.module('RevisatorProfApp')
    .controller('MainPageController', function($scope, $http, LxNotificationService){

    $http.get('data/quizlist.json').then(function(response){
        $scope.quizes = response.data;
    },function(reason){
        console.log(reason);
    });

    $scope.nom = 'Nom Prenom'

        $scope.confirm = function(quiz)
        {
                LxNotificationService.success(quiz.name+' a bien été supprimé');

        };



        $scope.removeQuiz = function(array, index){
            array.splice(index, 1);
        }


});