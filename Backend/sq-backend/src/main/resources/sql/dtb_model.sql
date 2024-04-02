CREATE TABLE spectator(
   id_spectator INT,
   firstname VARCHAR(25),
   lastname VARCHAR(50),
   date_of_birth DATE,
   email VARCHAR(75) NOT NULL,
   adress VARCHAR(200),
   PRIMARY KEY(id_spectator),
   UNIQUE(email)
 );   

CREATE TABLE event(
   id_event INT,
   name VARCHAR(50),
   start_date DATE,
   end_date DATE,
   PRIMARY KEY(id_event)
);   

CREATE TABLE poll(
   id_poll INT,
   id_event INT NOT NULL,
   PRIMARY KEY(id_poll),
   FOREIGN KEY(id_event) REFERENCES event(id_event)
);  

CREATE TABLE question(
   id_question INT,
   content VARCHAR(50),
   expiration_date DATETIME,
   id_poll INT NOT NULL,
   PRIMARY KEY(id_question),
   FOREIGN KEY(id_poll) REFERENCES poll(id_poll)
);  

CREATE TABLE choice(
   id_choice INT,
   content VARCHAR(50) NOT NULL,
   id_question INT NOT NULL,
   PRIMARY KEY(id_choice),
   FOREIGN KEY(id_question) REFERENCES question(id_question)
);   

CREATE TABLE ticket(
   id_spectator INT,
   id_event INT,
   ticket_number VARCHAR(50) NOT NULL,
   start_date DATETIME NOT NULL,
   end_date DATETIME NOT NULL,
   PRIMARY KEY(id_spectator, id_event),
   UNIQUE(ticket_number),
   FOREIGN KEY(id_spectator) REFERENCES spectator(id_spectator),
   FOREIGN KEY(id_event) REFERENCES event(id_event)
);   

CREATE TABLE response(
   id_spectator INT,
   id_choice INT,
   PRIMARY KEY(id_spectator, id_choice),
   FOREIGN KEY(id_spectator) REFERENCES spectator(id_spectator),
   FOREIGN KEY(id_choice) REFERENCES choice(id_choice)
); 