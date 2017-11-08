package com.hfbarrigas.filter.repositories;

import com.hfbarrigas.filter.model.DistanceUnits;
import com.hfbarrigas.filter.model.internal.Person;
import com.hfbarrigas.filter.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class DefaultCustomReactivePersonRepository implements CustomReactivePersonRepository {

    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public DefaultCustomReactivePersonRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    @Nonnull
    public Flux<Person> findBy(@Nullable Boolean withPhoto,
                               @Nullable Boolean inContact,
                               @Nullable Boolean favourite,
                               @Nonnull Float minCompatibilityScore,
                               @Nonnull Float maxCompatibilityScore,
                               @Nonnull Integer minAge,
                               @Nonnull Integer maxAge,
                               @Nonnull Integer minHeight,
                               @Nonnull Integer maxHeight,
                               @Nullable Float distance,
                               String distanceUnit,
                               Double lat,
                               Double lon) {

        Objects.requireNonNull(minCompatibilityScore, "minCompatibilityScore cannot be null.");
        Objects.requireNonNull(maxCompatibilityScore, "maxCompatibilityScore cannot be null.");
        Objects.requireNonNull(minAge, "minAge cannot be null.");
        Objects.requireNonNull(maxAge, "maxAge cannot be null.");
        Objects.requireNonNull(minHeight, "minHeight cannot be null.");
        Objects.requireNonNull(maxHeight, "maxHeight cannot be null.");

        final Criteria criteria = buildCriteria(withPhoto, inContact, favourite, minCompatibilityScore, maxCompatibilityScore,
                minAge, maxAge, minHeight, maxHeight, distance, distanceUnit, lat, lon);

        return reactiveMongoTemplate.find(query(criteria), Person.class);
    }

    @Nonnull
    private Criteria buildCriteria(@Nullable Boolean withPhoto,
                                   @Nullable Boolean inContact,
                                   @Nullable Boolean favourite,
                                   @Nonnull Float minCompatibilityScore,
                                   @Nonnull Float maxCompatibilityScore,
                                   @Nonnull Integer minAge,
                                   @Nonnull Integer maxAge,
                                   @Nonnull Integer minHeight,
                                   @Nonnull Integer maxHeight,
                                   @Nullable Float distance,
                                   String distanceUnit,
                                   Double lat,
                                   Double lon) {

        final List<Criteria> criterias = new ArrayList<>();

        //hasPhoto
        Optional.ofNullable(withPhoto).ifPresent(bool -> {
            Criteria criteria = new Criteria(Constants.MAIN_PHOTO).exists(bool).andOperator(new Criteria(Constants.MAIN_PHOTO).ne(""));
            criterias.add(bool ? criteria : criteria.not());
        });

        //inContact
        Optional.ofNullable(inContact).ifPresent(bool -> criterias.add(bool ? new Criteria(Constants.CONTACTS_EXCHANGED).gt(0)
                : new Criteria(Constants.CONTACTS_EXCHANGED).is(0))
        );

        //favourite
        Optional.ofNullable(favourite).ifPresent(bool -> criterias.add(new Criteria(Constants.FAVOURITE).is(bool)));

        //compatibility score
        criterias.add(new Criteria(Constants.COMPATIBILITY_SCORE).gte(minCompatibilityScore));
        criterias.add(new Criteria(Constants.COMPATIBILITY_SCORE).lte(maxCompatibilityScore));

        //age
        criterias.add(new Criteria(Constants.AGE).gte(minAge));
        criterias.add(new Criteria(Constants.AGE).lte(maxAge));

        //height
        criterias.add(new Criteria(Constants.HEIGHT_IN_CM).gte(minHeight));
        criterias.add(new Criteria(Constants.HEIGHT_IN_CM).lte(maxHeight));

        //distance - geo spatial
        Optional.ofNullable(distance).ifPresent(distanceValue -> {
            Objects.requireNonNull(distanceUnit, "distanceUnit cannot be null");
            Objects.requireNonNull(lat, "lat cannot be null");
            Objects.requireNonNull(lon, "lon cannot be null");

            Distance mongoDistance = new Distance(distanceValue, DistanceUnits.valueOf(distanceUnit.toUpperCase()).getMongoMetric());
            criterias.add(new Criteria(Constants.CITY_COORDINATES).nearSphere(new Point(lat, lon)).maxDistance(mongoDistance.getNormalizedValue()));
        });

        return new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
    }
}
