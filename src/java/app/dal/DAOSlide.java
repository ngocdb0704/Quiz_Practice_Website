package app.dal;

import app.entity.Slide;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOSlide extends DBContext {
    private List<Slide> hardCoded;

    public DAOSlide() {
        hardCoded = new ArrayList<>();
        hardCoded.add(new Slide(0, "Test Slide 1", "public/images/placeHolderSlideImg.png", "https://duckduckgo.com/?q=placeholder&t=vivaldi&ia=web", "Active"));
        hardCoded.add(new Slide(0, "Test Slide 2", "public/images/placeHolderSlideImg2.png", "https://dictionary.cambridge.org/dictionary/english/placeholder", "Active"));
        hardCoded.add(new Slide(0, "Test Slide 3", "public/images/placeHolderSlideImg3.png", "https://www.google.com/search?q=placeholder", "Active"));
        hardCoded.add(new Slide(0, "Test Slide 4", "public/images/placeHolderSlideImg4.png", "https://www.bing.com/search?q=placeholder", "Active"));
    }
    
    public List<Slide> getSliderList() {
        return hardCoded;
    }
}
