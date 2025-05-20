Start TRANSACTION ;
-- 0 insert into roles
INSERT INTO roles ( name) VALUES ( 'ROLE_USER');
INSERT into roles ( name) VALUES ( 'ROLE_ADMIN');

-- 1. Insert into user
INSERT INTO user (active, email, name, password_hash, profile_picture, user_created) VALUES
  (1, 'dave@gmail.com', 'Dave', '$2a$10$LR5aS5YJrUMu8WV6r8eCEOkjwNGaX0C8GVdReSdRYihwMTMi.CQLW', 'https://picsum.photos/201', NOW()),
  (1, 'Chuck@gmail.com', 'Chuck', '$2a$10$bk.YyJIlzffXtir.zjuDkuduc1YDxcbRa1po4vb/V9rb8Q7Kqjwye', 'https://picsum.photos/200', NOW());

-- 2. Insert into roles
insert into user_role (user_id, role_id) values
(1, 1),
(2, 1),
(2, 2);

-- 2. Insert into course
INSERT INTO course (category, closest_course, credits, description, diff_level, hours_week, img_link, related_cert, title, cert_link  ) VALUES
 ('Information Technologies', '2025-06-03', 7.5, 'Embark on a transformative
learning experience with our expert-level online course, "Real-Time Programming in Java." Designed for seasoned developers
and Java enthusiasts seeking mastery in real-time applications, this advanced course delves deep into the intricacies of
leveraging Java for mission-critical systems. Explore cutting-edge concepts such as multithreading, synchronization,
and low-latency programming, equipping you with the skills needed to build responsive and robust real-time solutions.
Led by industry experts with extensive hands-on experience, this course combines theoretical insights with practical
application, ensuring you not only grasp the theoretical underpinnings but also gain the proficiency to implement
real-time solutions confidently. Elevate your Java programming',
  3, 40, 'https://picsum.photos/200', 'Java SE 17 Programmer Professional',
  'Real-Time Programming in Java', 'https://education.oracle.com/java-se-17-developer/pexam_1Z0-829'),

 ('Information Technologies', '2025-07-10', 2.0, 'Dive into the fundamentals of
database management with our beginner-level online course, "Introduction to SQL Essentials." Geared towards those new to
the world of databases and SQL, this course provides a comprehensive foundation for understanding and utilizing SQL for
effective data management. While MySQL is touched upon to broaden your practical knowledge, the core focus is on SQL''s
universal principles applicable across various database systems. Led by seasoned instructors, the course covers database
design, querying data, and basic data manipulation using SQL commands. With a hands-on approach, you''ll engage in
practical exercises to reinforce your learning, ensuring you gain the skills necessary to navigate and interact with
databases confidently. Whether you''re a budding developer, analyst, or anyone eager to harness the power of databases,
this course offers an accessible entry point into the world of SQL, setting the stage for your future success in
data-driven environments.', 1, 20, 'https://picsum.photos/210', 'SQL Fundamentals',
  'Introduction to SQL Essentials', 'https://education.oracle.com/products/pexam_1Z0-071'),

 ('Information Technologies', '2025-08-05', 4.0, 'Embark on your journey into
web development with our beginner-level online course, "Creating Web Applications with .NET." Tailored for those stepping
into the dynamic world of web development, this course provides a solid foundation in utilizing the versatile
.NET framework to build powerful and interactive web applications. Guided by experienced instructors, you''ll explore
fundamental concepts such as web application architecture, user interface design, and server-side scripting using .NET
technologies like ASP.NET. Throughout the course, you''ll engage in hands-on projects that walk you through the entire
development process, from designing responsive user interfaces to implementing server-side functionality. Gain practical
skills in C# programming and discover how to leverage the robust features of .NET to bring your web applications to life.
Whether you''re a programming novice or transitioning from another language, this course offers a welcoming entry point
into the exciting realm of web application development with .NET, setting you on a path to create dynamic and engaging
online experiences.',
  1, 40, 'https://picsum.photos/220', '.Net Developer Fundamentals',
  'Creating Web Application with .Net','https://learn.microsoft.com/en-us/credentials/certifications/exams/98-361/'),

 ('Information Technologies', '2025-08-05', 2.0, 'Embark on your cloud
computing journey with our beginner-level online course, "Azure Fundamentals," meticulously crafted to prepare you
for the AZ-900 exam. Whether you''re new to cloud technologies or seeking to validate your foundational knowledge,
this course provides a comprehensive introduction to Microsoft Azure, one of the industry''s leading cloud platforms.
Delve into the essentials of cloud concepts, Azure services, pricing, and compliance, all while guided by expert
instructors who understand the importance of building a strong cloud foundation. Through a combination of engaging
lectures, hands-on labs, and real-world scenarios, you''ll gain practical insights into deploying solutions on Azure and
mastering fundamental cloud principles. By the end of the course, you''ll not only be well-prepared to ace the AZ-900
exam but will also have a solid understanding of Azure''s capabilities, empowering you to confidently navigate the vast
landscape of cloud computing. Join us on this educational journey and unlock the potential of cloud technology with Azure
Fundamentals.',
  1, 10, 'https://picsum.photos/230', 'AZ-900 Azure Fundamentals',
  'Azure Fundamentals', 'https://learn.microsoft.com/en-us/credentials/certifications/azure-fundamentals/?practice-assessment-type=certification'),

 ('Information Technologies', '2025-09-02', 4.0, 'Elevate your cloud
expertise with our intermediate-level online course, "Azure Administrator," meticulously designed to prepare you for
the AZ-104 exam – your gateway to becoming a Microsoft Certified Cloud Administrator. Tailored for individuals with a
foundational understanding of Azure, this course takes a deep dive into advanced administration and management tasks,
honing the skills required for efficient cloud operations. Led by seasoned Azure professionals, you''ll explore intricate
topics such as virtual networking, identity management, and governance strategies, gaining hands-on experience through
practical exercises and real-world scenarios. The course''s comprehensive coverage aligns seamlessly with the AZ-104
exam objectives, ensuring that you not only pass the certification but emerge as a proficient Azure Administrator capable
of implementing robust cloud solutions. Whether you''re looking to enhance your career or solidify your position as a
cloud expert, this course is your key to mastering the intricacies of Azure administration and achieving Microsoft
Certified Cloud Administrator status. Join us on this transformative journey towards advanced Azure proficiency.',
  2, 5, 'https://picsum.photos/240', 'AZ-104 Microsoft Certified Cloud Administrator',
  'Azure Administration','https://learn.microsoft.com/en-us/credentials/certifications/azure-administrator/?practice-assessment-type=certification'),

 ('Information Technologies', '2025-09-09', 2.0, 'Discover the fundamentals of cloud computing in
our beginner-level online course, "AWS Cloud Practitioner," designed to prepare you for the CLF-C02 certification exam.
Tailored for individuals with minimal prior experience in cloud technologies, this course provides a robust foundation
in understanding the essential concepts of Amazon Web Services (AWS). Led by experienced AWS professionals, the course
delves into core topics, including cloud architecture, AWS services, security, and pricing models. Through dynamic
lectures and hands-on labs, you''ll gain practical insights into navigating the AWS console, setting up basic
infrastructure, and comprehending key cloud principles. By the course''s end, you''ll be well-equipped to excel in the
CLF-C02 exam and possess a foundational understanding of AWS, empowering you to confidently explore and leverage cloud
services. Join us in this educational journey, and initiate your AWS Cloud Practitioner certification with assurance and proficiency.',
  1, 20, 'https://picsum.photos/250', 'CLF-C02 AWS Certified Cloud Practitioner',
  'AWS Cloud Practitioner', 'https://aws.amazon.com/certification/certified-cloud-practitioner/'),

 ('Digital Marketing', '2025-08-05', 2.0, 'Deepen your expertise in the digital landscape
with our intermediate-level online course, "Search Engine Optimization (SEO)." Tailored for marketers, business owners,
and digital enthusiasts looking to refine their online presence, this course takes a comprehensive dive into the
intricacies of SEO strategies and techniques. Led by seasoned SEO professionals, the course covers advanced topics
such as keyword research, on-page and off-page optimization, technical SEO, and analytics. Through a blend of
theoretical insights and practical exercises, you''ll learn how to enhance website visibility, improve search engine
rankings, and drive organic traffic effectively. Stay ahead in the ever-evolving digital landscape by mastering the art
and science of SEO. Whether you''re aiming to boost your business''s online visibility or embark on a career in digital
marketing, this course equips you with the skills and knowledge needed to navigate the complexities of SEO with
confidence and success. Join us and elevate your digital presence with our intermediate-level SEO course.',
  2, 4, 'https://picsum.photos/260', 'SEO Wizard',
  'Search Engine Optimization','https://skillshop.docebosaas.com/learn'),

 ('Digital Marketing', '2025-08-05', 2.0, 'Elevate your digital marketing prowess
with our intermediate-level online course, "Social Media Marketing." Tailored for marketers, business professionals,
and enthusiasts seeking to harness the power of social platforms, this course provides an in-depth exploration of
advanced social media marketing strategies. Led by industry experts, you''ll delve into nuanced topics such as audience
targeting, content optimization, social media advertising, and analytics. Through a blend of theoretical insights and
hands-on exercises, you''ll gain practical skills to create compelling social media campaigns, foster audience
engagement, and measure the impact of your efforts. Stay at the forefront of digital marketing trends by mastering the
art of crafting impactful narratives, building brand loyalty, and leveraging diverse social channels. Whether you aim
to enhance your business''s online presence or advance your career in digital marketing, this course equips you with
the tools and knowledge to navigate the dynamic landscape of social media marketing with confidence and proficiency.
Join us and amplify your social media marketing expertise with our intermediate-level course.'
 , 2, 4, 'https://picsum.photos/270', 'Certified Social Alchemist',
  'Social Media Marketing','https://samanthacameronsocial.co.uk/social-media-alchemist-academy/'),

 ('Business and Entrepreneurship', '2025-06-03', 10.0, 'Master the art of
strategic thinking with our expert-level online course, "Business Strategy." Tailored for seasoned professionals,
entrepreneurs, and strategic leaders, this course offers an immersive exploration of advanced business strategy concepts
and applications. Led by industry thought leaders, you''ll delve into intricate topics such as competitive analysis,
market positioning, disruptive innovation, and global strategic management. Through case studies, simulations, and
real-world scenarios, you''ll sharpen your ability to make informed strategic decisions that drive long-term success.
This course goes beyond the basics, challenging you to synthesize diverse business elements into a cohesive and
forward-thinking strategy. Whether you aspire to lead a multinational corporation or refine your entrepreneurial
ventures, our expert-level Business Strategy course empowers you to navigate complex business landscapes with foresight
and precision. Join us in this transformative learning journey and elevate your strategic acumen to new heights.',
  3, 10, 'https://picsum.photos/280', 'Certified Strategic Business Architect (CSBA)',
  'Business Strategy',' https://www.businessarchitectureguild.org/page/certification'),

 ('Data Science and Analytics', '2025-08-19', 2.0, 'Embark on your journey into
the exciting realm of artificial intelligence with our beginner-level online course, "Machine Learning Basics with
Python." Tailored for individuals new to the world of machine learning, this course provides a comprehensive
introduction to the fundamental concepts and techniques using the versatile Python programming language. Led by
experienced instructors, you''ll explore the basics of supervised and unsupervised learning, delve into popular
machine learning algorithms, and gain hands-on experience through practical exercises. No prior coding experience is
required, making this course an ideal starting point for beginners eager to grasp the essentials of machine learning.
By the end of the course, you''ll have a solid foundation in using Python for machine learning applications, empowering
you to unravel the mysteries of data and embark on a fascinating journey into the world of intelligent algorithms.
Join us and demystify the basics of machine learning with Python in this accessible and empowering course.',
  1, 10, 'https://picsum.photos/290', 'Machine Learning Fundamentals',
  'Machine Learning Basics with Python','https://learn.microsoft.com/en-us/credentials/certifications/azure-ai-fundamentals/?practice-assessment-type=certification'),

 ('Data Science and Analytics', '2025-09-02', 4.0, 'Deepen your expertise in the
realm of artificial intelligence with our intermediate-level online course, "Image Recognition with Python." Tailored
for those with a foundational understanding of machine learning, this course immerses you in the intricacies of image
recognition techniques and technologies using the powerful Python programming language. Led by seasoned instructors,
you''ll explore advanced concepts such as convolutional neural networks (CNNs), image preprocessing, and transfer
learning. Through hands-on projects and real-world applications, you''ll sharpen your skills in training models to
recognize and classify images with precision. This course is ideal for individuals looking to expand their knowledge in
computer vision and image processing, and it serves as a stepping stone for professionals aspiring to integrate image
recognition capabilities into their projects. Join us in this intermediate-level course, and unlock the potential of
image recognition with Python, advancing your proficiency in the exciting field of artificial intelligence.',
  2, 20, 'https://picsum.photos/300', 'Machine Vision Associate',
  'Image Recognition','https://aws.amazon.com/certification/certified-machine-learning-engineer-associate/'),

 ('Data Science and Analytics', '2025-08-19', 2.0, 'Embark on your data journey
with our beginner-level online course, "Databricks Fundamentals." Designed for individuals new to the world of big data
and analytics, this course offers a comprehensive introduction to the essential concepts of Databricks, a leading
unified analytics platform. Led by experienced instructors, you''ll navigate through the fundamentals of data
exploration, data engineering, and collaborative data science using Databricks. No prior experience with big data
technologies is required, making this course an ideal starting point for beginners eager to harness the power of
Databricks for streamlined data processing and analysis. By the end of the course, you''ll have a solid foundation
in using Databricks to uncover insights from large datasets, setting you on a path towards mastering the intricacies
of modern data analytics. Join us and demystify the fundamentals of Databricks in this accessible and empowering course.',
  1, 10, 'https://picsum.photos/310', 'Databricks Practitioner',
  'Databricks fundamentals','https://www.databricks.com/learn/certification/machine-learning-associate
');

-- 3. Insert into course_provider
INSERT INTO course_provider (alt_logo_link, logo_link, name) VALUES
('https://picsum.photos/201', 'https://picsum.photos/201', 'NTNU'),                       -- 1
('https://picsum.photos/211', 'https://picsum.photos/211', 'Oracle'),                     -- 2
('https://picsum.photos/221', 'https://picsum.photos/221', 'Apache Software Foundation'), -- 3
('https://picsum.photos/231', 'https://picsum.photos/231', 'Pearson'),                    -- 4
('https://picsum.photos/241', 'https://picsum.photos/241', 'Microsoft'),                  -- 5
('https://picsum.photos/251', 'https://picsum.photos/251', 'Amazon'),                     -- 6
('https://picsum.photos/261', 'https://picsum.photos/261', 'Adobe'),                      -- 7
('https://picsum.photos/271', 'https://picsum.photos/271', 'Handelshøyskolen BI'),        -- 8
('https://picsum.photos/281', 'https://picsum.photos/281', 'University of Oslo'),         -- 9
('https://picsum.photos/291', 'https://picsum.photos/291', 'University of Bergen'),      -- 10
('https://picsum.photos/311', 'https://picsum.photos/311', 'Google'),                    -- 11
('https://picsum.photos/321 ', 'https://picsum.photos/321', 'Apple');                    -- 12


-- 7. Insert into offerable_courses
INSERT INTO offerable_courses (date, discount, price, visibility, course_id, provider_id)VALUES
-- Real-Time Programming in Java
('2025-06-20', 0.0, 29999.00, 1, 1, 1),
('2025-06-03', 0.0, 32000.00, 1, 1, 2),
-- Introduction to SQL Essentials
('2025-06-10', 0.0, 8348.88, 1, 2, 3),
('2025-07-05', 0.0, 10000.00, 1, 2, 2),
('2025-07-05', 0.0, 9382.00, 1, 2, 4),
-- Creating Web Application with .Net
('2025-10-05', 0.0, 2999.00, 1, 3, 5),
('2025-09-05', 0.0, 3000.00, 1, 3, 4),
('2025-08-05', 0.0, 2087.00, 1, 3, 2),
-- Azure Fundamentals
('2025-09-05', 0.0, 2087.00, 1, 4, 5),
('2025-09-05', 0.0, 1800.00, 1, 4, 1),
('2025-08-05', 0.0, 2087.00, 1, 4, 4),
 -- Azure Administration
('2025-10-02', 0.0, 4174.00, 1, 5, 5),
('2025-10-05', 0.0, 3600.00, 1, 5, 1),
('2025-10-05', 0.0, 4174.00, 1, 5, 4),
-- AWS Cloud Practitioner
('2025-10-09', 0.0, 1043.00, 1, 6, 6),
('2025-11-05', 0.0, 1252.00, 1, 6, 4),
('2025-11-05', 0.0, 1800.00, 1, 6, 1),
-- Search Engine Optimization
('2025-08-05', 0.0, 66000.00, 1, 7, 7),
('2025-08-15', 0.0, 80000.00, 1, 7, 12),
('2025-08-15', 0.0, 62616.00, 1, 7, 11),
('2025-08-15', 0.0, 62616.00, 1, 7, 5),
('2025-08-15', 0.0, 62616.00, 1, 7, 6),
-- Social Media Marketing
('2025-08-05', 0.0, 66000.00, 1, 8, 7),
('2025-08-20', 0.0, 80000.00,1, 8, 12),
('2025-08-20', 0.0, 62616.00,1, 8, 11),
('2025-08-20', 0.0, 62616.00,1, 8, 5),
('2025-08-20', 0.0, 62616.00,1, 8, 6),
-- Business Strategy
('2025-06-03', 0.0, 50000.00, 1, 9, 1),
('2025-07-05', 0.0, 50000.00, 1, 9, 5),
('2025-07-05', 0.0, 50000.00, 1, 9, 9),
-- Machine Learning Basics with Python
('2025-08-20', 0.0, 20000.00, 1, 10, 1),
('2025-08-20', 0.0, 20000.00, 1, 10, 9),
('2025-08-19', 0.0, 20000.00, 1, 10, 10),
-- Image Recognition
('2025-09-02', 0.0, 30000.00, 1, 11, 1),
('2025-09-05', 0.0, 30000.00, 1, 11, 9),
('2025-09-05', 0.0, 30000.00, 1, 11, 10),
-- Databricks fundamentals
('2025-08-19', 0.0, 20000.00, 1, 12, 1),
('2025-08-20', 0.0, 20000.00, 1, 12, 9),
('2025-08-20', 0.0, 20000.00, 1, 12, 10);

INSERT INTO review (comment, rating, review_date, title) VALUES
 ('Very informative and easy to follow.', 5, '2025-05-01', 'Excellent Course'),
 ('Rewards hard work, excellent subject matter', 4, '2025-05-05', 'Hard'),
 ('Pointless course, nothing of value to learn', 1, '2025-05-05', 'Useless'),
 ('Look at this photograph', 2, '2025-05-05', 'Boring'),
 ('Grøtten var veldig god', 3, '2025-05-05', 'Decent'),
 ('Good content but a bit fast-paced.', 5, '2025-05-05', 'Magnificent'),
 ('Content needs more examples.', 3, '2025-05-10', 'Could Be Better');

insert into user_course (user_id, course_id, timestamp,  review_id) values
(1, 1,'2025-05-01' , 1),
(1, 4,'2025-05-01' , null),
(1, 5,'2025-05-01' , null),
(2, 2,'2025-05-01' , 2),
(1, 2,'2025-05-01' , 4),
(1, 3,'2025-05-01' , 5),
(2, 3,'2025-05-01' , 7),
(2, 1,'2025-05-01' , 6);

-- 5. Insert into favorite
-- Assuming users (1,2,3) and courses (1,2,3)
INSERT INTO favorite (course_id, user_id) VALUES
(1, 1),
(2, 2);

-- 6. Insert into keywords
INSERT INTO keywords (keyword, course_id) VALUES
-- Real-Time Programming in Java
 ('Java', 1),
 ('real-time programming', 1),
 ('multi-threading', 1),
-- Introduction to SQL Essentials
 ('SQL', 2),
 ('relational databases', 2),
 ('MySQL', 2),
-- Creating Web Application with .Net
 ('web', 3),
 ('programming', 3),
 ('.net', 3),
 ('C#', 3),
-- Azure Fundamentals
 ('Azure', 4),
 ('cloud services', 4),
-- Azure Administration
 ('Azure', 5),
 ('cloud services', 5),
 ('administration', 5),
-- AWS Cloud Practitioner
 ('AWS', 6),
 ('cloud services', 6),
-- Search Engine Optimization
('research and analysis', 7),
('technical SEO optimization', 7),
('off-page SEO strategies', 7),
('advanced analytics and reporting', 7),
-- Social Media Marketing
 ('strategic storytelling', 8),
 ('targeted engagement techniques', 8),
 ('data-driven optimization', 8),
-- Business Strategy

-- Machine Learning Basics with Python
 ('Python', 10),
 ('machine learning', 10),
 ('programming', 10),
 ('data science', 10),
-- Image Recognition
 ('Python', 11),
 ('machine learning', 11),
 ('programming', 11),
 ('data science', 11),
 ('neural networks', 11),
 ('image processing', 11),
-- Databricks fundamentals
 ('Python', 12),
 ('machine learning', 12),
 ('programming', 12),
 ('data science', 12),
 ('neural networks', 12),
 ('Databricks', 12);

insert into transaction(user_id, offerable_courses_id, time_of_transaction, price_paid) values
    (1, 1,'2025-05-01' , 29999.00),
    (1, 2,'2025-05-01' , 8348.88),
    (1, 3,'2025-05-01' , 32000.00),
    (2, 4,'2025-05-01' , 2087.00),
    (2, 5,'2025-05-01' , 4174.00),
    (2, 6,'2025-05-01' , 1043.00);

update offerable_courses set discount = round(rand(), 2) where 1=1;



select  * from offerable_courses;