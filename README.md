# Blog
#
# Blog web application. You can register, login then you can make posts with images. You can edit your posts, add profile picture, edit account. You can comment and reply on posts. Posts by other users are shown on the home page.
#
# It is written in java with Spring Boot, Spring MVC, Spring Security, Spring Data Jpa, Hibernate and Thymeleaf.
# Database : MySql , Connector : mysql-connector.
#
# If you want to run it in InteliJ :
# 1. Set your language level (in ProjectSettings/Modules/(Click on the folder with project name "Blog") then change Language level to 8 - Lambdas...)
# 2. File/Settings then click on Build, Execution, Deployment/Compiler/Java Compiler/
# and then in left have "Target bytecode version" - change it this to 1.8.
#
# Configure for your machine :
# 1. Open src/main/java/blog/application.properties
# 2. Change "spring.jpa.hibernate.ddl-auto = update" to create only for first running.
# 3. Change "spring.datasource.driver-class-name" to your database driver.
# 4. Change spring.datasource.url = jdbc:mysql://localhost:3306/db_blog
# to  jdbc:mysql://localhost:3306/{here your database name} (if you are on MySQL).
# 5. Change "spring.datasource.username = root" to your username.
# 6. Change "spring.datasource.password = root" to your password.
# 7. Make folder OUT OF THE PROJECT for your resources!!
# 8. To map this folder go to "src/main/java/blog/" and open SpringBoot file
# find .addResourceLocations("file:/home/georgi/Server/"); and change 
# to your folder location, created earlier in step 7.
# For example(windows) .addResourceLocations("file:///C:/ServerResources/");
# For example(linux) .addResourceLocations("file:/home/{your account name}/ServerResources/");
# Run application from SpringBoot file as java application.
# And you are ready, application is hosted on localhost:8080.
