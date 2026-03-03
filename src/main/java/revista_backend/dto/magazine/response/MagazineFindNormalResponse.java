package revista_backend.dto.magazine.response;

import lombok.Value;
import revista_backend.models.categories.MagazineCategory;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineTag;

import java.time.LocalDate;
import java.util.List;

@Value
public class MagazineFindNormalResponse {

    Integer id;
    String author;
    String titles;
    String description;
    LocalDate creationDate;
    List<String> tags;
    List<String> categories;

    public MagazineFindNormalResponse(Magazine magazine, List<MagazineTag> magazineTags, List<MagazineCategory> magazineCategories) {
        this.id = magazine.getId();
        this.author = magazine.getUser().getNames() + " " + magazine.getUser().getLastNames();
        this.titles = magazine.getTitle();
        this.description = magazine.getDescription();
        this.creationDate = magazine.getCreationDate();

        this.tags = magazineTags.stream()
                .map(MagazineTag::getDetail)
                .toList();

        this.categories = magazineCategories.stream()
                .map(cat -> cat.getMagazineCategoryType().getName())
                .toList();
    }

}
