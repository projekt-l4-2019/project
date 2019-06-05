package pl.example.service;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import pl.example.models.NoticeEntity;
        import pl.example.repository.NoticeRepository;

        import java.sql.Timestamp;
        import java.time.LocalDateTime;
        import java.util.ArrayList;
        import java.util.List;


@Service
public class NoticeService {


    @Autowired
    private NoticeRepository noticeRepository;

    public List<NoticeEntity> getAllNotice() {
        List<NoticeEntity> notices = new ArrayList<>();
        noticeRepository.findAll().forEach(notices::add);
        for(NoticeEntity no: notices)
        {
            no.getSubjectBySubjectIdSubject().setNoticesByIdSubject(null);
            no.getUserrByUserrIdUser().setOpinionsByIdUser_0(null);
            no.getUserrByUserrIdUser().setOpinionsByIdUser(null);
            no.getUserrByUserrIdUser().setNoticesByIdUser(null);
            no.getUserrByUserrIdUser().setMeetingsByIdUser(null);
            no.getUserrByUserrIdUser().getCityByCityIdCity().setUserrsByIdCity(null);
            no.getUserrByUserrIdUser().getCityByCityIdCity().setVoivodeshipByVoivodeshipIdVoivodeship(null);
            no.getUserrByUserrIdUser().setLogin(null);
            no.getUserrByUserrIdUser().setPassword(null);
        }
        return notices;
    }

    public NoticeEntity getNotice(Integer id) {

        NoticeEntity no = noticeRepository.findById(id).get();
        no.getSubjectBySubjectIdSubject().setNoticesByIdSubject(null);
        no.getUserrByUserrIdUser().setOpinionsByIdUser_0(null);
        no.getUserrByUserrIdUser().setOpinionsByIdUser(null);
        no.getUserrByUserrIdUser().setNoticesByIdUser(null);
        no.getUserrByUserrIdUser().setMeetingsByIdUser(null);
        no.getUserrByUserrIdUser().getCityByCityIdCity().setUserrsByIdCity(null);
        no.getUserrByUserrIdUser().getCityByCityIdCity().setVoivodeshipByVoivodeshipIdVoivodeship(null);
        no.getUserrByUserrIdUser().setLogin(null);
        no.getUserrByUserrIdUser().setPassword(null);
        return no;
    }

    public void addNotice(NoticeEntity notice) {
        notice.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        noticeRepository.save(notice);
    }


    public void updateNotice(Integer id, NoticeEntity notice) {
        noticeRepository.save(notice);
    }


    public void deleteNotice(Integer id) {
        noticeRepository.deleteById(id);
    }
}
