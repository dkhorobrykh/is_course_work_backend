package ru.itmo.is.course_work.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.UserDoc;
import ru.itmo.is.course_work.model.UserDocAddDto;
import ru.itmo.is.course_work.model.UserDocType;
import ru.itmo.is.course_work.repository.FlightRepository;
import ru.itmo.is.course_work.repository.UserDocRepository;
import ru.itmo.is.course_work.repository.UserDocTypeRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDocService {

  private final UserDocRepository userDocRepository;
  private final UserDocTypeRepository userDocTypeRepository;
  private final FlightRepository flightRepository;

  public List<UserDoc> getAllDocsForCurrentUser() {
    var currentUser = RoleService.getCurrentUser();
    if (currentUser == null) throw new CustomException(ExceptionEnum.UNAUTHORIZED);

    return userDocRepository.findAllByUser_IdOrderById(RoleService.getCurrentUser().getId());
  }

  public UserDoc addNewDoc(UserDocAddDto dto) {
    var currentUser = RoleService.getCurrentUser();
    if (currentUser == null) throw new CustomException(ExceptionEnum.UNAUTHORIZED);

    var userDocType = getUserDocTypeByName(dto.getUserDocTypeName());

    var issueDate = dto.getIssueDate();
    var expirationDate = dto.getExpirationDate();

    if (LocalDate.now().isBefore(issueDate))
      throw new CustomException(ExceptionEnum.ISSUE_DATE_IN_FUTURE);

    if (expirationDate.isBefore(issueDate))
      throw new CustomException(ExceptionEnum.ISSUE_DATE_MUST_BE_BEFORE_EXPIRATION_DATE);

    var newDoc =
        UserDoc.builder()
            .userDocType(userDocType)
            .series(dto.getSeries())
            .number(dto.getNumber())
            .issueDate(dto.getIssueDate())
            .expirationDate(dto.getExpirationDate())
            .user(currentUser)
            .build();

    return userDocRepository.saveAndFlush(newDoc);
  }

  public UserDocType getUserDocTypeById(Long userDocTypeId) {
    return userDocTypeRepository
        .findById(userDocTypeId)
        .orElseThrow(() -> new CustomException(ExceptionEnum.USER_DOC_TYPE_NOT_FOUND));
  }

  public UserDocType getUserDocTypeByName(String name) {
    return userDocTypeRepository
        .findByNameIgnoreCase(name)
        .orElseThrow(() -> new CustomException(ExceptionEnum.USER_DOC_TYPE_NOT_FOUND));
  }

  public void deleteDocById(Long documentId) {
    getUserDocById(documentId);

    userDocRepository.deleteById(documentId);
  }

  public UserDoc getUserDocById(Long id) {
    return userDocRepository
        .findById(id)
        .orElseThrow(() -> new CustomException(ExceptionEnum.USER_DOC_NOT_FOUND));
  }

  public List<UserDocType> getAllUserDocTypes() {
    return userDocTypeRepository.findAllByOrderById();
  }

  public void validateDocumentForFlight(Long documentId, Long flightId) {
      Flight flight = flightRepository.findById(flightId)
              .orElseThrow(() -> new CustomException(ExceptionEnum.FLIGHT_NOT_FOUND));

      UserDoc document = userDocRepository.findById(documentId)
              .orElseThrow(() -> new CustomException(ExceptionEnum.USER_DOC_NOT_FOUND));

      LocalDate flightDepartureDate = flight.getDepartureDatetime()
               .atZone(ZoneId.systemDefault())
               .toLocalDate();

      if (document.getExpirationDate() != null &&
              document.getExpirationDate().isBefore(flightDepartureDate)) {
          throw new CustomException(ExceptionEnum.DOCUMENT_EXPIRED,
                  "Document expires: " + document.getExpirationDate() +
                          ", Flight departs: " + flightDepartureDate
            );
        }
    }
}
