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
import reactor.core.publisher.Flux;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
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
                               @Nullable Float minCompatibilityScore,
                               @Nullable Float maxCompatibilityScore,
                               @Nullable Integer minAge,
                               @Nullable Integer maxAge,
                               @Nullable Integer minHeight,
                               @Nullable Integer maxHeight,
                               @Nullable Float distance,
                               String distanceUnit,
                               Double lat,
                               Double lon) {

        final Criteria criteria = buildCriteria(withPhoto, inContact, favourite, minCompatibilityScore, maxCompatibilityScore,
                minAge, maxAge, minHeight, maxHeight, distance, distanceUnit, lat, lon);


        return reactiveMongoTemplate.find(query(criteria), Person.class);
    }

    @Nullable
    private Criteria buildCriteria(@Nullable Boolean withPhoto,
                                   @Nullable Boolean inContact,
                                   @Nullable Boolean favourite,
                                   @Nullable Float minCompatibilityScore,
                                   @Nullable Float maxCompatibilityScore,
                                   @Nullable Integer minAge,
                                   @Nullable Integer maxAge,
                                   @Nullable Integer minHeight,
                                   @Nullable Integer maxHeight,
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
        Optional.ofNullable(minCompatibilityScore).ifPresent(minScore -> criterias.add(new Criteria(Constants.COMPATIBILITY_SCORE).gte(minScore)));
        Optional.ofNullable(maxCompatibilityScore).ifPresent(maxScore -> criterias.add(new Criteria(Constants.COMPATIBILITY_SCORE).lte(maxScore)));

        //age
        Optional.ofNullable(minAge).ifPresent(min -> criterias.add(new Criteria(Constants.AGE).gte(min)));
        Optional.ofNullable(maxAge).ifPresent(max -> criterias.add(new Criteria(Constants.AGE).lte(max)));

        //height
        Optional.ofNullable(minHeight).ifPresent(min -> criterias.add(new Criteria(Constants.HEIGHT_IN_CM).gte(min)));
        Optional.ofNullable(maxHeight).ifPresent(max -> criterias.add(new Criteria(Constants.HEIGHT_IN_CM).lte(max)));

        //distance - geo spatial
        Optional.ofNullable(distance).ifPresent(distanceValue -> {
            Distance mongoDistance = new Distance(distanceValue, DistanceUnits.valueOf(distanceUnit.toUpperCase()).getMongoMetric());
            criterias.add(new Criteria(Constants.CITY_COORDINATES).nearSphere(new Point(lat, lon)).maxDistance(mongoDistance.getNormalizedValue()));
        });

        return criterias.isEmpty() ? null : new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
    }
}
