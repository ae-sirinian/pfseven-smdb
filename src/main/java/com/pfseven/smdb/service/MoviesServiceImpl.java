package com.pfseven.smdb.service;

import com.pfseven.smdb.domain.Movie;
import com.pfseven.smdb.domain.Occupation;
import com.pfseven.smdb.domain.Person;
import com.pfseven.smdb.domain.RoleType;
import com.pfseven.smdb.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoviesServiceImpl extends BaseServiceImpl<Movie> implements MovieService {
    private final MovieRepository movieRepository;
    private PersonService personService;

    @Autowired
    @Lazy
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    JpaRepository<Movie, Long> getRepository() {
        return movieRepository;
    }

    @Override
    public Movie get(String title) {
        return movieRepository.getMovieByTitle(title);
    }

    @Override
    public Movie find(String title) {
        return movieRepository.findMovieByTitle(title);
    }

    @Override
    public void addPersonToMovieOccupation(Long personId, Long movieId, String roleType) {
        Person person = personService.find(personId);
        Movie movie = find(movieId);
        Occupation occupation = Occupation.builder()
                .person(person)
                .videoEntertainment(movie)
                .occupation(RoleType.valueOf(roleType))
                .build();

        addOccupation(person, movie, occupation);
    }

    @Override
    public void addPersonToMovieOccupation(String firstName, String lastName, String title, String roleType) {
        Person person = personService.find(firstName, lastName);
        Movie movie = find(title);
        Occupation occupation = Occupation.builder()
                .person(person)
                .videoEntertainment(movie)
                .occupation(RoleType.valueOf(roleType))
                .build();

        addOccupation(person, movie, occupation);
    }

    @Override
    public void removePersonToMovieOccupation(Long personId, Long movieId, String roleType) {
        Person person = personService.find(personId);
        Movie movie = find(movieId);
        Occupation occupation = Occupation.builder()
                .person(person)
                .videoEntertainment(movie)
                .occupation(RoleType.valueOf(roleType))
                .build();

        removeOccupation(person, movie, occupation);
    }

    @Override
    public void removePersonToMovieOccupation(String firstName, String lastName, String title, String roleType) {
        Person person = personService.find(firstName, lastName);
        Movie movie = find(title);
        Occupation occupation = Occupation.builder()
                .person(person)
                .videoEntertainment(movie)
                .occupation(RoleType.valueOf(roleType))
                .build();

        removeOccupation(person, movie, occupation);
    }

    @Override
    public void addOccupation(Movie movie, Occupation occupation) {
        if (isNull(occupation) || isNull(movie)) {
            return;
        }

        movie.addOccupation(occupation);
        update(movie);

        logger.debug("Occupation[{}] added to Movie[{}]", occupation, movie);
    }

    @Override
    public void removeOccupation(Movie movie, Occupation occupation) {
        if (isNull(occupation) || isNull(movie)) {
            return;
        }

        movie.removeOccupation(occupation);
        update(movie);

        logger.debug("Occupation[{}] removed to Movie[{}]", occupation, movie);
    }

    private void addOccupation(Person person, Movie movie, Occupation occupation) {
        personService.addOccupation(person, occupation);
        addOccupation(movie, occupation);
    }

    private void removeOccupation(Person person, Movie movie, Occupation occupation) {
        personService.removeOccupation(person, occupation);
        removeOccupation(movie, occupation);
    }

    private boolean isNull(Object object) {
        return object == null;
    }
}