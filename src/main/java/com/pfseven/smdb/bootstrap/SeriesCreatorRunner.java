package com.pfseven.smdb.bootstrap;

import com.pfseven.smdb.base.AbstractLogComponent;
import com.pfseven.smdb.domain.*;
import com.pfseven.smdb.service.EpisodeService;
import com.pfseven.smdb.service.SeasonService;
import com.pfseven.smdb.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class SeriesCreatorRunner extends AbstractLogComponent implements CommandLineRunner {
    private final SeriesService seriesService;
    private final SeasonService seasonService;
    private final EpisodeService episodeService;

    private final String SUMMARY_000 = "A notorious hacker takes an interest in cyber security engineer and vigilante" +
            " styled computer hacker Elliot, while an evil corporation is hacked.";
    private final String SUMMARY_001 = "Elliot is hesitant about fsociety; Elliot is offered a new job; Elliot" +
            " worries about Shayla's association with Fernando Vera; Ollie is given a CD outside of Allsafe; Mr." +
            " Robot makes Elliot talk about his father.";
    private final String SUMMARY_002 = "Monica and the gang introduce Rachel to the \"real world\" after she leaves" +
            " her fiancé at the altar.";
    private final String SUMMARY_003 = "Ross finds out his ex-wife is pregnant. Rachel returns her engagement ring to" +
            " Barry. Monica becomes stressed when her and Ross's parents come to visit.";


    private final String SERIES_INFO_000 = "Young, anti-social computer programmer Elliot works as a cybersecurity" +
            " engineer during the day, but at night he is a vigilante hacker. He is recruited by the mysterious" +
            " leader of an underground group of hackers to join their organization. Elliot's task? Help bring down" +
            " corporate America, including the company he is paid to protect, which presents him with a moral" +
            " dilemma. Although he works for a corporation, his personal beliefs make it hard to resist the urge" +
            " to take down the heads of multinational companies that he believes are running -- and ruining -- the" +
            " world.";
    private final String SERIES_INFO_001 = "Three young men and three young women -- of the BFF kind -- live in the" +
            " same apartment complex and face life and love in New York. They're not above sticking their noses" +
            "into one another's business and swapping romantic partners, which always leads to the kind of hilarity" +
            " average people will never experience -- especially during breakups.";

    private final String TITLE_001 = "Mr. Robot";
    private final String TITLE_002 = "Friends";

    @Override
    public void run(String... args) throws Exception {
        List<Episode> episodes = createBunchOfEpisodes();
        List<Season> seasons = createBunchOfSeasons();
        List<Series> series = createBunchOfSeries();

        logger.info("Created {} series.", seriesService.createAll(series).size());
        logger.info("Created {} episodes.", episodeService.createAll(episodes).size());

        episodes.get(0).setSeason(seasons.get(0));
        episodes.get(1).setSeason(seasons.get(0));
        seasons.get(0).setEpisodes(Set.of(
                episodes.get(0),
                episodes.get(1)));
        series.get(0).setSeasons(Set.of(
                seasons.get(0)));
        seasons.get(0).setSeries(series.get(0));


        episodes.get(2).setSeason(seasons.get(1));
        episodes.get(3).setSeason(seasons.get(1));
        seasons.get(1).setEpisodes(Set.of(
                episodes.get(2),
                episodes.get(3)));
        series.get(1).setSeasons(Set.of(
                seasons.get(1)));
        seasons.get(1).setSeries(series.get(1));


        logger.info("Created {} seasons.", seasonService.createAll(seasons).size());
    }

    private List<Series> createBunchOfSeries() {
        List<Series> series = List.of(
                Series.builder()
                        .title(TITLE_001)
                        .info(SERIES_INFO_000)
                        .seriesCategory(SeriesCategory.TV_SERIES)
                        .genres(Set.of(Genre.DRAMA, Genre.MYSTERY, Genre.THRILLER, Genre.CRIME))
                        .build(),
                Series.builder()
                        .title(TITLE_002)
                        .info(SERIES_INFO_001)
                        .seriesCategory(SeriesCategory.TV_SERIES)
                        .genres(Set.of(Genre.COMEDY))
                        .build()
        );

        return series;
    }

    private List<Season> createBunchOfSeasons() {
        List<Season> seasons = List.of(
                Season.builder()
                        .number(1)
                        .build(),
                Season.builder()
                        .number(1)
                        .build()
        );

        return seasons;
    }

    private List<Episode> createBunchOfEpisodes() {
        List<Episode> episodes = List.of(
                Episode.builder()
                        .title("eps1.0_hellofriend.mov")
                        .summary(SUMMARY_000)
                        .durationInSeconds(3720)
                        .releaseDate(LocalDate.of(2015, 6, 24))
                        .distributor("NBCUniversal Television Distribution")
                        .rating(9.3)
                        .build(),
                Episode.builder()
                        .title("eps1.1_ones-and-zer0es.mpeg")
                        .summary(SUMMARY_001)
                        .durationInSeconds(2880)
                        .releaseDate(LocalDate.of(2015, 7, 1))
                        .distributor("NBCUniversal Television Distribution")
                        .rating(8.6)
                        .build(),
                Episode.builder()
                        .title("The One Where Monica Gets a Roommate")
                        .summary(SUMMARY_002)
                        .durationInSeconds(1320)
                        .releaseDate(LocalDate.of(1994, 9, 22))
                        .distributor("National Broadcasting Company (NBC)")
                        .rating(8.3)
                        .build(),
                Episode.builder()
                        .title("The One with the Sonogram at the End")
                        .summary(SUMMARY_003)
                        .durationInSeconds(1320)
                        .releaseDate(LocalDate.of(1994, 9, 29))
                        .distributor("National Broadcasting Company (NBC)")
                        .rating(8.0)
                        .build()
        );

        return episodes;
    }
}
