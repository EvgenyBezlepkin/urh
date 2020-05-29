package com.example.demo.controller;

import com.example.demo.domain.Result;
import com.example.demo.domain.Rewiev;
import com.example.demo.repository.ResultRepository;
import com.example.demo.repository.RewievRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@RestController
@PropertySource("classpath:mail-message.properties")
public class MainRestController {

    private final JavaMailSender emailSender;
    private final ResultRepository resultRepository;
    private final RewievRepository rewievRepository;


    public MainRestController(JavaMailSender emailSender, ResultRepository resultRepository, RewievRepository rewievRepository) {
        this.emailSender = emailSender;
        this.resultRepository = resultRepository;
        this.rewievRepository = rewievRepository;
    }

    @RequestMapping(value = "contacts",
            method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveRec(@RequestBody Rewiev rewiev) {
        rewievRepository.save(rewiev);

    }

    @PostMapping("/")
    public String getIndex(@RequestBody Result result, @RequestHeader("Accept-Language") String langHeader) {

        String lang = langHeader.substring(0, 2);
        System.out.println(lang);

        result.setLdt(LocalDateTime.now());
        resultRepository.save(result);
        sendEmail(result, lang);
        return "redirect:/";
    }

    private void sendEmail(Result result, String lang) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsgRu =
                "<br>" +
                        "<h2 style='text-align: center;'> Интерпретация результатов<br>" +
                        "Профили профессиональной деятельности</h2>" +

                        "По типу профессиональной деятельности всех людей можно разделить на 5 групп:" +
                        "<ol>" +
                        "<li>Теоретик-исследователь</li>" +
                        "<li>Техник-исполнитель</li>" +
                        "<li>Лидер-преподаватель</li>" +
                        "<li>Общественник-филантроп</li>" +
                        "<li>Творец-артист</li>" +
                        " </ol>" +
                        "Для каждой группы характерны свои цели, особенности работы с людьми, процесса работы и результаты.<br>" +
                        "<br>" +
                        "<br>" +
                        "<h2>Теоретик-исследователь</h2><br>" +
                        "Это люди, у которых важнейшие ценности – это знание и правда. Их работа требует углубления в детали специальности, жажду новых знаний и объективное суждение о мире. В зависимости от специальности " +
                        "теоретики- исследователи могут в той или иной степени взаимодействовать с другими людьми, но чаще работа у них уединенная. Это люди, способные отстаивать свою точку зрения, прибегая к фактам и результатам " +
                        "собственных исследований и поисков.<br>" +
                        "<br>" +
                        "Профобласти, где больше всего пригодятся теоретики-исследователи:" +
                        "<ul>" +
                        "<li>Медицинские науки</li>" +
                        "<li>Инженерные науки</li>" +
                        "<li>Физика</li>" +
                        "<li>Математика</li>" +
                        "<li>Биология</li>" +
                        "<li>Химия</li>" +
                        "<li>Науки о Земле</li>" +
                        "<li>IT-технологии</li>" +
                        "<li>Гуманитарные науки</li>" +
                        "</ul>" +
                        "Примеры профессий в данных областях: физиолог; патофизиолог; микробилог; биолог; " +
                        "химик-технолог; дата-аналитик; лингвист; инженер-исследователь; ИТ-специалист; " +
                        "лаборант; геолог; эколог; геофизик; экскурсовод.<br>" +
                        "<br>" +
                        "<br>" +
                        "<h2>Техник-исполнитель</h2><br>" +
                        "Несмотря на название, к этой группе относятся не только технические профессии, но и все специальности, для которых характерен исполнительский труд. " +
                        "Это люди, эффективно действующие по четким инструкциям. Соблюдение алгоритмов и стандартов им дается легко. Их работа требует усидчивости, " +
                        "терпеливости и умения следовать инструкциям, не отклоняясь от них. " +
                        "Техники-исполнители в зависимости от специальности в той или иной степени взаимодействуют с людьми,  " +
                        "но чаще их работа не уединенная, они могут работать вместе с коллегами, выполняющими такие же или другие обязанности.<br>" +
                        "<br>" +
                        "Профобласти, где больше всего пригодятся техники-исполнители:<br>" +
                        "<ul>" +
                        "<li>Строительство</li>" +
                        "<li>Производство</li>" +
                        "<li>Бухгалтерия</li>" +
                        "<li>Юриспруденция</li>" +
                        "<li>Спорт, фитнес</li>" +
                        "<li>Госслужба</li>" +
                        "</ul>" +
                        "Примеры профессий в данных областях: инженер-конструктор; строитель; повар; технолог на производстве; " +
                        "металлург; инженер-энергетик; инженер-технолог; фармацевт; электрик-монтажник; водитель; мастер " +
                        "строительно-монтажных работ; слесарь; машинист; бухгалтер; инкассатор; аудитор; финансовый аналитик; адвокат; риэлтор; " +
                        "нотариус; юрист; спортивный инструктор; тренер; инспектор; судебный пристав; секретарь; полицейский.<br>" +
                        "<br>" +
                        "<br>" +
                        "<h2>Лидер-преподаватель</h2><br>" +
                        "Для этой группы людей наиболее подходящими являются профессии, где человек так или иначе реализует свои " +
                        "склонности и навыки лидера и управленца. Такая работа требует от человека умения работать с людьми, " +
                        "хороших речевых навыков и отсутствия стеснения перед большой аудиторией. А как реализовать эти навыки и " +
                        "умения, зависит от цели самого человека: он может направить их в русло преподавания, предпринимательства или менеджмента. " +
                        "В любом случае необходимо уметь четко выставлять цели и прорабатывать " +
                        "стратегию действий (в случае преподавательской деятельности – программу обучения).<br>" +
                        "<br>" +
                        "Профобласти, где больше всего пригодятся лидеры-преподаватели:<br>" +
                        "<ul>" +
                        "<li>Продажи</li>" +
                        "<li>Административный персонал</li>" +
                        "<li>Маркетинг</li>" +
                        "<li>Консультирование</li>" +
                        "<li>Управление персоналом</li>" +
                        "<li>Образование</li>" +
                        "</ul>" +
                        "Примеры профессий в данных областях: менеджер по работе с клиентами; продавец-консультант; менеджер по продажам; " +
                        "торговый представитель; сотрудник call-центра; офис-менеджер; помощник руководителя; PR-специалист; маркетолог; " +
                        "промоутер; бизнес-тренер; директор; менеджер по подбору персонала (рекрутер); преподаватель.<br>" +
                        "<br>" +
                        "<br>" +
                        "<h2>Общественник-филантроп</h2><br>" +
                        "Такими людьми в первую очередь движет желание помочь окружающим, а уже во вторую – заниматься саморазвитием, " +
                        "материально обеспечить себя самого и т.д. Впрочем, существуют профессии, которые прекрасно сочетают в себе два этих аспекта. " +
                        "Помимо желания помогать людям, нужно умение видеть социальные проблемы, а также навык поиска их решений. " +
                        "Немаловажным здесь может оказаться искусство убеждения и эмпатии, в зависимости от выбранной специальности.<br>" +
                        "<br>" +
                        "Профобласти, где больше всего пригодятся общественники-филантропы:<br>" +
                        "<ul>" +
                        "<li>Охрана труда</li>" +
                        "<li>Сертификация</li>" +
                        "<li>Медицина</li>" +
                        "<li>Безопасность</li>" +
                        "</ul>" +
                        "Примеры профессий в данных областях: специалист по охране труда; специалист отдела лицензирования; охранник; ревизор; " +
                        "лечащий врач; медсестра/медбрат; психолог; пожарный МЧС; санитарный эксперт.<br>" +
                        "<br>" +
                        "<br>" +
                        "<h2>Творец-артист</h2><br>" +
                        "Наверняка у Вас есть какой-то творческий навык: изобразительное искусство (включая живопись и скульптуру), " +
                        "музыка, фотография, видеопроизводство, театральное и киноискусство и т.д. Чтобы творчество стало делом " +
                        "Вашей жизни, нужно приготовиться к серьезной конкуренции, а также иметь навыки общения и убеждения. " +
                        "Творчество сможет приносить доход тогда, когда Вы научитесь его продавать. К тому же, несмотря на предубеждения, " +
                        "работа творца тоже состоит из определенных обязательств: выполнять работу в срок, не взирая на отсутствие " +
                        "вдохновения или музы, а также выполнять ее качественно. Поэтому умение организовать свое время - " +
                        "еще один чрезвычайно полезный навык для успеха в этой сфере. <br>" +
                        "<br>" +
                        "Профобласти, где больше всего пригодятся творцы-артисты: <br>" +
                        "<ul>" +
                        "<li>Дизайн</li>" +
                        "<li>Фотография</li>" +
                        "<li>Мода</li>" +
                        "<li>Музыка</li>" +
                        "<li>Кино</li>" +
                        "<li>Видеопроизводство</li>" +
                        "<li>Литература</li>" +
                        "</ul>" +
                        "Примеры профессий в данных областях: дизайнер; фотограф; видеограф; ательер; арт-директор; " +
                        "продюсер; режиссер; декоратор; флорист; видеооператор; видеомонтажер; актер/актриса; ювелир; пресс-секретарь; сценарист; " +
                        "копирайтер; искусствовед; хореограф; вокалист." +
                        "Бонус! Попробуйте нашу маску в Инстаграм (нажите, чтобы узнать свою профессию)" +
                        "<a href='https://www.instagram.com/a/r/?effect_id=1012474792460678'>instagram.com/UHR_mask</a>"
                ;

        String htmlMsgEn = "<br>" +
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
        String htmlEn =
                "<h2 style='text-align: center;'>" + "Hello, " + result.getName() + "!</h2>" +
                        "<p style='text-align: center;'>" + res + "</p>"
                        + htmlMsgEn;

        String htmlRu =
                "<h2 style='text-align: center;'>" + "Привет, " + result.getName() + "!</h2>" +
                        "<p style='text-align: center;'>" + res + "</p>"
                        + htmlMsgRu;

        try {
            if ("ru".equals(lang)) {
                helper.setText(htmlRu, true);
            } else {
                helper.setText(htmlEn, true);
            }
            helper.setTo(result.getEmail());
            helper.setSubject("Your test results");
            helper.setFrom("info@urhumanresource@mail.com");
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
        emailSender.send(mimeMessage);
    }


}
