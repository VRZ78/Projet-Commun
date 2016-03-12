/**
 * Created by Victor on 21/02/2016.
 */
angular.module('RevisatorProfApp')
    .controller('LoginController', function($scope, $http, LxNotificationService){


        $scope.accountConfirmCreation = function(quiz)
        {
            LxNotificationService.success('Votre compte a bien été créé. Merci de vérifier vos mails et de cliquer sur le lien d activation');
            $scope.isCreateButtonDisabled = true;
        };

        $scope.isCreateButtonDisabled = false;

    });