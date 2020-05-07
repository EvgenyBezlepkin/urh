package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.IntermediateResultRepository;
import com.example.demo.repository.ResultRepository;
import com.example.demo.repository.RewievRepository;
import com.example.demo.repository.ServiceTemporalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.DoubleStream;

@Controller
public class Main {

    @Autowired
    private ResultRepository resultRepository;
    @Qualifier("getJavaMailSender")
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private IntermediateResultRepository intermediateResult;
    @Autowired
    private RewievRepository rewievRepository;
    @Autowired
    private ServiceTemporalRepository serviceTemporalRepository;

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @PostMapping("/")
    public String getIndex(@ModelAttribute Result result) {
        result.setLdt(LocalDateTime.now());
        resultRepository.save(result);
        sendEmail(result);
        return "redirect:/";
    }


    @GetMapping("/test")
    public String startTest(Model model) {
        model.addAttribute("test", new Test());
        return "test";
    }


    @RequestMapping("/result")
    public String resTest(Model model, @ModelAttribute Test item) {
        IntermediateResult interRes = countTest(item);
        String res = countResult(interRes);
        res = res.replaceAll("%. ", "%<br>");
        intermediateResult.save(interRes);
        model.addAttribute("result", new Result(item.getAge(), item.getName(), res, interRes));
        return "result";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/our_services")
    public String getServicesPage(Model model) {
        model.addAttribute("serviceTemporal", new ServiceTemporal());
        model.addAttribute("count", resultRepository.count());
        return "our_servicesTemporal";
    }

    @PostMapping("/our_services")
    public String saveServicesPage(@ModelAttribute ServiceTemporal st) {
        serviceTemporalRepository.save(st);
        return "redirect:/our_services";
    }


    @GetMapping("/contacts")
    public String getContactsPage(Model model) {
        model.addAttribute("rewiev", new Rewiev());
        return "contacts";
    }

//    @PostMapping("/contacts")
//    public String saveRec(@ModelAttribute Rewiev rewiev) {
////        rewievRepository.save(rewiev);
//
//        System.out.println(rewiev.getEmail());
//        return "contacts";
//    }


    private void sendEmail(Result result) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg1 = "<br>" +
                "<h2 style='text-align: center;'> Result Interpretation<br>" +
                "Professional activity profiles</h2>" +

                "All people are divided on 5 groups by professional activity type:" +
                "<ol>" +
                "<li>Theorist-researcher</li>" +
                "<li>Technician-executor</li>" +
                "<li>Leader-teacher</li>" +
                "<li>Social activist-philanthropist</li>" +
                "<li>Creator-artist</li>" +
                " </ol>" +
                "Different goals, work with people features, work process and results are characteristic for every group.<br>" +
                "<br>" +
                "<br>" +
                "<h2>Theorist-researcher</h2><br>" +
                "These are people whose most important values are knowledge and truth. Their work requires deepening in the details of the specialty, the thirst for new knowledge and an objective judgment about the world. Depending on the specialty, research theorists can to one degree or another interact with other people, but more often their work is secluded. These are people who can defend their point of view, resorting to the facts and results of their own research and search.<br>" +
                "<br>" +
                "Professional areas where research theorists are most useful:" +
                "<ul>" +
                "<li>Medical Science</li>" +
                "<li>Engineer Science</li>" +
                "<li>Physics</li>" +
                "<li>Maths</li>" +
                "<li>Biology</li>" +
                "<li>Chemistry</li>" +
                "<li>Geological Sciences</li>" +
                "<li>IT</li>" +
                "<li>Humanitarian Sciences</li>" +
                "</ul>" +
                "Examples of professions in these areas: physiologist; pathophysiologist; microbilogue; biologist; process " +
                "chemist; Data Analyst, Linguist, research engineer, IT specialist, laboratory assistant, geologist, ecologist, " +
                "geophysicist, guide.<br>" +
                "<br>" +
                "<br>" +
                "<h2>Technician-executor</h2><br>" +
                "Despite the name, this group includes not only technical professions, but also all specialties that are " +
                "characterized by the performance of work. These are people who act in accordance " +
                "with clear instructions. Compliance with algorithms and standards is easy for them. Their work requires " +
                "perseverance, patience and the ability to follow instructions without departing from them. Depending on " +
                "their specialization, performers to one degree or another interact with people, but more often their work " +
                "is not solitary, they can work together with colleagues who perform the same or other duties.<br>" +
                "<br>" +
                "Professional areas, where performers are most useful:<br>" +
                "<ul>" +
                "<li>Constructing</li>" +
                "<li>Manufacturing</li>" +
                "<li>Accounting</li>" +
                "<li>Jurisprudence</li>" +
                "<li>Sport, Fitness</li>" +
                "<li>Public Service</li>" +
                "</ul>" +
                "Examples of professions in these areas: design engineer; builder; cook; production technologist; " +
                "metallurgist; Energy Engineer; process engineer; pharmacist; electrician-installer; driver; master of " +
                "construction works; locksmith; driver; accountant; collector; auditor; financial analyst; lawyer; realtor; " +
                "notary; lawyer; sports instructor; trainer; inspector; Secretary policeman.<br>" +
                "<br>" +
                "<br>" +
                "<h2>Leader-teacher</h2><br>" +
                "For this group of people, the most suitable are professions where a person somehow realizes his " +
                "inclinations and skills of a leader and manager. Such work requires a person to be able to work with " +
                "people, good speech skills and the absence of constraint in front of a large audience. And how to implement " +
                "these skills depends on the goal of the person himself: he can direct them into the mainstream of teaching, " +
                "entrepreneurship or management. In any case, it is necessary to be able to clearly set goals and work out an " +
                "action strategy (in the case of teaching, a training program).<br>" +
                "<br>" +
                "Professional areas, where the most useful is a teaching leaders:<br>" +
                "<ul>" +
                "<li>Selling</li>" +
                "<li>Administrative Staff</li>" +
                "<li>Marketing</li>" +
                "<li>Consulting</li>" +
                "<li>Management</li>" +
                "<li>Education</li>" +
                "</ul>" +
                "Examples of professions in these areas: customer service manager; shop assistant; Sales Manager; " +
                "Sales Representative; call center employee; Office Manager; assistant manager; PR specialist; marketer; " +
                "promoter; business trainer; Director recruitment manager (recruiter); teacher.<br>" +
                "<br>" +
                "<br>" +
                "<h2>Social activist-philanthropist</h2><br>" +
                "These people are primarily driven by a desire to help others, and already in the second - to engage " +
                "in self-development, financially provide for themselves, etc. However, there are professions that perfectly " +
                "combine these two aspects. In addition to the desire to help people, you need the ability to see social " +
                "problems, as well as the skill to find their solutions. The art of persuasion and empathy may turn out to be " +
                "important here, depending on the chosen specialty.<br>" +
                "<br>" +
                "Professional areas, where social activists-philanthropists are most useful:<br>" +
                "<ul>" +
                "<li>Occupational Safety and Health</li>" +
                "<li>Certification</li>" +
                "<li>Medicine</li>" +
                "<li>Safety</li>" +
                "</ul>" +
                "Examples of professions in these areas: labor safety specialist; Licensing security guard; the auditor; " +
                "Therapist; Nurse; psychologist; firefighter of the Emergency Management organisation; sanitary inspector.<br>" +
                "<br>" +
                "<br>" +
                "<h2>Creator-artist</h2><br>" +
                "Surely you have some kind of creative skill: fine art (including painting and sculpture), " +
                "music, photography, video production, theater and cinema, etc. For creativity to become a matter " +
                "of your life, you need to prepare for serious competition, as well as have communication and " +
                "persuasion skills. Creativity can generate income when you learn to sell it. In addition, " +
                "despite the prejudices, the work of the creator also consists of certain obligations: to complete " +
                "the work on time, despite the lack of inspiration or muse, and also to perform it qualitatively. Therefore, " +
                "the ability to organize your time is another extremely useful skill for success in this area.<br>" +
                "<br>" +
                "Professional areas where artists and creators are most useful:<br>" +
                "<ul>" +
                "<li>Design</li>" +
                "<li>Photography</li>" +
                "<li>Fashion</li>" +
                "<li>Music</li>" +
                "<li>Cinema</li>" +
                "<li>Video production</li>" +
                "<li>Literature</li>" +
                "</ul>" +
                "Examples of professions in these areas: designer; photographer; videographer; atelier; Art Director; " +
                "producer; film director; decorator; florist; cameraman; video editor; actor / actress; jeweler; screenwriter; " +
                "copywriter; art critic; choreographer; vocalist." +
                "Bonus! Look at our mask on Instagram (tap to find out your profession):" +
                    "<a href='https://www.instagram.com/a/r/?effect_id=1012474792460678'>instagram.com</a>"
                ;

        String res = result.getOuts().replaceAll("%. ", "%<br>");
        String htmlMsg =
                "<h2 style='text-align: center;'>" + "Hello, " + result.getName() + "!</h2>" +
                        "<p style='text-align: center;'>" + res + "</p>"
                        + htmlMsg1;
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(result.getEmail());
            helper.setSubject("Your test results");
            helper.setFrom("info@urhumanresource@mail.com");
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
        emailSender.send(mimeMessage);
    }

