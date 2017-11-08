import buildUrl from 'build-url';

function MatchesFactory($http) {
	function getMatches(withPhoto, inContact, favourite, maxAge, minAge, maxHeight, minHeight, maxCompatScore, minCompatScore,
	                    distance, distanceUnit, latitude, longitude) {

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
				lat: latitude,
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

MatchesFactory.$inject = ['$http'];

export default MatchesFactory;
