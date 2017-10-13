import {forOwn, isUndefined, has} from 'lodash';

function PlaceDetailsFactory () {
    function PlaceDetails(address, zipcode, state, city, fieldIdentifier) {
        this.address = address;
        this.zipcode = zipcode;
        this.state = state;
        this.city = city;
        this.fieldIdentifier = fieldIdentifier;
    }

    function parseData(data, result) {

        var desiredInfo = {
            locality: function (obj) {
                result.city = obj.long_name;
            },
            postal_code: function (obj) {
                result.zipcode = obj.long_name;
            },
            administrative_area_level_1: function (obj) {
                result.state = obj.long_name;
            },
            postal_code_prefix: function (obj) {
                result.zipcode = obj.long_name;
            }
        };

        if (!isUndefined(data)) {
            forOwn(data, function (value) {
                if(has(desiredInfo, value.types[0])){
                    desiredInfo[value.types[0]](value);
                }
            });
        }
    }

    PlaceDetails.build = function (data, address, fieldIdentifier) {
        var result = {
            address: address,
            fieldIdentifier: fieldIdentifier
        };
        parseData(data, result);
        return new PlaceDetails(result.address, result.zipcode, result.state, result.city, result.fieldIdentifier);
    };

    return PlaceDetails;
}

PlaceDetailsFactory.$inject = [];

export default PlaceDetailsFactory;
