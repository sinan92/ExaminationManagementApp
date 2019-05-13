package com.aon04.backend.configuration;

import com.aon04.backend.models.*;
import com.aon04.backend.repository.IExamRepository;
import com.aon04.backend.repository.IFinishedExamRepository;
import com.aon04.backend.repository.IRolesRepository;
import com.aon04.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements ApplicationRunner {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IExamRepository examRepository;
    @Autowired
    IRolesRepository rolesRepository;
    @Autowired
    IFinishedExamRepository finishedExamRepository;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        seedRolesTable();
        seedUsersTable();

        try {
            seedExamsTable();
        } catch (IOException e) {
            System.out.println("Exception " + e.getMessage() + " thrown during seeding.");
        }

        seedFinishedExamTabel();
    }

    private void seedUsersTable() {
        long userCount = userRepository.count();

        if (userCount != 0) {
            return;
        }

        List<User> users = new ArrayList<>();
        String[] usersFirstNames = {"Mathijs", "Giel", "Laura", "Stijn", "Joeri", "Daan", "Michelle", "Gert", "Adriaan", "Vincent", "Nele", "Jeroen", "Jan Pieter", "David", "Thijs", "Lars", "Jan", "Brecht", "Jolien", "Eline"};
        String[] usersLastNames = {"Boll", "van den Graven", "Boschman", "van de Vos", "Oosterbroek", "Steenbergen", "Smit", "Joosten", "Ruiter", "Wolters", "Mollemans", "Eijkenaar", "Ooms", "Elbers", "Huiverman", "van de Weide", "Konink", "Straatman", "Linteloofs", "Blokvoort"};
        Random random = new Random();

        User supervisor = new User();
        supervisor.setFirstName("root");
        supervisor.setLastName("root");
        supervisor.setStudentNumber("99999999");

        Role supervisorRole = new Role();
        supervisorRole.setId(2);

        supervisor.setRole(supervisorRole);


        users.add(supervisor);

        for (int i = 0; i < usersFirstNames.length; i++) {
            User newUser = new User();
            Role role = new Role();
            role.setId(1);
            newUser.setFirstName(usersFirstNames[i]);
            newUser.setLastName(usersLastNames[i]);
            newUser.setRole(role);
            newUser.setStudentNumber(random.nextInt(11999999 - 11000000) + 11000000 + "");

            users.add(newUser);
        }


        userRepository.saveAll(users);
    }

    private void seedExamsTable() throws IOException {
        long examCount = examRepository.count();

        if (examCount != 0) {
            return;
        }

        String filename;
        File file;

        List<Exam> exams = new ArrayList<>();
        Exam web = new Exam();
        web.setNaam("Web Advanced");
        filename = "Web_Advanced_skelet.txt";
        web.setSkelet(filename);
        exams.add(web);

       File webfile = new File("C:\\gitproject\\2018_AON04\\backend\\null\\aon04\\upload-dir\\" + filename);
        webfile.createNewFile();

        Exam java = new Exam();
        java.setNaam("Programming Advanced");
        filename = "Programming_Advanced_skelet.txt";
        java.setSkelet(filename);
        exams.add(java);

        File javaFile = new File("C:\\gitproject\\2018_AON04\\backend\\null\\aon04\\upload-dir\\" + filename);
        javaFile.createNewFile();

        Exam basicSecurity = new Exam();
        basicSecurity.setNaam("Basic Security");
        filename = "Basic_Security_skelet.txt";
        basicSecurity.setSkelet(filename);
        exams.add(basicSecurity);

        File basicSecurityFile = new File("C:\\gitproject\\2018_AON04\\backend\\null\\aon04\\upload-dir\\" + filename);
        basicSecurityFile.createNewFile();

        examRepository.saveAll(exams);
    }


    private void seedRolesTable() {
        long rolesCount = rolesRepository.count();

        if (rolesCount == 2) {
            return;
        }

        List<Role> roles = new ArrayList<>();
        List<String> roleNames = new ArrayList<>();

        roleNames.add("Student");
        roleNames.add("Supervisor");

        for (int i = 0; i < 2; i++) {
            Role newRole = new Role();
            newRole.setId(i + 1);
            newRole.setName(roleNames.get(i));
            roles.add(newRole);
        }

        rolesRepository.deleteAll();
        rolesRepository.saveAll(roles);

    }

    private void seedFinishedExamTabel() throws NoSuchAlgorithmException {
        long finishedExamCount = finishedExamRepository.count();
        String filename;
        Md5HashBuilder md5HashBuilder = new Md5HashBuilder();
        Iterable<File> files = new ArrayList<>();


        if (finishedExamCount != 0) return;

        List<FinishedExam> finishedExams = new ArrayList<>();
        Exam exam = examRepository.findById(1).get();

        for (int i = 2; i < 9; i++) {
            FinishedExam newFinishedExam = new FinishedExam();
            User user = userRepository.findById(i).get();
            newFinishedExam.setExam(exam);
            filename = exam.getNaam().replace(" ","_") + "_" + user.getFirstName().replace(" ","") + user.getLastName().replace(" ","");
            newFinishedExam.setFinishedExam(filename + ".txt");

            File file = new File("C:\\gitproject\\2018_AON04\\backend\\null\\aon04\\upload-dir\\" + filename + ".txt");
            //  File file = new File("backend" + File.separator + "null"  + File.separator +" aon04"  + File.separator + "upload-dir"   + File.separator + filename);

            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer.println(new Random().nextInt());
            writer.close();



            try {
                newFinishedExam.setHash(md5HashBuilder.createMD5Hash(new FileInputStream(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            newFinishedExam.setUser(user);
            finishedExams.add(newFinishedExam);
        }

        finishedExamRepository.deleteAll();

        finishedExamRepository.saveAll(finishedExams);
    }
}
