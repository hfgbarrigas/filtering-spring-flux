'use strict';

describe('MatchDetails tests', function () {

	var MatchDetails;

	beforeEach(module('Filtering'));

	beforeEach(inject(function (_MatchDetails_) {
		MatchDetails = _MatchDetails_;
	}));

	it('Expect MatchDetails to exist', function () {
		expect(MatchDetails).not.toBeNull();
	});

	it('Successfully build a MatchDetails object derived from data', function () {
		//prepare
		var data = {
			mainPhoto: 'x',
			age: 'x',
			jobTitle: 'x',
			heightInCm: 'x',
			compatibilityScore: 'x',
			contactsExchanged: 'x',
			favourite: 'x',
			religion: 'x',
			displayName: 'x',
			city: {
				name: 'x',
				coordinates: {
					lat: 'x',
					lon: 'x'
				}
			}
		};

		//act
		var result = MatchDetails.build(data);

		//asert
		expect(result.imageUrl).toEqual('x');
		expect(result.age).toEqual('x');
		expect(result.jobTitle).toEqual('x');
		expect(result.height).toEqual('x');
		expect(result.compatibilityScore).toEqual('x');
		expect(result.contacts).toEqual('x');
		expect(result.favourite).toEqual('x');
		expect(result.religion).toEqual('x');
		expect(result.displayName).toEqual('x');
		expect(result.city).toEqual('x');
		expect(result.latitude).toEqual('x');
		expect(result.longitude).toEqual('x');
	});

});