    private IntermediateResult countTest(Test item) {
        // TE: техник-исполнитель; ТО: теоретик-исследователь; LP: лидер-преподаватель;OF: общественник-филантроп; TА: творец-артист
        int TE = 0;
        int TO = 0;
        int LP = 0;
        int OF = 0;
        int TA = 0;
//SUM: Сумма всех баллов
        int SUM;
//СРЕДИ ВСЕХ СФЕР TOS - % теоретика среди всех сфер; TES - % техника от всех сфер; LPS - % лидера от всех сфер; OFS - % общественника среди всех сфер; TAS - % творца среди всех сфер
        double TOS = 0;
        double TES = 0;
        double LPS = 0;
        double OFS = 0;
        double TAS = 0;
//ВНУТРИ СВОЕЙ СФЕРЫ TOV - % теоретика внутри своей сферы; TEV - % техника внтури своей сферы; LPV - % лидера внутри своей сферы; OFV - % общественника внутри своей сферы; TAV - % творца внутри своей сферы
        double TOV = 0;
        double TEV = 0;
        double LPV = 0;
        double OFV = 0;
        double TAV = 0;

//Максимум среди всех сфер
        double MAX = 0;


//1 ВОПРОС: Тех и Общ


        if (item.getA() == -2 | item.getA() == -1) {
            TE = TE + Math.abs(item.getA()) + 1;
            //console.log("Техник1 " + TE)
        }

        if (item.getA() == 1 | item.getA() == 2) {
            OF = OF + Math.abs(item.getA()) + 1;
            //console.log("Общественник1 "+OF)
        }
        if (item.getA() == 0) {
            //console.log("Nothing")
        }


//2 ВОПРОС: Общ и Теор

        if (item.getB() == -2 | item.getB() == -1) {
            OF = OF + Math.abs(item.getB()) + 1;
            //console.log("Общественник2 " + OF)
        }

        if (item.getB() == 1 | item.getB() == 2) {
            TO = TO + Math.abs(item.getB()) + 1;
            //console.log("Теоретик1 " + TO)
        }
        if (item.getB() == 0) {
            //console.log("Nothing")
        }

//3 ВОПРОС: Теор и Лид; И ТО, И ТО

        if (item.getD() == -2 | item.getD() == -1) {
            TO = TO + Math.abs(item.getD()) + 1;
            //console.log("Теоретик2 " + TO)
        }

        if (item.getD() == 2 | item.getD() == 3) {
            LP = LP + Math.abs(item.getD());
            //console.log("Лидер1 " + LP)
        }
        if (item.getD() == 1) {
            TO = TO + 2;
            LP = LP + 2;
            //console.log("Теор2 " + TO + " " + "Лидер1 " + LP)
        }
        if (item.getD() == 0) {
            //console.log("Nothing")
        }

//4 ВОПРОС: Общественник и Творец; И ТО, И ТО

        if (item.getE() == -2 | item.getE() == -1) {
            OF = OF + Math.abs(item.getE()) + 1;
            //console.log("Общественник3 " + OF)
        }

        if (item.getE() == 2 | item.getE() == 3) {
            TA = TA + Math.abs(item.getE());
            //console.log("Творец1 " + TA)
        }
        if (item.getE() == 1) {
            OF = OF + 2;
            TA = TA + 2;
            //console.log("Общ3 " + OF + " " + "Творец1 " + TA)
        }
        if (item.getE() == 0) {
            //console.log("Nothing")
        }

//5 ВОПРОС: Техник и Теоретик

        if (item.getF() == -2 | item.getF() == -1) {
            TE = TE + Math.abs(item.getF()) + 1;
            //console.log("Техник2 " + TE)
        }

        if (item.getF() == 1 | item.getF() == 2) {
            TO = TO + Math.abs(item.getF()) + 1;
            //console.log("Теоретик3 " + TO)
        }
        if (item.getF() == 0) {
            //console.log("Nothing")
        }

//6 ВОПРОС: Творец и Техник

        if (item.getG() == -2 | item.getG() == -1) {
            TA = TA + Math.abs(item.getG()) + 1;
            //console.log("Творец2 " + TA)
        }

        if (item.getG() == 1 | item.getG() == 2) {
            TE = TE + Math.abs(item.getG()) + 1;
            //console.log("Техник3 " + TE)
        }
        if (item.getG() == 0) {
            //console.log("Nothing")
        }

//7 ВОПРОС: Техник и Общественник;  И ТО, И ТО

        if (item.getH() == -2 | item.getH() == -1) {
            TE = TE + Math.abs(item.getH()) + 1;
            //console.log("Техник4 " + TE)
        }

        if (item.getH() == 2 | item.getH() == 3) {
            OF = OF + Math.abs(item.getH());
            //console.log("Общественник4 " + OF)
        }
        if (item.getH() == 1) {
            TE = TE + 2;
            OF = OF + 2;
            //console.log("Техник4 " + TE + " " + "Общ4 " + OF)
        }
        if (item.getH() == 0) {
            //console.log("Nothing")
        }

//8 ВОПРОС: Лидер и Техник

        if (item.getK() == -2 | item.getK() == -1) {
            LP = LP + Math.abs(item.getK()) + 1;
            //console.log("Лидер2 " + LP)
        }

        if (item.getK() == 1 | item.getK() == 2) {
            TE = TE + Math.abs(item.getK()) + 1;
            //console.log("Техник5 " + TE)
        }
        if (item.getK() == 0) {
            //console.log("Nothing")
        }

//9 ВОПРОС: Техник и Лидер

        if (item.getL() == -2 | item.getL() == -1) {
            TE = TE + Math.abs(item.getL()) + 1;
            //console.log("Техник6 " + TE)
        }

        if (item.getL() == 1 | item.getL() == 2) {
            LP = LP + Math.abs(item.getL()) + 1;
            //console.log("Лидер3 " + LP)
        }
        if (item.getL() == 0) {
            //console.log("Nothing")
        }

//10 ВОПРОС: Творец и Теоретик

        if (item.getM() == -2 | item.getM() == -1) {
            TA = TA + Math.abs(item.getM()) + 1;
            //console.log("Творец3 " + TA)
        }
        if (item.getM() == 1 | item.getM() == 2) {
            TO = TO + Math.abs(item.getM()) + 1;
            //console.log("Теоретик4 " + TO)
        }
        if (item.getM() == 0) {
            //console.log("Nothing")
        }

//11 ВОПРОС: Теоретик и Техник

        if (item.getN() == -2 | item.getN() == -1) {
            TO = TO + Math.abs(item.getN()) + 1;
            //console.log("Теоретик5 " + TO)
        }

        if (item.getN() == 1 | item.getN() == 2) {
            TE = TE + Math.abs(item.getN()) + 1;
            //console.log("Техник7 " + TE)
        }
        if (item.getN() == 0) {
            //console.log("Nothing")
        }

//12 ВОПРОС: Техник и Творец

        if (item.getO() == -2 | item.getO() == -1) {
            TE = TE + Math.abs(item.getO()) + 1;
            //console.log("Техник8 " + TE)
        }

        if (item.getO() == 1 | item.getO() == 2) {
            TA = TA + Math.abs(item.getO()) + 1;
            //console.log("Творец4 " + TA)
        }
        if (item.getO() == 0) {
            //console.log("Nothing")
        }

//13 ВОПРОС: Творец и Лидер; И ТО, И ТО

        if (item.getP() == -2 | item.getP() == -1) {
            TA = TA + Math.abs(item.getP()) + 1;
            //console.log("Творец5 " + TA)
        }

        if (item.getP() == 2 | item.getP() == 3) {
            LP = LP + Math.abs(item.getP());
            //console.log("Лидер4 " + LP)
        }
        if (item.getP() == 1) {
            TA = TA + 2;
            LP = LP + 2;
            //console.log("Творец5 " + TA + " " + "Лидер4 " + LP)
        }
        if (item.getP() == 0) {
            //console.log("Nothing")
        }

//14 ВОПРОС: Лидер и Теоретик

        if (item.getR() == -2 | item.getR() == -1) {
            LP = LP + Math.abs(item.getR()) + 1;
            //console.log("Лидер5 " + LP)
        }

        if (item.getR() == 1 | item.getR() == 2) {
            TO = TO + Math.abs(item.getR()) + 1;
            //console.log("Теоретик6 " + TO)
        }
        if (item.getR() == 0) {
            //console.log("Nothing")
        }

//15 ВОПРОС: Общественник и Лидер; И ТО, И ТО

        if (item.getS() == -2 | item.getS() == -1) {
            OF = OF + Math.abs(item.getS()) + 1;
            //console.log("Общественник5 " + OF)
        }

        if (item.getS() == 2 | item.getS() == 3) {
            LP = LP + Math.abs(item.getS());
            //console.log("Лидер6 " + LP)
        }
        if (item.getS() == 1) {
            OF = OF + 2;
            LP = LP + 2;
            //console.log("Общ5 " + OF + " " + "Лидер6 " + LP)
        }
        if (item.getS() == 0) {
            //console.log("Nothing")
        }

//16 ВОПРОС: Теоретик и Общественник

        if (item.getT() == -2 | item.getT() == -1) {
            TO = TO + Math.abs(item.getT()) + 1;
            //console.log("Теоретик7 " + TO)
        }

        if (item.getT() == 1 | item.getT() == 2) {
            OF = OF + Math.abs(item.getT()) + 1;
            //console.log("Общест6 " + OF)
        }
        if (item.getT() == 0) {
            //console.log("Nothing")
        }

//17 ВОПРОС: Общественник и Лидер; И ТО, И ТО

        if (item.getX() == -2 | item.getX() == -1) {
            OF = OF + Math.abs(item.getX()) + 1;
            //console.log("Общественник7 " + OF)
        }

        if (item.getX() == 2 | item.getX() == 3) {
            LP = LP + Math.abs(item.getX());
            //console.log("Лидер7 " + LP)
        }
        if (item.getX() == 1) {
            OF = OF + 2;
            LP = LP + 2;
            //console.log("Общ7 " + OF + " " + "Лидер7 " + LP)
        }
        if (item.getX() == 0) {
            //console.log("Nothing")
        }

//18 ВОПРОС: Лидер и Теоретик

        if (item.getY() == -2 | item.getY() == -1) {
            LP = LP + Math.abs(item.getY()) + 1;
            //console.log("Лидер8 " + LP)
        }

        if (item.getY() == 1 | item.getY() == 2) {
            TO = TO + Math.abs(item.getY()) + 1;
            //console.log("Теоретик8 " + TO)
        }
        if (item.getY() == 0) {
            //console.log("Nothing")
        }

//19 ВОПРОС: Техник и Общественник

        if (item.getZ() == -2 | item.getZ() == -1) {
            TE = TE + Math.abs(item.getZ()) + 1;
            //console.log("Техник9 " + TE)
        }

        if (item.getZ() == 1 | item.getZ() == 2) {
            OF = OF + Math.abs(item.getZ()) + 1;
            // console.log("Общест8 " + OF)
        }
        if (item.getZ() == 0) {
            //console.log("Nothing")
        }

//20 ВОПРОС: Общественник и Творец

        if (item.getQ() == -2 | item.getQ() == -1) {
            OF = OF + Math.abs(item.getQ()) + 1;
            //console.log("Общест9 " + OF)
        }

        if (item.getQ() == 1 | item.getQ() == 2) {
            TA = TA + Math.abs(item.getQ()) + 1;
            //console.log("Творец6 " + TA)
        }
        if (item.getQ() == 0) {
            //console.log("Nothing")
        }

//21 ВОПРОС: Теоретик и Лидер

        if (item.getW() == -2 | item.getW() == -1) {
            TO = TO + Math.abs(item.getW()) + 1;
            //console.log("Теоретик9 " + TO)
        }

        if (item.getW() == 1 | item.getW() == 2) {
            LP = LP + Math.abs(item.getW()) + 1;
            //console.log("Лидер9 " + LP)
        }
        if (item.getW() == 0) {
            //console.log("Nothing")
        }

//22 ВОПРОС: Лидер и Творец

        if (item.getU() == -2 | item.getU() == -1) {
            LP = LP + Math.abs(item.getU()) + 1;
            //console.log("Лидер10 " + LP)
        }

        if (item.getU() == 1 | item.getU() == 2) {
            TA = TA + Math.abs(item.getU()) + 1;
            //console.log("Творец7 " + TA)
        }
        if (item.getU() == 0) {
            //console.log("Nothing")
        }

//23 ВОПРОС: Творец и Теоретик

        if (item.getI() == -2 | item.getI() == -1) {
            TA = TA + Math.abs(item.getI()) + 1;
            //console.log("Творец8 " + TA)
        }

        if (item.getI() == 1 | item.getI() == 2) {
            TO = TO + Math.abs(item.getI()) + 1;
            //console.log("Теоретик10 " + TO)
        }
        if (item.getI() == 0) {
            //console.log("Nothing")
        }

//ПРОМЕЖУТОЧНЫЕ БАЛЛЫ
//console.log("Теоретик "+ TO + "; Техник " + TE + "; Лидер " + LP + "; Общественник " + OF + "; Творец " + TA + ".")

//СУММА ВСЕХ БАЛЛОВ
        SUM = TO + TE + LP + OF + TA;

//console.log(SUM)

//СРЕДИ ВСЕХ СФЕР %
//Теоретик
        TOS = ((double) (TO) / SUM * 100);

        System.out.println(TOS);
//console.log("Доля среди всех сфер: " + TOS + "%")
//Техни
        TES = ((double) (TE) / SUM * 100);
//console.log("Доля среди всех сфер: " + TES + "%")
//Лидер
        LPS = ((double) (LP) / SUM * 100);
//console.log("Доля среди всех сфер: " + LPS + "%")
//Общественник
        OFS = ((double) (OF) / SUM * 100);
//console.log("Доля среди всех сфер: " + OFS + "%")
//Творец
        TAS = ((double) (TA) / SUM * 100);
//console.log("Доля среди всех сфер: " + TAS + "%")

//Внутри своей сферы
//Теоретик
        TOV = ((double) (TO) / 30 * 100);
//console.log("% внутри своей сферы " + TOV)
//Техник
        TEV = ((double) (TE) / 27 * 100);
//console.log("% внутри своей сферы " + TEV)
//Лидер
        LPV = ((double) (LP) / 30 * 100);
//console.log("% внутри своей сферы " + LPV)
//Общественник
        OFV = ((double) (OF) / 27 * 100);
//console.log("% внутри своей сферы " + OFV)
//Творец
        TAV = ((double) (TA) / 24 * 100);
//console.log("% внутри своей сферы " + TAV)

//Результат главная сфера среди всех
        MAX = DoubleStream.of(TOS, TES, LPS, OFS, TAS)
                .max().getAsDouble();

//if (MAX == TOS)
        //console.log("Наиболее выражена черта Теоретик-исследователь (" + TOS + "% среди всех сфер)")
//if (MAX == TES)
        //console.log("Наиболее выражена черта Техник-исполнитель (" + TES + "% среди всех сфер)")
//if (MAX == LPS)
        //console.log("Наиболее выражена черта Лидер-преподаватель (" + LPS + "% среди всех сфер)")
//if (MAX == OFS)
        //console.log("Наиболее выражена черта Общественник-филантроп (" + OFS + "% среди всех сфер)")
//if (MAX == TAS)
        //console.log("Наиболее выражена черта Творец-артист (" + TAS + "% среди всех сфер)")

//Весь результат

        IntermediateResult ir = new IntermediateResult((int) Math.round(TOS), (int) Math.round(TES), (int) Math.round(LPS), (int) Math.round(OFS), (int) Math.round(TAS),
                (int) Math.round(TOV), (int) Math.round(TEV), (int) Math.round(LPV), (int) Math.round(OFV), (int) Math.round(TAV),
                (int) Math.round(MAX));

        return ir;
    }

