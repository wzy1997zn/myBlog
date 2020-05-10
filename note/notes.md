# Developing notes

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

