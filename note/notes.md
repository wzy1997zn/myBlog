# Developing notes

This is just a note of my program developing experience. I am trying to document some useful things, but don't expect too much lol.

## 1. Demand Analysis

### 1.1 User Story

Character: Visitor, Admin:

#### Visitor: 

- ​		see all the blogs (split pages, ordered by timeline)
- ​        see recommended blogs
- ​		see all the categories
- ​		see the top (has most blogs) categories at main page
- ​		see blogs classified by categories
- ​		see all the tags
- ​		see the top tags at main page
- ​		see blogs classified by tags
- ​		see blogs classified by year
- ​		search for blogs
- ​		see detail in single blog
- ​		commit with blog content
- ​		scan QR code to see the page in WeiXin
- ​		scan QR code to follow me in WeiXin
- ​		scan QR code to donate :)

#### 	Admin(me!):

- ​		login backstage with username and password
- ​		manage blogs like:
  - publish new blog
  - set category
  - set tags
  - update blog
  - delete blog
  - search blog by topic/tag/category
- ​      manage categories like:
  - add new category
  - update category (name)
  - delete category
  - search category
- ​      manage tags like:
  - add new tag
  - update tag
  - delete tag
  - search tag



### 1.2 Design diagram

​			![brief design of myBlog](.\Design diagram.png)



## 2. Front end

### 2.1 Semantics UI

