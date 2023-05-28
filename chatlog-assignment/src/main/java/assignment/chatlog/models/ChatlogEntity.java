package assignment.chatlog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chatlog_details")
public class ChatlogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String messageId;

	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	private Long timestamp;

	private Boolean isSent;

	private String user;

}
