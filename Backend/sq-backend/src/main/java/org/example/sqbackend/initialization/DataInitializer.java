package org.example.sqbackend.initialization;

import org.example.sqbackend.models.*;
import org.example.sqbackend.models.Response.Response;
import org.example.sqbackend.models.Ticket.Ticket;
import org.example.sqbackend.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Profile({"dev", "test"})
public class DataInitializer implements CommandLineRunner {

    private final SpectatorRepository spectatorRepository;
    private final EventRepository eventRepository;
    private final PollRepository pollRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final TicketRepository ticketRepository;
    private final ResponseRepository responseRepository;

    public DataInitializer(SpectatorRepository spectatorRepository,
                           EventRepository eventRepository,
                           PollRepository pollRepository,
                           QuestionRepository questionRepository,
                           ChoiceRepository choiceRepository,
                           TicketRepository ticketRepository,
                           ResponseRepository responseRepository) {
        this.spectatorRepository = spectatorRepository;
        this.eventRepository = eventRepository;
        this.pollRepository = pollRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.ticketRepository = ticketRepository;
        this.responseRepository = responseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Spectator spectator1 = new Spectator();
        spectator1.setFirstname("Homer");
        spectator1.setLastname("Simpson");
        spectator1.setDateOfBirth(generateRandomDate(null,new Date(946684800000L))); // year 2000
        spectator1.setEmail("ihate.bart@doh.com");
        spectator1.setAddress("123 Street, City");

        Spectator spectator2 = new Spectator();
        spectator2.setFirstname("Alice");
        spectator2.setLastname("Smith");
        spectator2.setDateOfBirth(generateRandomDate(null,new Date(946684800000L))); // year 2000
        spectator2.setEmail("alice.smith@example.com");
        spectator2.setAddress("456 Avenue, Town");

        spectatorRepository.saveAll(Arrays.asList(
                spectator1,
                spectator2
        ));

        Event currentEvent = new Event();
        currentEvent.setName("Current event");
        Date startDateCurrentEvent = generateRandomDate(null,new Date()); // This event just started
        currentEvent.setStartDate(startDateCurrentEvent);
        currentEvent.setEndDate(generateRandomDateInFuture(15));

        Event futureEvent = new Event();
        futureEvent.setName("Future event");
        Date startDateFutureEvent = generateRandomDateInFuture(15); // This event will start
        futureEvent.setStartDate(startDateFutureEvent);
        futureEvent.setEndDate(generateRandomDate(startDateFutureEvent,null));

        Event passedEvent = new Event();
        passedEvent.setName("Passed event");
        Date startDatePassedEvent = generateRandomDateInPast(150); // This event is over
        passedEvent.setStartDate(startDatePassedEvent);
        passedEvent.setEndDate(generateRandomDate(startDatePassedEvent,null));

        eventRepository.saveAll(Arrays.asList(currentEvent,futureEvent, passedEvent));

        Poll futurePoll = new Poll();
        futurePoll.setName(futureEvent.getName()+" poll. This event has not started yet. This should never show up.");
        futurePoll.setEvent(futureEvent);

        Poll passedPoll = new Poll();
        passedPoll.setName(passedEvent.getName()+" poll. This event is over");
        passedPoll.setEvent(passedEvent);

        Poll currentPoll = new Poll();
        currentPoll.setName(currentEvent.getName()+" poll. This is happening right now! Give us your feedback");
        currentPoll.setEvent(currentEvent);

        pollRepository.saveAll(Arrays.asList(futurePoll,passedPoll, currentPoll));

        List<Question> questionsCurrentPoll = createQuestions(currentPoll);
        createQuestions(passedPoll);
        createQuestions(futurePoll);

        Ticket ticketCurrentEventSpect1 = new Ticket();
        ticketCurrentEventSpect1.setSpectator(spectator1);
        ticketCurrentEventSpect1.setEvent(currentEvent);
        ticketCurrentEventSpect1.setTicketNumber("T-"+15348);
        ticketCurrentEventSpect1.setEndDate(currentEvent.getEndDate());
        ticketCurrentEventSpect1.setStartDate(currentEvent.getStartDate());

        Ticket ticketFutureEventSpect1 = new Ticket();
        ticketFutureEventSpect1.setSpectator(spectator1);
        ticketFutureEventSpect1.setEvent(futureEvent);
        ticketFutureEventSpect1.setTicketNumber("T-"+764893);
        ticketFutureEventSpect1.setEndDate(futureEvent.getEndDate());
        ticketFutureEventSpect1.setStartDate(futureEvent.getStartDate());

        Ticket ticketCurrentEventSpect2 = new Ticket();
        ticketCurrentEventSpect2.setSpectator(spectator2);
        ticketCurrentEventSpect2.setEvent(currentEvent);
        ticketCurrentEventSpect2.setTicketNumber("T-"+15498);
        ticketCurrentEventSpect2.setStartDate(generateRandomDate(currentEvent.getStartDate(),currentEvent.getEndDate()));
        ticketCurrentEventSpect2.setEndDate(generateRandomDate(ticketCurrentEventSpect2.getStartDate(),currentEvent.getEndDate()));

        Ticket ticketPassedEventSpect2 = new Ticket();
        ticketPassedEventSpect2.setSpectator(spectator2);
        ticketPassedEventSpect2.setEvent(passedEvent);
        ticketPassedEventSpect2.setTicketNumber("T-"+793456);
        ticketPassedEventSpect2.setStartDate(generateRandomDate(passedEvent.getStartDate(),passedEvent.getEndDate()));
        ticketPassedEventSpect2.setEndDate(generateRandomDate(ticketPassedEventSpect2.getStartDate(),passedEvent.getEndDate()));

        ticketRepository.saveAll(Arrays.asList(ticketCurrentEventSpect1,ticketPassedEventSpect2,ticketPassedEventSpect2));

        Response sampleResponse = new Response();
        sampleResponse.setSpectator(spectator1);
        sampleResponse.setChoice(choiceRepository.findByQuestionIdQuestion(questionsCurrentPoll.getFirst().getIdQuestion()).getFirst());
        responseRepository.save(sampleResponse);
    }
    
