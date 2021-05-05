package com.demo.in.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.demo.in.model.Person;
import com.demo.in.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	PersonRepository personRepository;

	private JavaMailSender javaMailSender;

	public PersonServiceImpl(PersonRepository personRepository, JavaMailSender mailSender) {
		this.personRepository = personRepository;
		this.javaMailSender = mailSender;
	}

	// Get the list of Person details from the database
	public List<Person> findAll() {
		List<Person> list = personRepository.findAll();
		return list;
	}

	// Create Person detail to the database

	public Person save(Person person) {
		sendEmail(person);
		Person data = personRepository.save(person);
		return data;
	}

	// Get the particular of Person detail from the database

	public Person findById(int id) {
		Person data = personRepository.findById(id);
		return data;
	}

	// Delete a specific Person details from the database

	public void deleteById(int id) {
		personRepository.deleteById(id);
	}

	// Update the particular of Person detail from the database

	public Person update(Person person) {
		Person data = personRepository.save(person);
		return data;
	}

	/**
	 * This function is used to send mail without attachment.
	 * @param person
	 * @throws MailException
	 */

	public void sendEmail(Person person) throws MailException {

		/*
		 * This JavaMailSender Interface is used to send Mail in Spring Boot. This
		 * JavaMailSender extends the MailSender Interface which contains send()
		 * function. SimpleMailMessage Object is required because send() function uses
		 * object of SimpleMailMessage as a Parameter
		 */

		SimpleMailMessage mail = new SimpleMailMessage();
		//mail.setTo(person.getEmail());
		mail.setTo("abc12341sa@you-spam.com");
		mail.setSubject("Testing Mail API");
		mail.setText("Hurray ! You have done that dude...");

		/*
		 * This send() contains an Object of SimpleMailMessage as an Parameter
		 */
		javaMailSender.send(mail);
	}

	/**
	 * This function is used to send mail that contains a attachment.
	 * 
	 * @param person
	 * @throws MailException
	 * @throws MessagingException
	 */
	public void sendEmailWithAttachment(Person person) throws MailException, MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(person.getEmail());
		helper.setSubject("Testing Mail API with Attachment");
		helper.setText("Please find the attached document below.");

		ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
		helper.addAttachment(classPathResource.getFilename(), classPathResource);

		javaMailSender.send(mimeMessage);
	}


}
