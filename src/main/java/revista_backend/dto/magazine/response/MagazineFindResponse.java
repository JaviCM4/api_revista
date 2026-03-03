package revista_backend.dto.magazine.response;

import lombok.Value;
import revista_backend.models.magazine.Magazine;

import java.time.LocalDate;

@Value
public class MagazineFindResponse {

    Integer id;
    String author;
    String titles;
    String description;
    boolean allowSubscription;
    boolean allowComments;
    boolean allowReactions;
    boolean activeMagazine;
    Integer dailyCost;
    Integer adBlockCost;
    LocalDate creationDate;


    public MagazineFindResponse(Magazine magazine) {
        this.id = magazine.getId();
        this.author = magazine.getUser().getNames() + magazine.getUser().getLastNames();
        this.titles = magazine.getTitle();
        this.description = magazine.getDescription();
        this.allowSubscription = magazine.isAllowSubscription();
        this.allowComments = magazine.isAllowComments();
        this.allowReactions = magazine.isAllowReactions();
        this.activeMagazine = magazine.isActiveMagazine();
        this.dailyCost = magazine.getDailyCost();
        this.adBlockCost = magazine.getAdBlockCost();
        this.creationDate = magazine.getCreationDate();
    }

}