    public Date generateRandomDate(Date minDate, Date maxDate) {
        long now = System.currentTimeMillis();
        long lowerBound = (minDate != null) ? minDate.getTime() : now;
        long upperBound = (maxDate != null) ? maxDate.getTime() : now;

        long randomTime = lowerBound + (long) (Math.random() * (upperBound - lowerBound));

        return new Date(randomTime);
    }

    public Date generateRandomDateInFuture(int daysOffset) {
        long millisInFuture = (long) (Math.random() * TimeUnit.DAYS.toMillis(daysOffset));
        long now = System.currentTimeMillis();
        long futureTime = now + millisInFuture;
        return new Date(futureTime);
    }

    public Date generateRandomDateInPast(int daysOffset) {
        long millisInPast = (long) (Math.random() * TimeUnit.DAYS.toMillis(daysOffset));
        long now = System.currentTimeMillis();
        long futureTime = now - millisInPast;
        return new Date(futureTime);
    }

    private List<Question> createQuestions(Poll poll) {

        Question question1 = createQuestion("Quelle note donnez-vous à l’ambiance sonore de l’événement ?", poll, "1", "2", "3", "4", "5");

        Question question2 = createQuestion("Quel est votre favori pour la finale du tournoi « League Of Legends » ?", poll, "Avengers", "Gamers Legends");

        return Arrays.asList(question1,question2);
    }

    private Question createQuestion(String title,Poll poll,  String... choices) {
        Question question = new Question();
        question.setContent(title);
        question.setExpirationDate(poll.getEvent().getEndDate());
        question.setPoll(poll);
        questionRepository.save(question);

        for (String choiceText : choices) {
            Choice choice = new Choice();
            choice.setContent(choiceText);
            choice.setQuestion(question);
            choiceRepository.save(choice);
        }

        return question;
    }
}

