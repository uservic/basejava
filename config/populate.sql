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
  ('OBJECTIVE', 'Ведущая стажировок и корпоративного обучения ' ||
                'по Java Web и Enterprise технологиям', '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('PERSONAL', 'Аналитический склад ума, сильная логика, креативность, инициативность.' ||
               ' Пурист кода и архитектуры.', '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('ACHIEVEMENT', 'С 2013 года: разработка проектов "Разработка Web приложения", "Java Enterprise",' ||
                  ' "Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP).' ||
                  ' Удаленное взаимодействие (JMS/AKKA)".\nОрганизация онлайн стажировок и ведение проектов.' ||
                  ' Более 1000 выпускников.\nРеализация двухфакторной аутентификации для онлайн платформы управления' ||
                  ' проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. Налаживание' ||
                  ' процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM,' ||
                  ' CMIS, LDAP.\nРазработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery.\n' ||
                  ' Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.'
                  ,'34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),
  ('QUALIFICATIONS', 'JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\nVersion control:' ||
                     ' Subversion, Git, Mercury, ClearCase, Perforce\nDB: PostgreSQL(наследование, pgplsql, PL/Python),' ||
                     ' Redis (Jedis), H2, Oracle\nLanguages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy',
                  '34fe9bd7-45e8-40ec-9074-d91e8cd1e4c1'),

  ('OBJECTIVE', 'Bob position content', 'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('PERSONAL', 'Bob personal content', 'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('ACHIEVEMENT', 'Bob achievement content1\nBob achievement content2', 'a92b2748-f01d-4109-b933-a298def6d8bb'),
  ('QUALIFICATIONS', 'Bob qualifications content1\nBob qualifications content2', 'a92b2748-f01d-4109-b933-a298def6d8bb'),

  ('OBJECTIVE', 'Joe position content', 'b372af5b-7c53-4a70-a918-dc63c02171ab'),
  ('PERSONAL', 'Joe personal content', 'b372af5b-7c53-4a70-a918-dc63c02171ab'),
  ('ACHIEVEMENT', 'Joe achievement content1\nJoe achievement content2', 'b372af5b-7c53-4a70-a918-dc63c02171ab'),
  ('QUALIFICATIONS', 'Joe qualifications content1\nJoe qualifications content2', 'b372af5b-7c53-4a70-a918-dc63c02171ab');