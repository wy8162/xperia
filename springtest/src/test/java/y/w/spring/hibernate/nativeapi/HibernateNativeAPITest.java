package y.w.spring.hibernate.nativeapi;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Illustrates use of Hibernate native APIs.
 *
 * @author Steve Ebersole
 */
public class HibernateNativeAPITest {
	private static Logger log = Logger.getLogger(HibernateNativeAPITest.class);
	private SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {
		// A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
	}

	@After
	public void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@Test
	public void testBasicUsage() {
		// create a couple of events...
        Location location = new Location();
        location.setName("Hilton Convention Center");
        location.getMailingAddress().setStreetAddress("950 North Stafford St.");
        location.getMailingAddress().setCity("Arlington");
        location.getMailingAddress().setState("VA");
        location.getMailingAddress().setZipCode("22204");

        location.setName("Hilton Convention Center");
        location.getBillingAddress().setStreetAddress("12 Marlo Rd.");
        location.getBillingAddress().setCity("Livingston");
        location.getBillingAddress().setState("NJ");
        location.getBillingAddress().setZipCode("07039");

        Event event = new Event();
        event.setName("Annual Meeting");
        event.setDuration(60);
        event.setStartDate(createDate(2012, 1, 1));
        event.setLocation(location);

        Location location1 = new Location();
        location1.setName("My Home");
        location1.getMailingAddress().setStreetAddress("12 Marlo Rd");
        location1.getMailingAddress().setCity("Livingston");
        location1.getMailingAddress().setState("NJ");
        location1.getMailingAddress().setZipCode("07039");

        location1.setName("My Home");
        location1.getBillingAddress().setStreetAddress("12 Marlo Rd.");
        location1.getBillingAddress().setCity("Livingston");
        location1.getBillingAddress().setState("NJ");
        location1.getBillingAddress().setZipCode("07039");

        Event event1 = new Event();
        event1.setName("Weekly Meeting");
        event1.setDuration(60);
        event1.setStartDate(createDate(2012, 1, 2));
        event1.setLocation(location);
        
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save( event );
		session.save( event1 );
		session.getTransaction().commit();
		session.close();

		// now lets pull events from the database and list them
		session = sessionFactory.openSession();
        session.beginTransaction();
        
        // Suppress warning: [unchecked] unchecked conversion
        @SuppressWarnings("unchecked")
        List<Event> result = session.createQuery( "from Event" ).list();
		for ( Event ev : result ) {
			log.info( "Event (" + ev.getStartDate() + ") : " + ev.getName() );
		}
        session.getTransaction().commit();
        session.close();
	}
	
    private static Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
