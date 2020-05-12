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