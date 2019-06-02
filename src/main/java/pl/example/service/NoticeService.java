package pl.example.service;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import pl.example.models.NoticeEntity;
        import pl.example.models.UserEntity;
        import pl.example.repository.NoticeRepository;
        import pl.example.repository.UserRepository;

        import java.util.ArrayList;
        import java.util.List;


@Service
public class NoticeService {


    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;


    public List<NoticeEntity> getAllNotice() {
        List<NoticeEntity> notices = new ArrayList<>();
        noticeRepository.findAll().forEach(notices::add);
        return notices;
    }

    public NoticeEntity getNotice(Integer id) {
        NoticeEntity ne = noticeRepository.findById(id).get();
        UserEntity user = userRepository.findById(id).get();
        user.setLogin(null);
        user.setPassword(null);
        ne.setUserByUserIdUser(null);
        return ne;
    }

    public void addNotice(NoticeEntity notice) {
        noticeRepository.save(notice);
    }


    public void updateNotice(Integer id, NoticeEntity notice) {
        noticeRepository.save(notice);
    }


    public void deleteNotice(Integer id) {
        noticeRepository.deleteById(id);
    }
}
