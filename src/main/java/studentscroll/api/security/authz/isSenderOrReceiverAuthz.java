package studentscroll.api.security.authz;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import lombok.val;
import studentscroll.api.chats.data.Message;
import studentscroll.api.chats.data.MessageRepository;
import studentscroll.api.students.data.Student;

@Component
public class isSenderOrReceiverAuthz implements AuthorizationManager<RequestAuthorizationContext> {

  @Autowired
  private MessageRepository repo;

  @Override
  public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext context) {
    val principalId = ((Student) supplier.get().getPrincipal()).getId();
    val requestChatId = Long.parseLong(context.getVariables().get("postId"));
    val senderId = repo.findById(requestChatId).map(Message::getSender).map(Student::getId);
    val receiverId = repo.findById(requestChatId).map(Message::getReceiver).map(Student::getId);

    val isSenderOrReceiver = senderId
        .map(id -> id.equals(principalId))
        .orElse(
            receiverId
                .map(id -> id.equals(principalId))
                .orElse(false));

    return new AuthorizationDecision(isSenderOrReceiver);
  }

}