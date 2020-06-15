package com.example.demo.controller;

import com.example.demo.domain.Result;
import com.example.demo.domain.Rewiev;
import com.example.demo.repository.ResultRepository;
import com.example.demo.repository.RewievRepository;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;

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
    public String getIndex(@RequestBody Result result, @RequestHeader("Accept-Language") String langHeader) throws IOException, URISyntaxException {

        String lang = langHeader.substring(0, 2);
        System.out.println(lang);

        sendEmail(result, lang);
        return "redirect:/";
    }


    private void sendEmail(Result result, String lang) throws URISyntaxException, IOException {
        System.out.println("in send email");
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        //System.out.println("before sending");
        //System.out.println(getClass().getClassLoader().getResource("sendMessageRu").toURI());

        //URI uriRu = getClass().getClassLoader().getResource("sendMessageRu").toURI();
        //String htmlMsgRu = new String(Files.readAllBytes(Paths.get(uriRu)));

        //URI uriEn = getClass().getClassLoader().getResource("sendMessageEn").toURI();
        //String htmlMsgEn = new String(Files.readAllBytes(Paths.get(uriEn)));

        //System.out.println(uriEn);
        //System.out.println(uriRu);


        String res = result.getOuts()
                .replaceAll("%. ", "%<br>");

        String res2 = res
                .replaceAll("<li>", "")
                .replaceAll("</li>", "");

        String[] resArray = res2.split("<br>");

        String htmlEn =
                "<div    style=\"background:#000000;\n" +
                        "        color: white;\">\n" +
                        "<div style=\"padding-top: 2rem; margin: 0 4rem;\"> <h2 style='text-align: center;'>" + "Hello, " + result.getName() + "!</h2>" +
                        "<p style='text-align: center;'>" + res + "</p>"
                        + "<br>\n" +
                        "                <h2 style='text-align: center;'> Result Interpretation<br>\n" +
                        "                Professional activity profiles</h2>\n" +
                        "\n" +
                        "                All people are divided on 5 groups by professional activity type:\n" +
                        "                <ol>\n" +
                        "                <li>Theorist-researcher</li>\n" +
                        "                <li>Technician-executor</li>\n" +
                        "                <li>Leader-teacher</li>\n" +
                        "                <li>Social activist-philanthropist</li>\n" +
                        "                <li>Creator-artist</li>\n" +
                        "                 </ol>\n" +
                        "                Different goals, work with people features, work process and results are characteristic for every group.<br>\n" +
                        "                <br>\n" +
                        "                <br>\n" +
                        "                <h2>Theorist-researcher</h2><br>\n" +
                        "                These are people whose most important values are knowledge and truth. Their work requires deepening in the details of the specialty, the thirst for new knowledge and an objective judgment about the world. Depending on the specialty, research theorists can to one degree or another interact with other people, but more often their work is secluded. These are people who can defend their point of view, resorting to the facts and results of their own research and search.<br>\n" +
                        "                <br>\n" +
                        "                Professional areas where research theorists are most useful:\n" +
                        "                <ul>\n" +
                        "                <li>Medical Science</li>\n" +
                        "                <li>Engineer Science</li>\n" +
                        "                <li>Physics</li>\n" +
                        "                <li>Maths</li>\n" +
                        "                <li>Biology</li>\n" +
                        "                <li>Chemistry</li>\n" +
                        "                <li>Geological Sciences</li>\n" +
                        "                <li>IT</li>\n" +
                        "                <li>Humanitarian Sciences</li>\n" +
                        "                </ul>\n" +
                        "                Examples of professions in these areas: physiologist; pathophysiologist; microbilogue; biologist; process \n" +
                        "                chemist; Data Analyst, Linguist, research engineer, IT specialist, laboratory assistant, geologist, ecologist, \n" +
                        "                geophysicist, guide.<br>\n" +
                        "                <br>\n" +
                        "                <br>\n" +
                        "                <h2>Technician-executor</h2><br>\n" +
                        "                Despite the name, this group includes not only technical professions, but also all specialties that are \n" +
                        "                characterized by the performance of work. These are people who act in accordance \n" +
                        "                with clear instructions. Compliance with algorithms and standards is easy for them. Their work requires \n" +
                        "                perseverance, patience and the ability to follow instructions without departing from them. Depending on \n" +
                        "                their specialization, performers to one degree or another interact with people, but more often their work \n" +
                        "                is not solitary, they can work together with colleagues who perform the same or other duties.<br>\n" +
                        "                <br>\n" +
                        "                Professional areas, where performers are most useful:<br>\n" +
                        "                <ul>\n" +
                        "                <li>Constructing</li>\n" +
                        "                <li>Manufacturing</li>\n" +
                        "                <li>Accounting</li>\n" +
                        "                <li>Jurisprudence</li>\n" +
                        "                <li>Sport, Fitness</li>\n" +
                        "                <li>Public Service</li>\n" +
                        "                </ul>\n" +
                        "                Examples of professions in these areas: design engineer; builder; cook; production technologist; \n" +
                        "                metallurgist; Energy Engineer; process engineer; pharmacist; electrician-installer; driver; master of \n" +
                        "                construction works; locksmith; driver; accountant; collector; auditor; financial analyst; lawyer; realtor; \n" +
                        "                notary; lawyer; sports instructor; trainer; inspector; Secretary policeman.<br>\n" +
                        "                <br>\n" +
                        "                <br>\n" +
                        "                <h2>Leader-teacher</h2><br>\n" +
                        "                For this group of people, the most suitable are professions where a person somehow realizes his \n" +
                        "                inclinations and skills of a leader and manager. Such work requires a person to be able to work with \n" +
                        "                people, good speech skills and the absence of constraint in front of a large audience. And how to implement \n" +
                        "                these skills depends on the goal of the person himself: he can direct them into the mainstream of teaching, \n" +
                        "                entrepreneurship or management. In any case, it is necessary to be able to clearly set goals and work out an \n" +
                        "                action strategy (in the case of teaching, a training program).<br>\n" +
                        "                <br>\n" +
                        "                Professional areas, where the most useful is a teaching leaders:<br>\n" +
                        "                <ul>\n" +
                        "                <li>Selling</li>\n" +
                        "                <li>Administrative Staff</li>\n" +
                        "                <li>Marketing</li>\n" +
                        "                <li>Consulting</li>\n" +
                        "                <li>Management</li>\n" +
                        "                <li>Education</li>\n" +
                        "                </ul>\n" +
                        "                Examples of professions in these areas: customer service manager; shop assistant; Sales Manager; \n" +
                        "                Sales Representative; call center employee; Office Manager; assistant manager; PR specialist; marketer; \n" +
                        "                promoter; business trainer; Director recruitment manager (recruiter); teacher.<br>\n" +
                        "                <br>\n" +
                        "                <br>\n" +
                        "                <h2>Social activist-philanthropist</h2><br>\n" +
                        "                These people are primarily driven by a desire to help others, and already in the second - to engage \n" +
                        "                in self-development, financially provide for themselves, etc. However, there are professions that perfectly \n" +
                        "                combine these two aspects. In addition to the desire to help people, you need the ability to see social \n" +
                        "                problems, as well as the skill to find their solutions. The art of persuasion and empathy may turn out to be \n" +
                        "                important here, depending on the chosen specialty.<br>\n" +
                        "                <br>\n" +
                        "                Professional areas, where social activists-philanthropists are most useful:<br>\n" +
                        "                <ul>\n" +
                        "                <li>Occupational Safety and Health</li>\n" +
                        "                <li>Certification</li>\n" +
                        "                <li>Medicine</li>\n" +
                        "                <li>Safety</li>\n" +
                        "                </ul>\n" +
                        "                Examples of professions in these areas: labor safety specialist; Licensing security guard; the auditor; \n" +
                        "                Therapist; Nurse; psychologist; firefighter of the Emergency Management organisation; sanitary inspector.<br>\n" +
                        "                <br>\n" +
                        "                <br>\n" +
                        "                <h2>Creator-artist</h2><br>\n" +
                        "                Surely you have some kind of creative skill: fine art (including painting and sculpture), \n" +
                        "                music, photography, video production, theater and cinema, etc. For creativity to become a matter \n" +
                        "                of your life, you need to prepare for serious competition, as well as have communication and \n" +
                        "                persuasion skills. Creativity can generate income when you learn to sell it. In addition, \n" +
                        "                despite the prejudices, the work of the creator also consists of certain obligations: to complete \n" +
                        "                the work on time, despite the lack of inspiration or muse, and also to perform it qualitatively. Therefore, \n" +
                        "                the ability to organize your time is another extremely useful skill for success in this area.<br>\n" +
                        "                <br>\n" +
                        "                Professional areas where artists and creators are most useful:<br>\n" +
                        "                <ul>\n" +
                        "                <li>Design</li>\n" +
                        "                <li>Photography</li>\n" +
                        "                <li>Fashion</li>\n" +
                        "                <li>Music</li>\n" +
                        "                <li>Cinema</li>\n" +
                        "                <li>Video production</li>\n" +
                        "                <li>Literature</li>\n" +
                        "                </ul>\n" +
                        "                Examples of professions in these areas: designer; photographer; videographer; atelier; Art Director; \n" +
                        "                producer; film director; decorator; florist; cameraman; video editor; actor / actress; jeweler; screenwriter; \n" +
                        "                copywriter; art critic; choreographer; vocalist.\n" +
                        "                Bonus! Look at our mask on Instagram (tap to find out your profession):\n" +
                        "                <a href='https://www.instagram.com/a/r/?effect_id=1012474792460678'>instagram.com</a>";

        String htmlRu =
                "<div    style=\"background:#000000;\n" +
                        "        color: white;\">\n" +
                        "<div style=\"margin: 0 4rem; padding-top: 2rem;\"> <h2 style='text-align: center;'>" + "Привет, " + result.getName() + "!</h2>" +
                        "Наш алгоритм проанализировал Ваши ответы, и вот результат." +
                        "<p style='text-align: center;'>" + res + "</p>"
                        + "  <style>\n" +
                        "   hr {border: none; background-color: white; color: white; height: 4px; width: 80%;}" +
                        "  </style>\n" +
                        "Что это означает?\n" +
                        "Наши исследования показывают, что человек наиболее успешен в определенной\n" +
                        "сфере, если эта область действительно ему подходит. Чем выше проценты\n" +
                        "определенного профессионального профиля, тем выше шансы получить идеальную\n" +
                        "работу в связанной сфере.\n" +
                        "               <br> <br>\n" +
                        "<hr>\n" +
                        "                        <h2 style='text-align: center;'> Интерпретация результатов<br>\n" +
                        "                        Профили профессиональной деятельности</h2>\n" +
                        "\n" +
                        "                        По типу профессиональной деятельности всех людей можно разделить на 5 групп:\n" +
                        "                        <ul>\n" +
                        "                        <li>Теоретик-исследователь</li>\n" +
                        "                        <li>Техник-исполнитель</li>\n" +
                        "                        <li>Лидер-преподаватель</li>\n" +
                        "                        <li>Общественник-филантроп</li>\n" +
                        "                        <li>Творец-артист</li>\n" +
                        "                         </ul>\n" +
                        "                        Для каждой группы характерны свои цели, особенности работы с людьми, процесса работы и результаты.\n" +
                        "                        Профессиональные профили, как черты темперамента, смешиваются в каждом из нас, но обычно один профиль\n" +
                        "                        проявляется больше других." +
                        "<br><br><br>\n" +
                        "<hr><br>\n" +
                        "                        <h2>Теоретик-исследователь</h2>\n" +
                        resArray[0] + "<br><br>" +
                        "                        Это люди, у которых важнейшие ценности – это знание и правда. Их работа требует углубления в детали специальности, жажду новых знаний и объективное суждение о мире. В зависимости от специальности\n" +
                        "                        теоретики- исследователи могут в той или иной степени взаимодействовать с другими людьми, но чаще работа у них уединенная. Это люди, способные отстаивать свою точку зрения, прибегая к фактам и результатам\n" +
                        "                        собственных исследований и поисков.<br>\n" +
                        "                        <br>\n" +
                        "                        Профобласти, где больше всего пригодятся теоретики-исследователи:\n" +
                        "                        <ul>\n" +
                        "                        <li>Медицинские науки</li>\n" +
                        "                        <li>Инженерные науки</li>\n" +
                        "                        <li>Физика</li>\n" +
                        "                        <li>Математика</li>\n" +
                        "                        <li>Биология</li>\n" +
                        "                        <li>Химия</li>\n" +
                        "                        <li>Науки о Земле</li>\n" +
                        "                        <li>IT-технологии</li>\n" +
                        "                        <li>Гуманитарные науки</li>\n" +
                        "                        </ul>\n" +
                        "                        Примеры профессий в данных областях: физиолог; патофизиолог; микробилог; биолог;\n" +
                        "                        химик-технолог; дата-аналитик; лингвист; инженер-исследователь; ИТ-специалист;\n" +
                        "                        лаборант; геолог; эколог; геофизик; экскурсовод." +
                        "<br><br><br>\n" +
                        "<hr><br>\n" +
                        "                        <h2>Техник-исполнитель</h2>\n" +
                        resArray[1] + "<br><br>" +
                        "                        Несмотря на название, к этой группе относятся не только технические профессии, но и все специальности, для которых характерен исполнительский труд.\n" +
                        "                        Это люди, эффективно действующие по четким инструкциям. Соблюдение алгоритмов и стандартов им дается легко. Их работа требует усидчивости,\n" +
                        "                        терпеливости и умения следовать инструкциям, не отклоняясь от них.\n" +
                        "                        Техники-исполнители в зависимости от специальности в той или иной степени взаимодействуют с людьми,\n" +
                        "                        но чаще их работа не уединенная, они могут работать вместе с коллегами, выполняющими такие же или другие обязанности.<br>\n" +
                        "                        <br>\n" +
                        "                        Профобласти, где больше всего пригодятся техники-исполнители:<br>\n" +
                        "                        <ul>\n" +
                        "                        <li>Строительство</li>\n" +
                        "                        <li>Производство</li>\n" +
                        "                        <li>Бухгалтерия</li>\n" +
                        "                        <li>Юриспруденция</li>\n" +
                        "                        <li>Спорт, фитнес</li>\n" +
                        "                        <li>Госслужба</li>\n" +
                        "                        </ul>\n" +
                        "                        Примеры профессий в данных областях: инженер-конструктор; строитель; повар; технолог на производстве;\n" +
                        "                        металлург; инженер-энергетик; инженер-технолог; фармацевт; электрик-монтажник; водитель; мастер\n" +
                        "                        строительно-монтажных работ; слесарь; машинист; бухгалтер; инкассатор; аудитор; финансовый аналитик; адвокат; риэлтор;\n" +
                        "                        нотариус; юрист; спортивный инструктор; тренер; инспектор; судебный пристав; секретарь; полицейский.<br>\n" +
                        "                        <br>\n" +
                        "                        <br>\n" +
                        "<hr><br>\n" +
                        "                        <h2>Лидер-преподаватель</h2>\n" +
                        resArray[2] + "<br><br>" +
                        "                        Для этой группы людей наиболее подходящими являются профессии, где человек так или иначе реализует свои\n" +
                        "                        склонности и навыки лидера и управленца. Такая работа требует от человека умения работать с людьми,\n" +
                        "                        хороших речевых навыков и отсутствия стеснения перед большой аудиторией. А как реализовать эти навыки и\n" +
                        "                        умения, зависит от цели самого человека: он может направить их в русло преподавания, предпринимательства или менеджмента.\n" +
                        "                        В любом случае необходимо уметь четко выставлять цели и прорабатывать\n" +
                        "                        стратегию действий (в случае преподавательской деятельности – программу обучения).<br>\n" +
                        "                        <br>\n" +
                        "                        Профобласти, где больше всего пригодятся лидеры-преподаватели:<br>\n" +
                        "                        <ul>\n" +
                        "                        <li>Продажи</li>\n" +
                        "                        <li>Административный персонал</li>\n" +
                        "                        <li>Маркетинг</li>\n" +
                        "                        <li>Консультирование</li>\n" +
                        "                        <li>Управление персоналом</li>\n" +
                        "                        <li>Образование</li>\n" +
                        "                        </ul>\n" +
                        "                        Примеры профессий в данных областях: менеджер по работе с клиентами; продавец-консультант; менеджер по продажам;\n" +
                        "                        торговый представитель; сотрудник call-центра; офис-менеджер; помощник руководителя; PR-специалист; маркетолог;\n" +
                        "                        промоутер; бизнес-тренер; директор; менеджер по подбору персонала (рекрутер); преподаватель.<br>\n" +
                        "                        <br>\n" +
                        "                        <br>\n" +
                        "<hr><br>\n" +
                        "                        <h2>Общественник-филантроп</h2>\n" +
                        resArray[3] + "<br><br>" +
                        "                        Такими людьми в первую очередь движет желание помочь окружающим, а уже во вторую – заниматься саморазвитием,\n" +
                        "                        материально обеспечить себя самого и т.д. Впрочем, существуют профессии, которые прекрасно сочетают в себе два этих аспекта.\n" +
                        "                        Помимо желания помогать людям, нужно умение видеть социальные проблемы, а также навык поиска их решений.\n" +
                        "                        Немаловажным здесь может оказаться искусство убеждения и эмпатии, в зависимости от выбранной специальности.<br>\n" +
                        "                        <br>\n" +
                        "                        Профобласти, где больше всего пригодятся общественники-филантропы:<br>\n" +
                        "                        <ul>\n" +
                        "                        <li>Охрана труда</li>\n" +
                        "                        <li>Сертификация</li>\n" +
                        "                        <li>Медицина</li>\n" +
                        "                        <li>Безопасность</li>\n" +
                        "                        </ul>\n" +
                        "                        Примеры профессий в данных областях: специалист по охране труда; специалист отдела лицензирования; охранник; ревизор;\n" +
                        "                        лечащий врач; медсестра/медбрат; психолог; пожарный МЧС; санитарный эксперт.<br>\n" +
                        "                        <br>\n" +
                        "                        <br>\n" +
                        "<hr><br>\n" +
                        "                        <h2>Творец-артист</h2>\n" +
                        resArray[4] + "<br><br>" +
                        "                        Наверняка у Вас есть какой-то творческий навык: изобразительное искусство (включая живопись и скульптуру),\n" +
                        "                        музыка, фотография, видеопроизводство, театральное и киноискусство и т.д. Чтобы творчество стало делом\n" +
                        "                        Вашей жизни, нужно приготовиться к серьезной конкуренции, а также иметь навыки общения и убеждения.\n" +
                        "                        Творчество сможет приносить доход тогда, когда Вы научитесь его продавать. К тому же, несмотря на предубеждения,\n" +
                        "                        работа творца тоже состоит из определенных обязательств: выполнять работу в срок, не взирая на отсутствие\n" +
                        "                        вдохновения или музы, а также выполнять ее качественно. Поэтому умение организовать свое время -\n" +
                        "                        еще один чрезвычайно полезный навык для успеха в этой сфере. <br>\n" +
                        "                        <br>\n" +
                        "                        Профобласти, где больше всего пригодятся творцы-артисты: <br>\n" +
                        "                        <ul>\n" +
                        "                        <li>Дизайн</li>\n" +
                        "                        <li>Фотография</li>\n" +
                        "                        <li>Мода</li>\n" +
                        "                        <li>Музыка</li>\n" +
                        "                        <li>Кино</li>\n" +
                        "                        <li>Видеопроизводство</li>\n" +
                        "                        <li>Литература</li>\n" +
                        "                        </ul>\n" +
                        "                        Примеры профессий в данных областях: дизайнер; фотограф; видеограф; ательер; арт-директор;\n" +
                        "                        продюсер; режиссер; декоратор; флорист; видеооператор; видеомонтажер; актер/актриса; ювелир; пресс-секретарь; сценарист;\n" +
                        "                        копирайтер; искусствовед; хореограф; вокалист.\n" +
                        "<br>\n" +
                        "<br>\n" +
                        "<hr><br>\n" +
                        "                        <div style =\"padding-bottom: 4rem;\">\n" +
                        "Бонус!<br> Попробуйте нашу маску в Инстаграм <br>\n" +
                        "(нажите, чтобы узнать свою профессию)\n" +
                        "                        <a href='https://www.instagram.com/a/r/?effect_id=1012474792460678'>instagram.com/UHR_mask</a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>";

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
