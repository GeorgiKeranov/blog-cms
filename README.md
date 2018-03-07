# Blog

Blog web application. You can register, login then you can make posts with images. You can edit your posts, add profile picture, edit account. You can comment and reply on posts. Posts by other users are shown on the home page.

It is written in Java with Spring Boot, Spring MVC, Spring Security, Spring Data JPA, Hibernate and Thymeleaf. Database used - MySQL. Also REST part of the project is used by Android application -> https://github.com/GeorgiKeranov/BlogApp


# Documentation

### POST /rest/register -> Register new user.
Required parameters -> firstName, lastName, username, email, password.

#### Successful register response:
```JavaScript
{
  "error": false
}
```

#### Unsuccessful register response:
```JavaScript
{
  "error": true,
  "error_msg": "Username already exists(for example)"
}
```
<br/>

### POST /rest/login -> Login.
Required parameters -> username, password.

#### Logged in response:
```JavaScript
{
  "authenticated": true
}
```

#### Not logged in response:
```JavaScript
{
  "authenticated": false
}
```
<br/>

### GET /rest/authentication -> Check if user is authenticated.

#### Authenticated response:
```JavaScript
{
  "authenticated": true
}
```

#### Not authenticated response:
```JavaScript
{
  "authenticated": false
}
```
<br/>

### GET /rest/account -> Get authenticated user as JSON.

#### Response:
```JavaScript
{
    "userUrl": "georgi.keranov",
    "firstName": "Georgi",
    "lastName": "Keranov",
    "email": "georgy.keranov@gmail.com",
    "profile_picture": "no"
}
```
<br/>

### POST /rest/account -> Edit authenticated user's account details.
Required parameters -> firstName, lastName, email, currPassword.\
Not required parameters -> picture(image file), newPassword.
#### Successful register response:
```JavaScript
{
  "error": false
}
```

#### Unsuccessful register response:
```JavaScript
{
  "error": true,
  "error_msg": "Current password is invalid(for example)"
}
```
<br/>

### GET /rest/{userUrl} -> Get user by url as JSON.

#### Response:
```JavaScript
{
    "userUrl": "georgi.keranov",
    "firstName": "Georgi",
    "lastName": "Keranov",
    "email": "georgy.keranov@gmail.com",
    "profile_picture": "no"
}
```
<br/>

### POST /rest/account -> Create new post.
Required parameters -> title, description.\
Not required parameters -> picture(image file).
#### Successful created post response:
```JavaScript
{
  "error": false
}
```
#### Unsuccessful create post response:
```JavaScript
{
  "error": true,
  "error_msg": "Size of image cannot be more than 10MB.(for example)"
}
```

<br/>

### POST /rest/posts/{id}/comment -> Comment on post by id.
Required parameters -> comment.

#### Successful commented response:
```JavaScript
{
  "error": false
}
```
#### Unsuccessful comment response:
```JavaScript
{
  "error": true,
  "error_msg": "There is no comment"
}
```

<br/>

### POST /rest/posts/{id}/reply -> Reply on comment by post id and comment id.
Required parameters -> reply, commentIdToReply.

#### Successful replied response:
```JavaScript
{
  "error": false
}
```
#### Unsuccessful reply response:
```JavaScript
{
  "error": true,
  "error_msg": "The comment to reply doesn't exists."
}
```

<br/>

### GET /rest/post/{id} -> Get post by id.

#### Response:
```JavaScript
{
    "id": 6,
    "title": "Post title",
    "icon": "Tue Mar 06 17:38:34 EET 2018Honda.jpg",
    "description": "Post description",
    "date": "2018-03-06 17:38:35.0"
}
```
<br/>


