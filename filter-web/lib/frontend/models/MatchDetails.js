import {forOwn, isUndefined, has} from 'lodash';

function MatchDetailsFactory() {

	function MatchDetails(imageUrl, age, jobTitle, height, compatibilityScore, contacts, favourite, religion, displayName, latitude, longitude) {
		this.imageUrl = imageUrl;
		this.age = age;
		this.jobTitle = jobTitle;
		this.height = height;
		this.compatibilityScore = compatibilityScore;
		this.contacts = contacts;
		this.favourite = favourite;
		this.religion = religion;
		this.displayName = displayName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	function parseData(data, result) {
		result.imageUrl = data.mainPhoto;
		result.age = data.age;
		result.jobTitle = data.jobTitle;
		result.height = data.heightInCm;
		result.compatibilityScore = data.compatibilityScore;
		result.contacts = data.contactsExchanged;
		result.favourite = data.favourite;
		result.religion = data.religion;
		result.displayName = data.displayName;
		result.city = data.city.name;
		result.latitude = data.city.coordinates.lat;
		result.longitude = data.city.coordinates.lon;
	}

	MatchDetails.build = function (data) {
		let result = {};
		parseData(data, result);
		return new MatchDetails(result.imageUrl, result.age, result.jobTitle, result.height, result.compatibilityScore,
			result.contacts, result.favourite, result.religion, result.displayName, result.city, result.latitude, result.longitude);
	};

	return MatchDetails;
}

MatchDetailsFactory.$inject = [];

export default MatchDetailsFactory;
