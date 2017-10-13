
function GoogleAPIPlacesFactory ($q) {
    var autocompleteService = new google.maps.places.AutocompleteService();
    var placesService = new google.maps.places.PlacesService();

    function parsePredictionsResults(predictions, status) {
        if (status != google.maps.places.PlacesServiceStatus.OK) {
            return [];
        } else {
            return predictions;
        }
    }

    function parsePlaceDetails(place, status) {
        if (status != google.maps.places.PlacesServiceStatus.OK) {
            return {};
        } else {
            return place;
        }
    }

    function getPredictions(text) {
        var deferred = $q.defer();
        if (text && autocompleteService) {
            deferred.resolve(autocompleteService.getQueryPredictions({input: text}, parsePredictionsResults));
        } else {
            deferred.reject('Something went wrong get predictions list!');
        }
        return deferred.promise;
    }

    function getPlaceDetails(placeId) {
        var deferred = $q.defer();
        if (placeId && placesService) {
            deferred.resolve(placesService.getDetails({placeId: placeId}, parsePlaceDetails));
        } else {
            deferred.reject('Something went wrong fetching place details!');
        }
        return deferred.promise;
    }

    return {
        getPredictions: getPredictions,
        getPlaceDetails: getPlaceDetails
    };
}

GoogleAPIPlacesFactory.$inject = ['$q'];

export default GoogleAPIPlacesFactory;
