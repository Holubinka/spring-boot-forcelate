В базі даних зв'язок один до багатьох між User та Article. User має такі поля: id, name, age. Article має такі поля: id, text, color (enum). На старті аплікації в БД повинно добавлятися 5-10 випадкових Users з Articles.

Потрібно зробити наступний АРІ:
- Дістати всіх Users, в яких age більше за якесь значення
- Дістати всіх Users з Articles, в яких color якесь певне значення з enum-а
- Дістати унікальні name з Users, в яких більше ніж 3 Articles
- Зберегти User
- Зберегти Article

Потрібно зробити наступний тип security:
- JWT-based

Потрібно написати 1-2 JUnit тести на будь який з цих методів АРІ (на контролери, на сервіси, на ДАО-рівень)
Тести повинні бути (!) якісні

Залити цей код на GitHub і написати README як проект запускати
Створити колекцію в Postman або написати запити за допомогою curl тулзи, щоб можна було легко потестувати (залити це в README або будь яким іншим відомим способом)

#### **Environment**
        OS: Linux Ubuntu 18.04.3 LTS
        Spring Boot: 2.1.4.RELEASE
        H2: 1.4.199
        
#### **Run**
 - Run Application
 
#### **H2 console**
 - configure jdbc connection, username, password in application.properties
 - jdbc:h2:tcp://localhost:9093/mem:dbname 

#### **Postman**
- postman collection in saved in postman directory
- Then you can connect to this H2 Server from outside (e.g. to your application with H2 DB 
if the application is running ) using this connection:
    `jdbc:h2:tcp://localhost:9093/mem:dbname`

#### **Queries**
 - Login in program by client 
 _POST_ http://localhost:8080/login  with credentials 
                ` {
                    "email":"email1",
                    "password":"123123"
                 }`
 - Get all users 
 _GET_  http://localhost:8080/users
 
 - Get all Users who are older than any value
 _GET_  http://localhost:8080/users/18
 
 - Get all Users from Articles in which color has some value from enum
 _GET_  http://localhost:8080/users/article/green
 
 - Get unique names from Users with more than 3 Articles
 _GET_  http://localhost:8080/users/article-great-than-3
 
 - Save User
 _POST_ http://localhost:9090/users with credentials 
        `         {
                    "email":"email10@ukr.net",
                    "password":"123123123",
                    "confirmPassword":"123123123",
                    "firstName":"name10"
                 }`
                                         
 - Save Article
 _POST_ http://localhost:9090/article  with credentials  
                ` {
                    "text":"someText",
                    "color":"GREEN"
                 } `                
