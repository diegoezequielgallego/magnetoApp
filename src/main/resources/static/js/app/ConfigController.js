angular.module('crudApp').controller('ConfigController', ['ConfigService', '$scope',  
	function( service, $scope) {
		
		//se crean todas las variables y objetos dentro de 
		//otro asi a la hora de limpiarlos es mas optimo angular
		$scope.data = {
			config: {},
			properties: {},
			configList: [],
			registroMutantes: [],
			successMessage : '',
			errorMessage : '',
			
			countMutantDna: '4',
			
			buttonRunDisable : false
		}

        $scope.submit = function() {
            console.log('Submitting');
            $scope.data.buttonRunDisable = true;
            $scope.data.successMessage = 'Corriendo ...';
            $scope.data.configList = []; 
            
            var name = 'CyborgAmish';
            var dna = ["ATGTGA", "CATTGC", "TTATGT", "TGAAGG", "CCCCTA", "TCACTG"];
            
            service.isMutant(dna, name).then(
                function (response) {
                	$scope.data.successMessage = 'Es mutante';
                	$scope.data.errorMessage='';
                    $scope.data.buttonRunDisable = false;
                },
                function (errResponse) {
                	$scope.data.errorMessage = 'Alerta: ' + errResponse.data.errorMessage;
                	$scope.data.successMessage='';
                	$scope.data.buttonRunDisable = false;
                }
            );
           
        }
        
        
        $scope.getallmutants = function() {
            service.getallmutants().then(
                function (response) {
                	$scope.data.registroMutantes = response;
                	$scope.data.buttonRunDisable = false;
                },
                function (errResponse) {
                    console.error('Error while removing user ' + id + ', Error :' + errResponse.data);
                    $scope.data.buttonRunDisable = false;
                }
            );
            
        }
        
        
    }


    ]);