app.controller('devicesController', function($scope, $http, $routeParams) {
    $scope.devices = [];
    $scope.selectedDevice = null;

    $scope.setSelected = function(device) {
       $scope.selectedDevice = device;
    };

    $http.get('/devices').success(function (devices, status, headers, config) {
        $scope.devices = devices;
        angular.forEach(devices, function(device) {
            $http.get('/devices/' + device.id + "/readings")
                .success(function (readings, status, headers, config) {
                    device.chart = {
                        series: ['Humidity', 'Temperature'],
                        labels: readings.map(function(reading) {
                            return reading.created;
                        }),
                        data: [
                            readings.map(function(reading) {
                              return reading.humidity;
                            }),
                            readings.map(function(reading) {
                              return reading.temperature;
                            })
                        ]
                    };
                })
                .error(function (data, status, headers, config) {
                    $scope.errorMessage = "Can't retrieve devices " + data;
                    return null;
                });
        });

        if( $scope.selectedDevice == null && $scope.devices.length > 0) {
            $scope.selectedDevice = $scope.devices[0];
        }
    }).error(function (data, status, headers, config) {
        $scope.errorMessage = "Can't retrieve devices " + data;
    });
});