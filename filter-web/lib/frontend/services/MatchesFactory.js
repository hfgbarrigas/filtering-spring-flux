import buildUrl from 'build-url';

function MatchesFactory($http, $timeout, $rootScope) {
	function getMatches(withPhoto, inContact, favourite, maxAge, minAge, maxHeight, minHeight, maxCompatScore, minCompatScore,
	                    distance, distanceUnit, latitute, longitude) {

		let url = buildUrl('http://localhost:8080', {
			path: 'persons',
			queryParams: {
				hasPhoto: withPhoto,
				inContact: inContact,
				favourite: favourite,
				maxAge: maxAge,
				minAge: minAge,
				maxHeight: maxHeight,
				minHeight: minHeight,
				minCompatibilityScore: minCompatScore,
				maxCompatibilityScore: maxCompatScore,
				distance: distance,
				distanceUnit: distanceUnit,
				lat: latitute,
				lon: longitude
			}
		});

		return $http({
			method: 'GET',
			url: url
		}).then((success) => {
			return success.data;
		}, (error) => {
			return error;
		});
	}

	return {
		getMatches: getMatches
	}
}

MatchesFactory.$inject = ['$http', '$timeout', '$rootScope'];

export default MatchesFactory;
