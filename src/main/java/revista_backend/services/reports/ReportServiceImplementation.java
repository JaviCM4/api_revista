package revista_backend.services.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.reports.common.AdBlockReport;
import revista_backend.dto.reports.five.*;
import revista_backend.dto.reports.four.*;
import revista_backend.dto.reports.one_nine.*;
import revista_backend.dto.reports.seven.*;
import revista_backend.dto.reports.six.*;
import revista_backend.dto.reports.three.*;
import revista_backend.dto.reports.two_eight.*;
import revista_backend.models.advertisement.*;
import revista_backend.models.magazine.*;
import revista_backend.repositories.advertisement.*;
import revista_backend.repositories.magazine.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImplementation implements ReportService {

    private final InteractionCommentRepository interactionCommentRepository;
    private final MagazineSubscriptionRepository magazineSubscriptionRepository;
    private final InteractionLikeRepository interactionLikeRepository;
    private final MagazinePaymentRepository magazinePaymentRepository;
    private final AdvertisementRepository advertisementRepository;
    private final ExtraContentRepository extraContentRepository;
    private final AdBlockRepository adBlockRepository;
    private final MagazineRepository magazineRepository;

    @Autowired
    public ReportServiceImplementation(InteractionCommentRepository interactionCommentRepository, MagazineSubscriptionRepository magazineSubscriptionRepository, InteractionLikeRepository interactionLikeRepository, MagazinePaymentRepository magazinePaymentRepository, AdvertisementRepository advertisementRepository, ExtraContentRepository extraContentRepository, AdBlockRepository adBlockRepository, MagazineRepository magazineRepository) {
        this.interactionCommentRepository = interactionCommentRepository;
        this.magazineSubscriptionRepository = magazineSubscriptionRepository;
        this.interactionLikeRepository = interactionLikeRepository;
        this.magazinePaymentRepository = magazinePaymentRepository;
        this.advertisementRepository = advertisementRepository;
        this.extraContentRepository = extraContentRepository;
        this.adBlockRepository = adBlockRepository;
        this.magazineRepository = magazineRepository;
    }

    // Reporte 1
    @Override
    public List<OneNineReportResponse> firstEditorAdminReport(LocalDate start, LocalDate end, Integer idUser) {
        List<InteractionComment> list = hasDateRange(start, end)
                ? interactionCommentRepository.findByCommentDateBetweenAndMagazine_User_Id(start, end, idUser)
                : interactionCommentRepository.findByMagazine_User_Id(idUser);

        return list.stream()
                .collect(Collectors.groupingBy(InteractionComment::getMagazine))
                .entrySet().stream()
                .map(this::toOneNineResponse)
                .toList();
    }

    // Reporte 2
    @Override
    public List<TwoEigthReportResponse> secondEditorAdminReport(LocalDate start, LocalDate end, Integer idUser) {
        List<MagazineSubscription> list = hasDateRange(start, end)
                ? magazineSubscriptionRepository.findBySubscriptionDateBetweenAndMagazine_User_Id(start, end, idUser)
                : magazineSubscriptionRepository.findByMagazine_User_Id(idUser);

        return list.stream()
                .collect(Collectors.groupingBy(MagazineSubscription::getMagazine))
                .entrySet().stream()
                .map(this::toTwoEightResponse)
                .toList();
    }

    // Reporte 3
    @Override
    public List<ThreeReportResponse> thirdEditorReport(LocalDate start, LocalDate end, Integer idUser) {
        List<InteractionLike> list = hasDateRange(start, end)
                ? interactionLikeRepository.findByLikeDateBetweenAndLikedAndMagazine_User_Id(start, end, true, idUser)
                : interactionLikeRepository.findByMagazine_User_Id(idUser);

        return list.stream()
                .collect(Collectors.groupingBy(InteractionLike::getMagazine))
                .entrySet().stream()
                .map(entry -> new ThreeReportResponse(
                        entry.getKey().getId(),
                        entry.getKey().getTitle(),
                        entry.getValue().stream()
                                .map(s -> new LikeReport(fullName(s.getUser()), s.getLikeDate()))
                                .toList()
                ))
                .toList();
    }

    // Reporte 4
    @Override
    public List<FourthReportResponse> fourthEditorReport(LocalDate start, LocalDate end, Integer idUser) {
        return magazineRepository.findByUser_Id(idUser).stream()
                .map(magazine -> {
                    List<PaymentReport> payments = (hasDateRange(start, end)
                            ? magazinePaymentRepository.findByPaymentDateBetweenAndMagazine(start, end, magazine)
                            : magazinePaymentRepository.findByMagazine(magazine))
                            .stream().map(this::toPaymentReport).toList();

                    List<AdBlockReport> blocks = (hasDateRange(start, end)
                            ? adBlockRepository.findByStartDateBetweenAndMagazine(start, end, magazine)
                            : adBlockRepository.findByMagazine(magazine))
                            .stream()
                            .map(b -> new AdBlockReport(
                                    b.getAdvertisement().getAdType().getName(), b.getPayment(), b.getStartDate()))
                            .toList();

                    return new FourthReportResponse(magazine.getId(), magazine.getTitle(), payments, blocks);
                })
                .toList();
    }

    // Reporte 5
    @Override
    public FiveReportResponse fiveAdminReport(LocalDate start, LocalDate end) {
        List<MagazinePayment> payments = hasDateRange(start, end)
                ? magazinePaymentRepository.findByPaymentDateBetween(start, end)
                : magazinePaymentRepository.findAll();

        List<Advertisement> ads = hasDateRange(start, end)
                ? advertisementRepository.findByCreationDateBetween(start, end)
                : advertisementRepository.findAll();

        List<AdBlock> blocks = hasDateRange(start, end)
                ? adBlockRepository.findByStartDateBetween(start, end)
                : adBlockRepository.findAll();

        List<MagazineReport> magazineReports = payments.stream()
                .map(MagazinePayment::getMagazine).distinct()
                .map(magazine -> new MagazineReport(
                        magazine.getTitle(),
                        payments.stream()
                                .filter(p -> p.getMagazine().equals(magazine))
                                .map(this::toPaymentReport).toList()
                ))
                .toList();

        List<AdReport> adReports = ads.stream()
                .map(ad -> new AdReport(ad.getAdType().getName(), ad.getTotalCost(), ad.getCreationDate()))
                .toList();

        List<AdBlockReport> adBlockReports = blocks.stream().map(this::toAdBlockReport).toList();

        return new FiveReportResponse(magazineReports, adReports, adBlockReports);
    }

    // Reporte 6
    @Override
    public List<SixReportResponse> sixAdminReport(LocalDate start, LocalDate end) {
        List<Advertisement> list = hasDateRange(start, end)
                ? advertisementRepository.findByCreationDateBetween(start, end)
                : advertisementRepository.findAll();

        return list.stream()
                .map(ad -> new SixReportResponse(ad,
                        extraContentRepository.findByAdvertisement_Id(ad.getId()).stream()
                                .map(ExtraContent::getResource).toList()))
                .toList();
    }

    // Reporte 7
    @Override
    public List<SeventhReportResponse> seventhReport(LocalDate start, LocalDate end) {
        List<Advertisement> list = hasDateRange(start, end)
                ? advertisementRepository.findByCreationDateBetween(start, end)
                : advertisementRepository.findAll();

        return list.stream()
                .collect(Collectors.groupingBy(Advertisement::getUser))
                .entrySet().stream()
                .map(entry -> new SeventhReportResponse(
                        fullName(entry.getKey()),
                        entry.getValue().stream()
                                .map(ad -> new AdCostReport(ad.getId(), ad.getAdType().getName(),
                                        ad.getTotalCost(), ad.getCreationDate()))
                                .toList()
                ))
                .toList();
    }

    // Reporte 8
    @Override
    public List<TwoEigthReportResponse> eighthReport(LocalDate start, LocalDate end) {
        List<MagazineSubscription> list = hasDateRange(start, end)
                ? magazineSubscriptionRepository.findBySubscriptionDateBetween(start, end)
                : magazineSubscriptionRepository.findAll();

        return list.stream()
                .collect(Collectors.groupingBy(MagazineSubscription::getMagazine))
                .entrySet().stream()
                .map(this::toTwoEightResponse)
                .toList();
    }

    // Reporte 9
    @Override
    public List<OneNineReportResponse> nineReport(LocalDate start, LocalDate end) {
        List<InteractionComment> list = hasDateRange(start, end)
                ? interactionCommentRepository.findByCommentDateBetween(start, end)
                : interactionCommentRepository.findAll();

        return list.stream()
                .collect(Collectors.groupingBy(InteractionComment::getMagazine))
                .entrySet().stream()
                .map(this::toOneNineResponse)
                .toList();
    }

    // Extras
    private boolean hasDateRange(LocalDate start, LocalDate end) {
        return start != null && end != null;
    }

    private String fullName(revista_backend.models.user.User user) {
        return user.getNames() + " " + user.getLastNames();
    }

    private PaymentReport toPaymentReport(MagazinePayment p) {
        return new PaymentReport(p.getPayment(), p.getPaymentDate());
    }

    private AdBlockReport toAdBlockReport(AdBlock b) {
        return new AdBlockReport(b.getAdvertisement().getAdType().getName(), b.getPayment(), b.getStartDate());
    }

    private OneNineReportResponse toOneNineResponse(Map.Entry<Magazine, List<InteractionComment>> entry) {
        return new OneNineReportResponse(
                entry.getKey().getId(),
                entry.getKey().getTitle(),
                entry.getValue().stream()
                        .map(s -> new CommentReport(fullName(s.getUser()), s.getComment(), s.getCommentDate()))
                        .toList()
        );
    }

    private TwoEigthReportResponse toTwoEightResponse(Map.Entry<Magazine, List<MagazineSubscription>> entry) {
        return new TwoEigthReportResponse(
                entry.getKey().getId(),
                entry.getKey().getTitle(),
                entry.getValue().stream()
                        .map(s -> new ReportSubscription(fullName(s.getUser()), s.getSubscriptionDate()))
                        .toList()
        );
    }
}