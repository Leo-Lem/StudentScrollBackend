package studentscroll.api.students.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import studentscroll.api.security.JSONWebToken;
import studentscroll.api.students.data.Student;

@Data
@RequiredArgsConstructor
@JsonInclude(Include.NON_NULL)
public class StudentResponse {

  private final Long id;
  private final String name;
  private final String email;
  private final String token;
  private final String type;

  public StudentResponse(Student student) {
    this(student.getId(), student.getProfile().getName(), student.getEmail(), null, null);
  }

  public StudentResponse(Student student, JSONWebToken token) {
    this(student.getId(), student.getProfile().getName(), student.getEmail(), token.toString(), "Bearer");
  }

}
