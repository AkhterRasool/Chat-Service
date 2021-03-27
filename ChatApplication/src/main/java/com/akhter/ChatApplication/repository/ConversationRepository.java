package com.akhter.ChatApplication.repository;

import com.akhter.ChatApplication.model.Conversation;
import com.akhter.ChatApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    @Query("select c from com.akhter.ChatApplication.model.Conversation c," +
            " com.akhter.ChatApplication.model.ConversationRecipients cr where " +
            "(cr.currUser=:currUser AND cr.otherUser=:otherUser) AND c.conversationRecipients=cr")
    Conversation findConversation(@Param("otherUser") User otherUser,
                                  @Param("currUser") User currUser);
}
