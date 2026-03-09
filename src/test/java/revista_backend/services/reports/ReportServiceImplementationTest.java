package revista_backend.services.reports;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.reports.five.FiveReportResponse;
import revista_backend.dto.reports.four.FourthReportResponse;
import revista_backend.dto.reports.one_nine.OneNineReportResponse;
import revista_backend.dto.reports.seven.SeventhReportResponse;
import revista_backend.dto.reports.six.SixReportResponse;
import revista_backend.dto.reports.three.ThreeReportResponse;
import revista_backend.dto.reports.two_eight.TwoEigthReportResponse;
import revista_backend.models.advertisement.AdBlock;
import revista_backend.models.advertisement.Advertisement;
import revista_backend.models.types.AdType;
import revista_backend.models.advertisement.ExtraContent;
import revista_backend.models.magazine.*;
import revista_backend.models.user.User;
import revista_backend.repositories.advertisement.AdBlockRepository;
import revista_backend.repositories.advertisement.AdvertisementRepository;
import revista_backend.repositories.advertisement.ExtraContentRepository;
import revista_backend.repositories.magazine.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplementationTest {

    private static final int USER_ID = 1;
    private static final int MAGAZINE_ID = 10;
    private static final int AD_ID = 5;
    private static final String MAGAZINE_TITLE = "Tech Weekly";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final String COMMENT_TEXT = "Great article!";
    private static final String AD_TYPE_NAME = "Banner";
    private static final String RESOURCE_URL = "http://example.com/img.png";
    private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);
    private static final int PAYMENT = 100;

    @Mock private InteractionCommentRepository interactionCommentRepository;
    @Mock private MagazineSubscriptionRepository magazineSubscriptionRepository;
    @Mock private InteractionLikeRepository interactionLikeRepository;
    @Mock private MagazinePaymentRepository magazinePaymentRepository;
    @Mock private AdvertisementRepository advertisementRepository;
    @Mock private ExtraContentRepository extraContentRepository;
    @Mock private AdBlockRepository adBlockRepository;
    @Mock private MagazineRepository magazineRepository;

    @InjectMocks
    private ReportServiceImplementation reportService;

    // ────────────────── Reporte 1 ──────────────────

    @Test
    void testFirstReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        InteractionComment comment = createComment(magazine, createUser(), COMMENT_TEXT, START_DATE);

        when(interactionCommentRepository.findByCommentDateBetweenAndMagazine_User_Id(START_DATE, END_DATE, USER_ID))
                .thenReturn(List.of(comment));

        List<OneNineReportResponse> result = spy.firstEditorAdminReport(START_DATE, END_DATE, USER_ID);

        verify(interactionCommentRepository).findByCommentDateBetweenAndMagazine_User_Id(START_DATE, END_DATE, USER_ID);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine()),
                () -> assertEquals(MAGAZINE_TITLE, result.get(0).getNameMagazine()),
                () -> assertEquals(1, result.get(0).getComments().size())
        );
    }

    @Test
    void testFirstReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        InteractionComment comment = createComment(magazine, createUser(), COMMENT_TEXT, START_DATE);

        when(interactionCommentRepository.findByMagazine_User_Id(USER_ID))
                .thenReturn(List.of(comment));

        List<OneNineReportResponse> result = spy.firstEditorAdminReport(null, null, USER_ID);

        verify(interactionCommentRepository).findByMagazine_User_Id(USER_ID);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine())
        );
    }

    // ────────────────── Reporte 2 ──────────────────

    @Test
    void testSecondReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        MagazineSubscription subscription = createSubscription(magazine, createUser(), START_DATE);

        when(magazineSubscriptionRepository.findBySubscriptionDateBetweenAndMagazine_User_Id(START_DATE, END_DATE, USER_ID))
                .thenReturn(List.of(subscription));

        List<TwoEigthReportResponse> result = spy.secondEditorAdminReport(START_DATE, END_DATE, USER_ID);

        verify(magazineSubscriptionRepository).findBySubscriptionDateBetweenAndMagazine_User_Id(START_DATE, END_DATE, USER_ID);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine()),
                () -> assertEquals(MAGAZINE_TITLE, result.get(0).getNameMagazine()),
                () -> assertEquals(1, result.get(0).getSubscriptions().size())
        );
    }

    @Test
    void testSecondReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        MagazineSubscription subscription = createSubscription(magazine, createUser(), START_DATE);

        when(magazineSubscriptionRepository.findByMagazine_User_Id(USER_ID))
                .thenReturn(List.of(subscription));

        List<TwoEigthReportResponse> result = spy.secondEditorAdminReport(null, null, USER_ID);

        verify(magazineSubscriptionRepository).findByMagazine_User_Id(USER_ID);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine())
        );
    }

    // ────────────────── Reporte 3 ──────────────────

    @Test
    void testThirdReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        InteractionLike like = createLike(magazine, createUser(), true, START_DATE);

        when(interactionLikeRepository.findByLikeDateBetweenAndLikedAndMagazine_User_Id(START_DATE, END_DATE, true, USER_ID))
                .thenReturn(List.of(like));

        List<ThreeReportResponse> result = spy.thirdEditorReport(START_DATE, END_DATE, USER_ID);

        verify(interactionLikeRepository).findByLikeDateBetweenAndLikedAndMagazine_User_Id(START_DATE, END_DATE, true, USER_ID);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine()),
                () -> assertEquals(MAGAZINE_TITLE, result.get(0).getNameMagazine()),
                () -> assertEquals(1, result.get(0).getLikes().size())
        );
    }

    @Test
    void testThirdReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        InteractionLike like = createLike(magazine, createUser(), true, START_DATE);

        when(interactionLikeRepository.findByMagazine_User_Id(USER_ID))
                .thenReturn(List.of(like));

        List<ThreeReportResponse> result = spy.thirdEditorReport(null, null, USER_ID);

        verify(interactionLikeRepository).findByMagazine_User_Id(USER_ID);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine())
        );
    }

    // ────────────────── Reporte 4 ──────────────────

    @Test
    void testFourthReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        MagazinePayment payment = createPayment(magazine, PAYMENT, START_DATE);
        AdBlock adBlock = createAdBlock(magazine, createAdType(AD_TYPE_NAME), PAYMENT, START_DATE);

        when(magazineRepository.findByUser_Id(USER_ID)).thenReturn(List.of(magazine));
        when(magazinePaymentRepository.findByPaymentDateBetweenAndMagazine(START_DATE, END_DATE, magazine))
                .thenReturn(List.of(payment));
        when(adBlockRepository.findByStartDateBetweenAndMagazine(START_DATE, END_DATE, magazine))
                .thenReturn(List.of(adBlock));

        List<FourthReportResponse> result = spy.fourthEditorReport(START_DATE, END_DATE, USER_ID);

        verify(magazineRepository).findByUser_Id(USER_ID);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine()),
                () -> assertEquals(MAGAZINE_TITLE, result.get(0).getNameMagazine()),
                () -> assertEquals(1, result.get(0).getPaymentReports().size()),
                () -> assertEquals(1, result.get(0).getAdBlocks().size())
        );
    }

    @Test
    void testFourthReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        MagazinePayment payment = createPayment(magazine, PAYMENT, START_DATE);
        AdBlock adBlock = createAdBlock(magazine, createAdType(AD_TYPE_NAME), PAYMENT, START_DATE);

        when(magazineRepository.findByUser_Id(USER_ID)).thenReturn(List.of(magazine));
        when(magazinePaymentRepository.findByMagazine(magazine)).thenReturn(List.of(payment));
        when(adBlockRepository.findByMagazine(magazine)).thenReturn(List.of(adBlock));

        List<FourthReportResponse> result = spy.fourthEditorReport(null, null, USER_ID);

        verify(magazineRepository).findByUser_Id(USER_ID);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine()),
                () -> assertEquals(1, result.get(0).getPaymentReports().size()),
                () -> assertEquals(1, result.get(0).getAdBlocks().size())
        );
    }

    // ────────────────── Reporte 5 ──────────────────

    @Test
    void testFiveReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        MagazinePayment payment = createPayment(magazine, PAYMENT, START_DATE);
        Advertisement ad = createAdvertisement(AD_ID, createUser(), createAdType(AD_TYPE_NAME), 200, START_DATE);
        AdBlock adBlock = createAdBlock(magazine, createAdType(AD_TYPE_NAME), PAYMENT, START_DATE);

        when(magazinePaymentRepository.findByPaymentDateBetween(START_DATE, END_DATE)).thenReturn(List.of(payment));
        when(advertisementRepository.findByCreationDateBetween(START_DATE, END_DATE)).thenReturn(List.of(ad));
        when(adBlockRepository.findByStartDateBetween(START_DATE, END_DATE)).thenReturn(List.of(adBlock));

        FiveReportResponse result = spy.fiveAdminReport(START_DATE, END_DATE);

        assertAll(
                () -> assertEquals(1, result.getMagazines().size()),
                () -> assertEquals(1, result.getAds().size()),
                () -> assertEquals(1, result.getAdBlocks().size())
        );
    }

    @Test
    void testFiveReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        MagazinePayment payment = createPayment(magazine, PAYMENT, START_DATE);
        Advertisement ad = createAdvertisement(AD_ID, createUser(), createAdType(AD_TYPE_NAME), 200, START_DATE);
        AdBlock adBlock = createAdBlock(magazine, createAdType(AD_TYPE_NAME), PAYMENT, START_DATE);

        when(magazinePaymentRepository.findAll()).thenReturn(List.of(payment));
        when(advertisementRepository.findAll()).thenReturn(List.of(ad));
        when(adBlockRepository.findAll()).thenReturn(List.of(adBlock));

        FiveReportResponse result = spy.fiveAdminReport(null, null);

        assertAll(
                () -> assertEquals(1, result.getMagazines().size()),
                () -> assertEquals(1, result.getAds().size()),
                () -> assertEquals(1, result.getAdBlocks().size())
        );
    }

    // ────────────────── Reporte 6 ──────────────────

    @Test
    void testSixReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Advertisement ad = createAdvertisement(AD_ID, createUser(), createAdType(AD_TYPE_NAME), 200, START_DATE);
        ExtraContent extra = createExtraContent(ad, RESOURCE_URL);

        when(advertisementRepository.findByCreationDateBetween(START_DATE, END_DATE)).thenReturn(List.of(ad));
        when(extraContentRepository.findByAdvertisement_Id(AD_ID)).thenReturn(List.of(extra));

        List<SixReportResponse> result = spy.sixAdminReport(START_DATE, END_DATE);

        verify(advertisementRepository).findByCreationDateBetween(START_DATE, END_DATE);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(AD_ID, result.get(0).getIdAd()),
                () -> assertEquals(AD_TYPE_NAME, result.get(0).getNameTypeAd()),
                () -> assertEquals(1, result.get(0).getLinks().size()),
                () -> assertEquals(RESOURCE_URL, result.get(0).getLinks().get(0))
        );
    }

    @Test
    void testSixReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Advertisement ad = createAdvertisement(AD_ID, createUser(), createAdType(AD_TYPE_NAME), 200, START_DATE);
        ExtraContent extra = createExtraContent(ad, RESOURCE_URL);

        when(advertisementRepository.findAll()).thenReturn(List.of(ad));
        when(extraContentRepository.findByAdvertisement_Id(AD_ID)).thenReturn(List.of(extra));

        List<SixReportResponse> result = spy.sixAdminReport(null, null);

        verify(advertisementRepository).findAll();
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(AD_ID, result.get(0).getIdAd())
        );
    }

    // ────────────────── Reporte 7 ──────────────────

    @Test
    void testSeventhReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        User user = createUser();
        Advertisement ad = createAdvertisement(AD_ID, user, createAdType(AD_TYPE_NAME), 200, START_DATE);

        when(advertisementRepository.findByCreationDateBetween(START_DATE, END_DATE)).thenReturn(List.of(ad));

        List<SeventhReportResponse> result = spy.seventhReport(START_DATE, END_DATE);

        verify(advertisementRepository).findByCreationDateBetween(START_DATE, END_DATE);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(USER_FIRST_NAME + " " + USER_LAST_NAME, result.get(0).getAdvertiserName()),
                () -> assertEquals(1, result.get(0).getAdvertisements().size()),
                () -> assertEquals(AD_TYPE_NAME, result.get(0).getAdvertisements().get(0).getAdTypeName())
        );
    }

    @Test
    void testSeventhReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        User user = createUser();
        Advertisement ad = createAdvertisement(AD_ID, user, createAdType(AD_TYPE_NAME), 200, START_DATE);

        when(advertisementRepository.findAll()).thenReturn(List.of(ad));

        List<SeventhReportResponse> result = spy.seventhReport(null, null);

        verify(advertisementRepository).findAll();
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(USER_FIRST_NAME + " " + USER_LAST_NAME, result.get(0).getAdvertiserName())
        );
    }

    // ────────────────── Reporte 8 ──────────────────

    @Test
    void testEighthReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        MagazineSubscription subscription = createSubscription(magazine, createUser(), START_DATE);

        when(magazineSubscriptionRepository.findBySubscriptionDateBetween(START_DATE, END_DATE))
                .thenReturn(List.of(subscription));

        List<TwoEigthReportResponse> result = spy.eighthReport(START_DATE, END_DATE);

        verify(magazineSubscriptionRepository).findBySubscriptionDateBetween(START_DATE, END_DATE);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine()),
                () -> assertEquals(MAGAZINE_TITLE, result.get(0).getNameMagazine()),
                () -> assertEquals(1, result.get(0).getSubscriptions().size())
        );
    }

    @Test
    void testEighthReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        MagazineSubscription subscription = createSubscription(magazine, createUser(), START_DATE);

        when(magazineSubscriptionRepository.findAll()).thenReturn(List.of(subscription));

        List<TwoEigthReportResponse> result = spy.eighthReport(null, null);

        verify(magazineSubscriptionRepository).findAll();
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine())
        );
    }

    // ────────────────── Reporte 9 ──────────────────

    @Test
    void testNineReportWithDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        InteractionComment comment = createComment(magazine, createUser(), COMMENT_TEXT, START_DATE);

        when(interactionCommentRepository.findByCommentDateBetween(START_DATE, END_DATE))
                .thenReturn(List.of(comment));

        List<OneNineReportResponse> result = spy.nineReport(START_DATE, END_DATE);

        verify(interactionCommentRepository).findByCommentDateBetween(START_DATE, END_DATE);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine()),
                () -> assertEquals(MAGAZINE_TITLE, result.get(0).getNameMagazine()),
                () -> assertEquals(1, result.get(0).getComments().size()),
                () -> assertEquals(COMMENT_TEXT, result.get(0).getComments().get(0).getComment())
        );
    }

    @Test
    void testNineReportWithoutDateRange() {
        ReportServiceImplementation spy = spy(reportService);

        Magazine magazine = createMagazine(MAGAZINE_ID, MAGAZINE_TITLE);
        InteractionComment comment = createComment(magazine, createUser(), COMMENT_TEXT, START_DATE);

        when(interactionCommentRepository.findAll()).thenReturn(List.of(comment));

        List<OneNineReportResponse> result = spy.nineReport(null, null);

        verify(interactionCommentRepository).findAll();
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(MAGAZINE_ID, result.get(0).getIdMagazine())
        );
    }

    // ────────────────── Helpers ──────────────────

    private User createUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setNames(USER_FIRST_NAME);
        user.setLastNames(USER_LAST_NAME);
        return user;
    }

    private Magazine createMagazine(Integer id, String title) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        magazine.setTitle(title);
        return magazine;
    }

    private InteractionComment createComment(Magazine magazine, User user, String comment, LocalDate date) {
        InteractionComment c = new InteractionComment();
        c.setMagazine(magazine);
        c.setUser(user);
        c.setComment(comment);
        c.setCommentDate(date);
        return c;
    }

    private MagazineSubscription createSubscription(Magazine magazine, User user, LocalDate date) {
        MagazineSubscription sub = new MagazineSubscription();
        sub.setMagazine(magazine);
        sub.setUser(user);
        sub.setSubscriptionDate(date);
        return sub;
    }

    private InteractionLike createLike(Magazine magazine, User user, boolean liked, LocalDate date) {
        InteractionLike like = new InteractionLike();
        like.setMagazine(magazine);
        like.setUser(user);
        like.setLiked(liked);
        like.setLikeDate(date);
        return like;
    }

    private MagazinePayment createPayment(Magazine magazine, Integer payment, LocalDate date) {
        MagazinePayment p = new MagazinePayment();
        p.setMagazine(magazine);
        p.setPayment(payment);
        p.setPaymentDate(date);
        return p;
    }

    private AdBlock createAdBlock(Magazine magazine, AdType adType, Integer payment, LocalDate date) {
        AdBlock block = new AdBlock();
        block.setMagazine(magazine);
        Advertisement ad = new Advertisement();
        ad.setAdType(adType);
        block.setAdvertisement(ad);
        block.setPayment(payment);
        block.setStartDate(date);
        return block;
    }

    private Advertisement createAdvertisement(Integer id, User user, AdType adType, int cost, LocalDate date) {
        Advertisement ad = new Advertisement();
        ad.setId(id);
        ad.setUser(user);
        ad.setAdType(adType);
        ad.setTotalCost(cost);
        ad.setCreationDate(date);
        return ad;
    }

    private AdType createAdType(String name) {
        AdType adType = new AdType();
        adType.setName(name);
        return adType;
    }

    private ExtraContent createExtraContent(Advertisement ad, String resource) {
        ExtraContent ec = new ExtraContent();
        ec.setAdvertisement(ad);
        ec.setResource(resource);
        return ec;
    }
}
