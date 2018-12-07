DELETE FROM section;
DELETE FROM contact;
DELETE FROM resume;

INSERT INTO resume (uuid, full_name) VALUES
  ('34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1', 'Ann One'),
  ('a92b2748-f01d-4109-b933-a298def6d8bb', 'Bob Two'),
  ('b372af5b-7c53-4a70-a918-dc63c02171ab', 'Joe Three');

insert into contact(type, value, resume_uuid) VALUES
  ('PHONE_NUMBER', '+7 921 123 45 67', '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('SKYPE', 'AnnOneskype.address', '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('EMAIL', 'ann@mail.com', '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('LINKEDIN', 'www.lnkd/AnnOne.com', '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('HOME_PAGE', 'www.Annsite.com', '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),

  ('PHONE_NUMBER', '+7 905 987 65 43', 'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('SKYPE', 'BobTwoSkype.address', 'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('EMAIL', 'Bob@mail.com', 'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('LINKEDIN', 'www.lnkd/BobTwo.com', 'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('HOME_PAGE', 'www.BobSite.com', 'a92b2748-f01d-4109-b933-a298def6d8bb'),

  ('PHONE_NUMBER', '+7 999 111 33 33', 'b372af5b-7c53-4a70-a918-dc63c02171ab'),
  ('EMAIL', 'Joe@mail.com', 'b372af5b-7c53-4a70-a918-dc63c02171ab');

insert into section(section_type, section_value, resume_uuid) VALUES
  ('OBJECTIVE', '{"CLASSNAME":"ru.javawebinar.basejava.model.TextSection","INSTANCE":{"content":"Ведущая стажировок и' ||
                ' корпоративного обучения по Java Web и Enterprise технологиям"}}', '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('PERSONAL', '{"CLASSNAME":"ru.javawebinar.basejava.model.TextSection","INSTANCE":{"content":"Аналитический склад ума,' ||
               ' сильная логика, креативность, инициативность. Пурист кода и архитектуры."}}',
   '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('ACHIEVEMENT', '{"CLASSNAME":"ru.javawebinar.basejava.model.ListSection","INSTANCE":{"items":["С 2013 года: разработка' ||
                  ' проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность.' ||
                  ' XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн' ||
                  ' стажировок и ведение проектов. Более 1000 выпускников.","Реализация двухфакторной аутентификации для' ||
                  ' онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator,' ||
                  ' Jira, Zendesk.","Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.' ||
                  ' Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке:' ||
                  ' Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция' ||
                  ' CIFS/SMB java сервера.","Реализация c нуля Rich Internet Application приложения на стеке технологий' ||
                  ' JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга."' ||
                  ',"Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов' ||
                  ' (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии' ||
                  ' через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)."]}}'
                  ,'34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('QUALIFICATIONS', '{"CLASSNAME":"ru.javawebinar.basejava.model.ListSection","INSTANCE":{"items":["JEE AS:' ||
                     ' GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2","Version control: Subversion,' ||
                     ' Git, Mercury, ClearCase, Perforce","DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis),' ||
                     ' H2, Oracle","MySQL, SQLite, MS SQL, HSQLDB","Languages: Java, Scala, Python/Jython/PL-Python,' ||
                     ' JavaScript, Groovy"]}}',
                  '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('EXPERIENCE', '{"CLASSNAME":"ru.javawebinar.basejava.model.OrganizationSection","INSTANCE":{"organizations":[{"homePage":' ||
                 '{"name":"Organisation1","url":""},"positions":[{"startDate":{"year":2009,"month":1,"day":1},"endDate":{"year":2010,' ||
                 '"month":1,"day":1},"position":"org1_pos","description":"org1_posDescr"},{"startDate":{"year":2010,"month":1,"day":1},' ||
                 '"endDate":{"year":2012,"month":6,"day":1},"position":"org1_pos2","description":"org1_posDescr2"}]},{"homePage":' ||
                 '{"name":"Organisation2","url":"www.org2.com"},"positions":[{"startDate":{"year":2012,"month":7,"day":1},"endDate":' ||
                 '{"year":3000,"month":1,"day":1},"position":"org2_pos","description":"org2_posDescr"}]}]}}',
   '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('EDUCATION', '{"CLASSNAME":"ru.javawebinar.basejava.model.OrganizationSection","INSTANCE":{"organizations":[{"homePage":' ||
                '{"name":"University1","url":"www.uni1.com"},"positions":[{"startDate":{"year":2000,"month":9,"day":1},"endDate"' ||
                ':{"year":2005,"month":6,"day":1},"position":"student","description":""}]},{"homePage":{"name":"University2","url":' ||
                '"www.uni2.com"},"positions":[{"startDate":{"year":2005,"month":9,"day":1},"endDate":{"year":2007,"month":6,"day":1},' ||
                '"position":"student","description":""}]}]}}',
   '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),

  ('OBJECTIVE', '{"CLASSNAME":"ru.javawebinar.basejava.model.TextSection","INSTANCE":{"content":"Bob Twoposition content"}}',
   'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('PERSONAL', '{"CLASSNAME":"ru.javawebinar.basejava.model.TextSection","INSTANCE":{"content":"Bob Two personal content"}}',
   'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('ACHIEVEMENT', '{"CLASSNAME":"ru.javawebinar.basejava.model.ListSection","INSTANCE":{"items":["Bob Two achievement content1",' ||
                  '"Bob Two achievement content2"]}}',
   'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('QUALIFICATIONS', '{"CLASSNAME":"ru.javawebinar.basejava.model.ListSection","INSTANCE":{"items":["Bob Two qualifications content1",' ||
                     '"Bob Two qualifications content2"]}}',
   'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('EXPERIENCE', '{"CLASSNAME":"ru.javawebinar.basejava.model.OrganizationSection","INSTANCE":{"organizations":[{"homePage":{"name":' ||
                 '"Organisation1","url":""},"positions":[{"startDate":{"year":2009,"month":1,"day":1},"endDate":{"year":2010,"month":1,"day":1},' ||
                 '"position":"org1_pos","description":"org1_posDescr"},{"startDate":{"year":2010,"month":1,"day":1},"endDate":{"year":2012,"month":6,' ||
                 '"day":1},"position":"org1_pos2","description":"org1_posDescr2"}]},{"homePage":{"name":"Organisation2","url":"www.org2.com"},' ||
                 '"positions":[{"startDate":{"year":2012,"month":7,"day":1},"endDate":{"year":3000,"month":1,"day":1},"position":"org2_pos",' ||
                 '"description":"org2_posDescr"}]}]}}',
   'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('EDUCATION', '{"CLASSNAME":"ru.javawebinar.basejava.model.OrganizationSection","INSTANCE":{"organizations":[{"homePage":{"name":' ||
                '"University1","url":"www.uni1.com"},"positions":[{"startDate":{"year":2000,"month":9,"day":1},"endDate":{"year":2005,' ||
                '"month":6,"day":1},"position":"student","description":""}]},{"homePage":{"name":"University2","url":"www.uni2.com"},' ||
                '"positions":[{"startDate":{"year":2005,"month":9,"day":1},"endDate":{"year":2007,"month":6,"day":1},"position":"student",' ||
                '"description":""}]}]}}',
    'a92b2748-f01d-4109-b933-a298def6d8bb'),

  ('OBJECTIVE', '{"CLASSNAME":"ru.javawebinar.basejava.model.TextSection","INSTANCE":{"content":"Joe Three position content"}}',
   'b372af5b-7c53-4a70-a918-dc63c02171ab'),
  ('PERSONAL', '{"CLASSNAME":"ru.javawebinar.basejava.model.TextSection","INSTANCE":{"content":"Joe Three personal content"}}',
   'b372af5b-7c53-4a70-a918-dc63c02171ab'),
  ('ACHIEVEMENT', '{"CLASSNAME":"ru.javawebinar.basejava.model.ListSection","INSTANCE":{"items":["Joe achievement content1",' ||
                  '"achievement content2"]}}',
   'b372af5b-7c53-4a70-a918-dc63c02171ab'),
  ('QUALIFICATIONS', '{"CLASSNAME":"ru.javawebinar.basejava.model.ListSection","INSTANCE":{"items":["qualifications content1",' ||
                     '"qualifications content2"]}}',
   'b372af5b-7c53-4a70-a918-dc63c02171ab');