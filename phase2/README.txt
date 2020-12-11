1. Run main
2. Follow prompts to login as existing user: "Admin" (password == "123"), alternatively create your own standard user account.
3. As Admin:
	a) choose "Add Speaker" and follow prompts to create a speaker
	b) choose "Add Event" and follow prompts to create an event
	c) choose "Friends" and follow prompts to add/remove friends. Available friends are: andy, william, fred, elliot, chris, ziwen, jesse, nithilan
	d) choose "Message". Follow prompts to send message to single user, event participants to to message a speaker.
	e) choose "Events'. Follow prompts to 'add' an event to your list or 'leave' an event that you are signed up for. 
	f) choose "Change password". Follow prompts to change password. Please remember to change it back to "123"
	g) choose "Logout". Follow prompts to logout. Follow prompts to close login and then program. Goodbye, friend. 
4. If you choose to create your own account, you may do so. Please note, the options are limited for standard users for security purposes. 
5. UML can be found in root "group_0051\phase1"
6. Javadoc can be found in "group_0051\phase1\src\javadoc"

NOTE: Please do not resize the window to be bigger/smaller if possible. A maximum/minimum window size is not set
because JavaFX is known to have display issues on devices with high DPI if a maximum/minimum window size is set.

===============================
 ON USING MESSAGE INBOX/OUTBOX
===============================
NOTE: Please add a friend to chat with that user.
1. Select a chatroom from the left to access chat history.
2. Enter text in the text box and click the "send" button to send it. Multi-line messages are allowed.
    a) Most profanities will be filtered out automatically and replaced with math terms.
3. For each message, click "Delete" to delete the message from chat, "Pin Message/Unpin Message"
   to pin or unpin the message, and "Mark As Read/Mark as Unread" to mark the message as read or unread.
    a) Message deletion is available to every user, but only admins can delete other users' messages.
    b) Pinned messages in a specific chatroom can be accessed by clicking the "Pins" button while in that chatroom.
    c) Only other users' messages can be marked as read/unread.
4. Click the "search for user" to look up a chatroom.
5. Click the blue "+" button to open a message outbox that can send messages to multiple people at once.
    a) This feature is NOT AVAILABLE TO ATTENDEES.
    b) Select "All speakers" to send a message to every speaker who is hosting an event. (This does not include
       speakers whose accounts exist, but are not giving talks.
    c) Select an event to send a message to all attendees of that event.

Questions for TA:
Are static gateways allowed?