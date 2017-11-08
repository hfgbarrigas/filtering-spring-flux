'use strict';

describe('MatchesFactory test', function () {

	var $httpBackend, MatchesFactory;

	beforeEach(module('Filtering'));

	beforeEach(inject(function (_$httpBackend_, _MatchesFactory_) {
		$httpBackend = _$httpBackend_;
		MatchesFactory = _MatchesFactory_;
	}));

	it('Expect factory to exist', function () {
		expect(MatchesFactory).not.toBeNull();
	});

	it('Expect successful backend request', function () {
		//prepare
		$httpBackend.expect('GET', /persons/, undefined, undefined)
			.respond(function (method, url, data, headers, params) {
				return [200, {}];
			});

		//act
		MatchesFactory.getMatches().then(function (data) {
			expect(data).toEqual({});
		});
		$httpBackend.flush();
	});

	it('Expect error on backend request', function () {
		//prepare
		$httpBackend.expect('GET', /persons/, undefined, undefined)
			.respond(function (method, url, data, headers, params) {
				return [500, {error: 'error'}];
			});

		//act
		MatchesFactory.getMatches().catch(function (err) {
			expect(err).toEqual({error: 'error'});
		});
		$httpBackend.flush();
	});

});