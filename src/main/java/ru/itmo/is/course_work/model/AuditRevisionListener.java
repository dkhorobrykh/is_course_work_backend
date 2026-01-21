package ru.itmo.is.course_work.model;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;
import ru.itmo.is.course_work.util.SecurityUtils;

@Component
@Slf4j
public class AuditRevisionListener implements RevisionListener {
  @Override
  public void newRevision(Object o) {
    AuditRevisionEntity audit = (AuditRevisionEntity) o;
    try {
        audit.setUserId(SecurityUtils.getKeycloakUserId());
    } catch (Exception ex) {
        log.warn("Не удалось получить текущего пользователя для создания новой ревизии аудита", ex);
    }
  }
}
