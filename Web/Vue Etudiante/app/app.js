var myApp = angular.module('myRevisator',
    ['lumx', 'chart.js', 'ngRoute',
        'accueilCtrl',
        'loginCtrl',
        'inscriptionCtrl',
        'tableauDeBordCtrl',
        'listeMatiereCtrl',
        'listeQuizzCtrl',
        'quizzCtrl',
        'statistiqueCtrl',
        'resultatCtrl']).config(function ($httpProvider) {
        $httpProvider.interceptors.push(function ($q, sharedStorageService) {
            return {
                'request': function (config) {


                    config.headers = config.headers || {};
                    config.headers.Authorization = sharedStorageService.get();

                    return config || $q.when(config);
                }
            };
        });
    }).factory('sharedStorageService', function () {
        var savedData = {};

        function set(data) {
            savedData = data;
        }

        function get() {
            return savedData;
        }

        return {
            set: set,
            get: get
        }
    });



