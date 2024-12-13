package ru.itmo.is.course_work.model;

import org.hibernate.envers.RevisionListener;
import ru.itmo.is.course_work.service.RoleService;

public class AuditRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object o) {
        AuditRevisionEntity audit = (AuditRevisionEntity) o;

        audit.setUser(RoleService.getCurrentUser());
    }
}
