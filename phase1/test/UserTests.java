import org.junit.*;

import static org.junit.Assert.*;

public class UserTests {
    Attendee john = new Attendee("john", "john117", "donuts");
    Speaker wiggins = new Speaker("andrew", "antkiller", "winning");



    @Test
    public void userCanRetrieveAllEvents(){
        john.addEvent(2147483649L);
        john.addEvent(1983748912L);
        john.addEvent(1298603183L);


        assertTrue("the number of retrieved events should equal 3\n", john.getEvents().size() == 3);
    }

    @Test
    public void userCanCancel(){
        john.addEvent(2147483649L);
        john.addEvent(1983748912L);
        john.addEvent(1298603183L);
        john.removeEvent(2147483649L);

        assertTrue("user can cancel events\n", john.getEvents().size() == 2);
    }
    @Test
    public void userCanRetrieveAllTalks(){
        wiggins.addTalk(2147483649L);
        wiggins.addTalk(1983748912L);
        wiggins.addTalk(1298603183L);

        assertTrue("the number of retrieved talks should equal 3\n", wiggins.getTalks().size() == 3);
    }

    @Test
    public void canMessage(){
        //Can send and retrieve messages from other attendees and message speakers.

        //assertTrue("user can send a message\n",);

        //assertTrue("user can receive a message\n",);

        //assertTrue("speaker can message all\n",);
    }

}

