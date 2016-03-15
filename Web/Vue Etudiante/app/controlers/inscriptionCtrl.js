//inscriptionCtrl

var inscriptionCtrl = angular.module('inscriptionCtrl', []);
inscriptionCtrl.controller('inscriptionCtrl', function ($scope, $http, LxNotificationService) {
    console.log("inscriptionCtrl");

    // Fonction appelé lors du click sur le bouton de création de compte
    $scope.accountConfirmCreation = function (quiz) {
        if ($scope.hasAccountCreationButtonBeenClicked != true) {
            $http.post('http://localhost:8080/inscProf', JSON.stringify($scope.inscProf)).then(function (response) {
                LxNotificationService.success('Votre compte a bien été créé. Merci de vérifier vos mails et de cliquer sur le lien d activation');
                $scope.hasAccountCreationButtonBeenClicked = true;
            }, function () {
                LxNotificationService.error('Impossible de contacter le serveur');
            });
        }
    };

    // Liste des choix pour le select du niveau d'étude
    $scope.year = [
        {name: 'L1'},
        {name: 'L2'},
        {name: 'L3'},
        {name: 'M1'},
        {name: 'M2'},
        {name: 'Doctorat'}
    ];



    // ng-models
    $scope.inscEleve = {
        username: "",
        password: "",
        mail: "",
        school: "",
        birthday: undefined,
        year: ""
    };

    // Vérification du formulaire d'inscritpion
    $scope.isCreateButtonDisabled = function () {
        var isCorrect = true;
        if ($scope.inscEleve.username === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.password === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.confirmPassword === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.mail === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.confirmMail === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.birthday === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.school === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.year === "") {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.password != $scope.inscEleve.confirmPassword) {
            isCorrect = false;
            return true;
        }
        if ($scope.inscEleve.mail != $scope.inscEleve.confirmMail) {
            isCorrect = false;
            return true;
        }
    };

    // Vérification mots de passe et adresses mails identiques
    $scope.onMouseoverCreationButton = function () {
        var isSomethingWrong = false;
        if ($scope.inscEleve.password != $scope.inscEleve.confirmPassword && $scope.inscEleve.password != "" && $scope.inscEleve.confirmPassword != "") {
            LxNotificationService.error("Les mots de passe ne sont pas identiques.");
            isSomethingWrong = true;
        }
        if ($scope.inscEleve.mail != $scope.inscEleve.confirmMail && $scope.inscEleve.mail != "" && $scope.inscEleve.confirmMail != "") {
            LxNotificationService.error("Les adresses mails ne sont pas identiques.");
            isSomethingWrong = true;
        }
        if ($scope.isCreateButtonDisabled() === true) {
            LxNotificationService.error("Les champs ne sont pas tous remplis.");
        }
        if (isSomethingWrong === true) {
            return false;
        }
    }


});