    private String countResult(IntermediateResult interRes) {

        int[] res1 = new int[]{interRes.getTOV(), interRes.getTEV(), interRes.getLPV(), interRes.getOFV(), interRes.getTAV()};
        int[] res2 = new int[]{interRes.getTOS(), interRes.getTES(), interRes.getLPS(), interRes.getOFS(), interRes.getTAS()};
        String[] res3 = new String[]{"Theorist-researcher", "Technician-executor", "Leader-teacher", "Social activist-philanthropist", "Creator-artist"};
        String out1 = "", out2 = "", out3 = "", out4 = "", out5 = "";
        String[] out = new String[]{out1, out2, out3, out4, out5};


        for (int i = 0; i < 5; i++) {
            if (res1[i] < 25) {
                out[i] = res3[i] + ": inside the sphere: " + Math.round(res1[i]) + "% (low level). Share of all spheres: " + Math.round(res2[i]) + "%. ";
            }
            if (res1[i] >= 25 & res1[i] < 50) {
                out[i] = res3[i] + ": inside the sphere: " + Math.round(res1[i]) + "% (average level). Share of all spheres: " + Math.round(res2[i]) + "%. ";
            }
            if (res1[i] >= 50 & res1[i] < 75) {
                out[i] = res3[i] + ": inside the sphere: " + Math.round(res1[i]) + "% (above average level). Share of all spheres: " + Math.round(res2[i]) + "%. ";
            }
            if (res1[i] >= 75) {
                out[i] = res3[i] + ": inside the sphere: " + Math.round(res1[i]) + "% (high level). Share of all spheres: " + Math.round(res2[i]) + "%. ";
            }
        }

        StringBuilder resOut = new StringBuilder();
        resOut.append("<ul>");
        for (int i = 0; i < 5; i++) {
            resOut.append("<li>");
            resOut.append(out[i]);
            resOut.append("</li>");
            resOut.append("\n");
        }
        resOut.append("<br>");


// внутри всех сфер
        Map<Integer, Integer> first = new TreeMap<>();
        first.put(0, interRes.getTOS());
        first.put(1, interRes.getTES());
        first.put(2, interRes.getLPS());
        first.put(3, interRes.getOFS());
        first.put(4, interRes.getTAS());

        first = sortByValue(first);

        System.out.println("Внутри всех сфер");
        first.forEach((k, v) -> System.out.print(k + " : " + v + " "));
        System.out.println();


// внутри своей сферы
        Map<Integer, Integer> second = new TreeMap<>();
        second.put(0, interRes.getTOV());
        second.put(1, interRes.getTEV());
        second.put(2, interRes.getLPV());
        second.put(3, interRes.getOFV());
        second.put(4, interRes.getTAV());

        second = sortByValue(second);

        System.out.println("Внутри своей сферы");
        second.forEach((k, v) -> System.out.print(k + " : " + v + " "));


        ArrayList<Integer> firstSorted = new ArrayList(first.values());
        ArrayList<Integer> firstKey = new ArrayList<>(first.keySet());
        ArrayList<Integer> secondKey = new ArrayList(second.keySet());
        int howFirst = 1;
        for (int i = 4; i > 2; i--) {
            if (firstSorted.get(4).equals(0)){
                howFirst = 0;
                break;
            }
            if (!firstSorted.get(i).equals(0)) {
                if (firstSorted.get(i).equals(firstSorted.get(i - 1))) {
                    howFirst++;
                }
            }
        }
        if (!firstKey.get(4).equals(secondKey.get(4))) {
            howFirst++;
        }

        System.out.println();
        System.out.println(howFirst);


        String outout = "";
        int j = 4;

        ArrayList<Integer> arrInt = new ArrayList<>(first.keySet());

        System.out.println("Итоги");
        for (int i = 0; i < howFirst; i++) {


            System.out.println(interRes.getMax());
            System.out.println(first.get(arrInt.get(j)));
            System.out.println(second.get(arrInt.get(j)));

            if ( second.get(arrInt.get(j)) < 25) {
                outout = "The most pronounced trait " + res3[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% of all spheres), low level (" + Math.round(second.get(arrInt.get(j))) + "%).";
            }
            if ( second.get(arrInt.get(j)) >= 25 & second.get(arrInt.get(j)) < 50) {
                outout = "The most pronounced trait " + res3[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% of all spheres), average level (" + Math.round(second.get(arrInt.get(j))) + "%).";
            }
            if ( second.get(arrInt.get(j)) >= 50 & second.get(arrInt.get(j)) < 75) {
                System.out.println("here");
                outout = "The most pronounced trait " + res3[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% of all spheres), above average level (" + Math.round(second.get(arrInt.get(j))) + "%).";
            }
            if ( second.get(arrInt.get(j)) >= 75) {
                outout = "The most pronounced trait " + res3[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% of all spheres), high level (" + Math.round(second.get(arrInt.get(j))) + "%).";
            }
            j--;
            resOut.append("<li>");
            resOut.append(outout);
            resOut.append("</li>");
            resOut.append("\n");
        }
        resOut.append("</ul>");
        return resOut.toString();
    }

    private Map<Integer, Integer> sortByValue(Map<Integer, Integer> unsortMap) {
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort(Comparator.comparing(o -> (o.getValue())));
        Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}
