
function tvgGoogleAutocompleteDirective (PlaceDetails) {
    return {
        restrict: 'AE',
        controller: 'GoogleAPIAutocompleteCtrl',
        controllerAs: 'googleAutocomplete',
        scope: {
            placeDetails: '=',
            detailsModel: '=',
            fieldIdentifier: '@'
        },
        link: function (scope, element) {
            var $input = element.find('input');
            scope.googlePlace = new google.maps.places.Autocomplete($input[0]);

            google.maps.event.addListener(scope.googlePlace, 'place_changed', function () {
                scope.$apply(function () {
                    scope.placeDetails = JSON.stringify(scope.googlePlace.getPlace(), undefined, 2);
                    scope.detailsModel = PlaceDetails.build(scope.googlePlace.getPlace().address_components, $input.val(), scope.fieldIdentifier);
                });
            });
        }
    };
}

tvgGoogleAutocompleteDirective.$inject = ['PlaceDetails'];

export default tvgGoogleAutocompleteDirective;
