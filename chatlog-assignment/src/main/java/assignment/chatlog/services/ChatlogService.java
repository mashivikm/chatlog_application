package assignment.chatlog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import assignment.chatlog.constants.MessageConstants;
import assignment.chatlog.models.ChatlogEntity;
import assignment.chatlog.models.PageReq;
import assignment.chatlog.repositories.ChatlogRepository;

@Service
public class ChatlogService {

	@Autowired
	private ChatlogRepository chatlogRepository;

	public ResponseEntity<String> saveChatlog(String user, ChatlogEntity chatlogEntity) {

		chatlogEntity.setUser(user);
		chatlogEntity = chatlogRepository.save(chatlogEntity);
		return ResponseEntity.status(HttpStatus.CREATED).body(chatlogEntity.getMessageId());
	}

	public ResponseEntity<Page<ChatlogEntity>> getChatlogs(String user, PageReq pageReq) {
		ChatlogEntity start = null;
		if (pageReq.getStart() != null && pageReq.getStart() != "") {
			start = chatlogRepository.findById(pageReq.getStart()).orElse(null);
		}

		Pageable paging = PageRequest.ofSize(pageReq.getLimit()).withSort(Sort.by("timestamp").descending());
		Page<ChatlogEntity> pageResult = null;
		if (start == null) {
			pageResult = chatlogRepository.findByUser(user, paging);
		} else {
			pageResult = chatlogRepository.findByUserAndTimestampLessThanEqual(user, start.getTimestamp(), paging);
		}

		return ResponseEntity.status(HttpStatus.OK).body(pageResult);
	}

	public ResponseEntity<String> deleteChatlogsOfUser(String user) {
		List<ChatlogEntity> chatlogs = chatlogRepository.findByUser(user);
		if (chatlogs == null || chatlogs.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageConstants.NOT_FOUND_USER);
		}
		List<String> chatlogIds = chatlogs.stream().map(e -> e.getMessageId()).collect(Collectors.toList());
		chatlogRepository.deleteAllByIdInBatch(chatlogIds);
		return ResponseEntity.status(HttpStatus.OK)
				.body(String.format(MessageConstants.USER_MESSAGE_DELETE_SUCCESS, user));
	}

	public ResponseEntity<String> deleteChatlogMessageOfUser(String user, String messageId) {
		ChatlogEntity chatlog = chatlogRepository.findByMessageIdAndUser(messageId, user);
		if (chatlog == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageConstants.NOT_FOUND_MESSAGE);
		}
		chatlogRepository.deleteById(messageId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(String.format(MessageConstants.MESSAGE_DELETE_SUCCESS, messageId));
	}

}
