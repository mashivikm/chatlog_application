package assignment.chatlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import assignment.chatlog.models.ChatlogEntity;
import assignment.chatlog.models.PageReq;
import assignment.chatlog.services.ChatlogService;

@RestController
public class ChatlogController {

	@Autowired
	private ChatlogService chatlogService;

	@PostMapping("/chatlogs/{user}")
	public ResponseEntity<String> saveChatlog(@PathVariable(name = "user") String user, @RequestBody ChatlogEntity chatlogEntity) {
		return chatlogService.saveChatlog(user, chatlogEntity);
	}

	@GetMapping("/chatlogs/{user}")
	public ResponseEntity<Page<ChatlogEntity>> getChatlogs(@PathVariable(name = "user") String user, @RequestParam(name = "limit", required = false) Integer limit, @RequestParam(name = "start", required = false) String start) {
		return chatlogService.getChatlogs(user, new PageReq(limit == null ? 10 : limit, start));
	}

	@DeleteMapping("/chatlogs/{user}")
	public ResponseEntity<String> deleteChatlogsOfUser(@PathVariable(name = "user") String user) {
		return chatlogService.deleteChatlogsOfUser(user);
	}

	@DeleteMapping("/chatlogs/{user}/{msgid}")
	public ResponseEntity<String> deleteChatlogMessageOfUser(@PathVariable(name = "user") String user,
			@PathVariable(name = "msgid") String messageId) {
		return chatlogService.deleteChatlogMessageOfUser(user, messageId);
	}

}
