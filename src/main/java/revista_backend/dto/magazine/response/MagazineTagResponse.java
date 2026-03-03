package revista_backend.dto.magazine.response;

import lombok.Value;
import revista_backend.models.magazine.MagazineTag;

@Value
public class MagazineTagResponse {

    Integer id;
    String TagName;

    public MagazineTagResponse(MagazineTag magazineTag) {
        this.id = magazineTag.getId();
        this.TagName = magazineTag.getDetail();
    }
}