[getting start](https://semantic-ui.com/introduction/getting-started.html)

#### basic layout:

​	ui.segment: A longitudinal section

​	ui.segements: define multi-segment

​	ui.menu: change a div to menu-like

​	ui.item: items in menu

​	ui.container: basic container to show inherited layout

​	i.xxx.icon: abundant icons

​	ui.grid: divide a div to 16 parts, and use 'five wide column'/'eleven wide column' to show the width of the sub div

#### modifier:

​	teal/black...: set color

​	fluid: full fill the container

​	right.aligned: align to right

#### mobile fitting:

​	mobile reverse: reverse the order of sub divs of a div

​	@media screen and (max-width: 768px){} to define special css of mobile view

### 2.2 picsum.photos

​	A good api for random placeholders. Just a url like https://picsum.photos//800/400 can give you a random beautiful picture with fixed size. You can also set your random seed to get same picture every time like this: https://picsum.photos/seed/yourseed/800/400.



### 2.3 background patterns

​	[toptal.com](https://www.toptal.com/designers/subtlepatterns/)

   In css, set:

```css
body{background:url(yourbackgroundpattern.png);}
```



### 2.4 plugins

#### Markdown Editor:

[editor.md](http://editor.md.ipandao.com/)

#### Better type setting:

[typo.css](https://github.com/sofish/typo.css)

#### CSS animate:

[animate.css](https://github.com/daneden/animate.css)

#### Code highlighting:

[prismJS](https://github.com/PrismJS/prism)

#### QR code auto generate:

[qrcodejs](https://github.com/davidshimjs/qrcodejs)

#### Smooth scrolling:

[scrollTo](https://github.com/flesler/jquery.scrollTo)

#### Scrolling detection:

[waypoints](https://github.com/imakewebthings/waypoints)

## 3.Back end

### 3.1 Dependencies

#### basic:

```xml
spring-boot-starter-web
spring-boot-starter-test
```

#### template engine:

```xml
spring-boot-starter-thymeleaf
```

#### data:

```xml
spring-boot-starter-data-jpa
mysql-connector-java
```

#### AOP:

```xml
spring-boot-starter-aop
```

#### develop tool:

```xml
spring-boot-devtools
```

### 3.2 application.yml

#### database:

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/blog?userUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
    username: root
    password: mysql
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

Without *serverTimezone=UTC* , springboot will throw some error.  But I am not sure why.

Set *ddl-auto* to **update** is asking jpa to update the database when something not consistent.

#### logging:

```yml
logging:
  level:
    root: info
    com.zywang: debug
  file:
    name: log/blog-dev.log
```

*logging.level* shows 'how many log should be record'.

*File.name* set the position of logging file.

### 3.3 Some Annotations

Below are very simple notes of SpringMVC annotations. Detailed instructions can be found in the official [documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/). Also, there are SO many blogs talking about such things. Just Google:)

#### @Controller

One of the components' annotations, showing this class is a 'controller' (of MVC). Controller is designed for handle the requests dispatched from Servlet. It may get some data from database, transform them into a Model and send the model to specific View.

#### @RequestMapping

This annotation is used on the methods of a Controller (also the class itself). It need a parameter to set which request this method should response to. There are also *@GetMapping*, *@PostMapping*, *@PutMapping*, *@deleteMapping* for dealing with RESTful requests. They can be seen as *@RequestMapping* with 'method==RequestMethod.xxx'

#### @ControllerAdvice

An annotation for enhancing Controllers. Mostly it is used for global exception handling with the help of *@ExceptionHandler*.

#### @Aspect

Define a aspect handler for AOP.

#### @Pointcut

Define a aspect and the handling method under a *@Aspect* class.

```java
@Pointcut("execution(* com.package.*.*(..))")
```

Intercept all the classes and all the methods in the package like above.

### 3.4 Error handling

#### 404/500

Basic error states like 404 or 500 are handled by Spring Boot automatically. Just create the page files under the templates/error and you can see them when some errors happen . 

#### Global error handle

With *@ControllerAdvice* and *@ExceptionHandler*, it is easy to handle all the runtime exceptions wherever they happen.

```java
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
		......
        // send info to common error page
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
```

Handlers like above is enough to get the error info and send them to an error page. If some special exceptions with response state code are supposed to be redirected to 404 page or 500 page, add codes below to ignore them.

```java
// not handle the exceptions with response states.
if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
    throw e;
}
```

### 3.5 Log dealing with AOP

#### what is AOP

'[Aspect-oriented programming](https://en.wikipedia.org/wiki/Aspect-oriented_programming) is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns.' It is aiming to adding some functions into the program without editing the original codes. In this blog project, log is not what the blog showing system should focus on. So dealing log with AOP paradigm is a good choice.

#### log contents

- request URL
- request IP
- the called method
- args
- returned contents

#### Aspect component usage

Below defines an aspect and set some methods being called before, after, and after returning the aspect.

```java
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.zywang.myblog.controller.*.*(..))")
    public void log() {

    }
    @Before("log()")
    public void beforeAspect() {
        logger.info("----------before----------");
    }
    @After("log()")
    public void afterAspect() {
        logger.info("----------after-----------");
    }
    @AfterReturning(returning = "result", pointcut = "log()")
    public void afterReturn(Object result) {
        logger.info("Result : {}", result);
    }
}
```

When handling such a request:

```java
@GetMapping("/logTest/{id}/{name}")
public String logTest(@PathVariable Integer id,@PathVariable  String name) {
    System.out.println("----------index-----------");
    return "index";
}
```

You can see results like this:

```java
2020-05-11 23:16:51.281  INFO 12024 --- [nio-8080-exec-5] com.zywang.myblog.aspect.LogAspect       : ----------before----------
----------index-----------
2020-05-11 23:16:51.282  INFO 12024 --- [nio-8080-exec-5] com.zywang.myblog.aspect.LogAspect       : ----------after-----------
2020-05-11 23:16:51.282  INFO 12024 --- [nio-8080-exec-5] com.zywang.myblog.aspect.LogAspect       : Result : index
```

### 3.6 PO with JPA

#### E-R diagram

![E-R](C:\Users\qdwan\IdeaProjects\myBlog\note\E-R diagram.png)

#### generate a table

```java
@Entity(name = "t_blog") //announce this is a table and the related table name
public class Blog {}
```

#### set columns

```java
@Id // announce this is primary key
@GeneratedValue // primary key is auto generated
private Long id;

private String title; // simple attributes
private String content;

@Temporal(TemporalType.TIMESTAMP) // set date type in the database
private Date createdTime;
```

#### set inter-table relationships

```java
// in Blog.java
@ManyToOne // many blogs to one category
private Category category;

// in Category.java
@OneToMany(mappedBy = "category") // one category to many blogs, and this is managed by 'many side' because  'many side' update more frequently.
private List<Blog> blogs = new ArrayList<>();
```

#### constructor, getter and setter

alt+insert in IDEA :)

### 3.7 User login part

#### LoginController

To check whether the user exist, login() need *username*, *password*, pass these two parameter to **UserService** to check and generate po **User**. If login success, we need *session* to remember current user. If not, we need *redirectAttribute* to pass failure message back to client and ask to redirect.

#### UserService

interface UserFace: 

```java
public interface UserService {
    User checkUser(String username, String password);
}
```

class UserServiceImp:

```java
@Service 
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.getMD5(password)); // avoid store real password
        return user;
    }
}
```

Need *@Service* to tell this is a service, springboot can autowired it when UserService interface is used. To get a po User, we need dao **userRepository** to apply data access method.

#### UserRepository

```java
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
}
```

Just define interface obeying rules is enough by extending JpaRepository. *findByUsernameAndPasswod()* means 

```sql
select user0_.id as id1_5_,
user0_.avatar as avatar2_5_, user0_.create_time as create_t3_5_, user0_.email as email4_5_, user0_.nickname as nickname5_5_, user0_.password as password6_5_, user0_.type as type7_5_, user0_.update_time as update_t8_5_, 
user0_.username as username9_5_ 
from t_user user0_ 
where user0_.username=? and user0_.password=?
```

With Jpa, implement of the interface is not necessary. If there is no Impl, interface will generate a SimpleJpaRepository automatically.

#### Safety with MD5

Here I use MD5 for cypher password. With no real password storage, it could be safer.

#### Interceptor for admin system

Finally, all the admin things should not be accessed without login. So **LoginInterceptor** is important.

```java
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
```

Interceptor need configurer to regist in springboot IOC container.

```java
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()) // add a interceptor
                .addPathPatterns("/admin/**") // all the paths like this
                .excludePathPatterns("/admin","/admin/login");
        // but admin itself and the form submit path should be exclude to avoid dead loop.
    }
}
```

So far, most things about backend of user login part have done. Related web pages  will not be explained here :)