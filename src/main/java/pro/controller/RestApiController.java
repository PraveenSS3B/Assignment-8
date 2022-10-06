package pro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pro.dao.Book;
import pro.dao.Student;
import pro.repository.BookRepository;
import pro.repository.StudentRepository;
import pro.service.StudentService;

@RestController
@RequestMapping("/Api")
public class RestApiController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private BookRepository bookRepository;
	
	@PostMapping("/addStudent")
	public ResponseEntity<Student> addStudent(@RequestBody Student student) throws Exception {
		return new ResponseEntity<Student>(studentService.addStudent(student), HttpStatus.OK);
	}

	@GetMapping("/getStudentData")
	public ResponseEntity<List<Student>> getAllStudents() {
		return new ResponseEntity<List<Student>>(studentRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/getStudentById")
	public ResponseEntity<Student> getStudent(@RequestParam Long id) throws Exception {
		return new ResponseEntity<Student>(studentService.getStudentById(id), HttpStatus.OK);
	}
	
	@GetMapping("/getStudentByName")
	public ResponseEntity<Student> getStudent(@RequestParam String name) throws Exception {
		return new ResponseEntity<Student>(studentService.getStudentByName(name), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllStudentXML", produces = { "application/xml", "text/xml" }, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<List<Student>> getAllStudent() {
		return new ResponseEntity<List<Student>>(studentRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/getStudentByIdXML", produces = { "application/xml", "text/xml" }, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Student> getStudentById(@RequestParam(required = true) Long id) throws Exception {
		return new ResponseEntity<Student>(
				studentRepository.findById(id).orElseThrow(() -> new Exception("Student Id not found in database")),
				HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllBooks", produces = { "application/xml", "text/xml" }, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<List<Book>> getAllBooks() {
		return new ResponseEntity<List<Book>>(bookRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/getBook", produces = { "application/xml", "text/xml" }, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Book> getBookById(@RequestParam(required = true) String id) throws Exception {
		return new ResponseEntity<Book>(
				bookRepository.findById(id).orElseThrow(() -> new Exception("Book Id not found in database")),
				HttpStatus.OK);
	}
	

}
