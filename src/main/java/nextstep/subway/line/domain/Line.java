package nextstep.subway.line.domain;

import nextstep.subway.BaseEntity;
import nextstep.subway.station.domain.Station;

import javax.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String color;

    @Embedded
    private LineSections sections = new LineSections();

    public Line() {
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Line(String name, String color, Station upStation, Station downStation, int distance) {
        this.name = name;
        this.color = color;
        this.sections = new LineSections(Arrays.asList(new Section(this, upStation, downStation, distance)));
    }

    public Line(String name, String color, List<Section> sections) {
        this.name = name;
        this.color = color;
        this.sections = new LineSections(sections);
    }

    public void update(Line line) {
        this.name = line.getName();
        this.color = line.getColor();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<Section> getSections() {
        return this.sections.getSections();
    }

    public List<Station> getStations() {
        return this.sections.getStations();
    }

    public void addSection(Station upStation, Station downStation, int distance) {
        this.sections.addSection(new Section(this, upStation, downStation, distance));
    }

    public void removeStation(Station station) {
        if (!this.sections.isRemovable()) {
            throw new RuntimeException();
        }

        this.sections.removeStation(this, station);
    }
}
