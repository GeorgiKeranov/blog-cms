# Blog

Blog web application. You can register, login then you can make posts with images. You can edit your posts, add profile picture, edit account. You can comment and reply on posts. Posts by other users are shown on the home page.

It is written in Java with Spring Boot, Spring MVC, Spring Security, Spring Data JPA, Hibernate and Thymeleaf. Database used - MySQL. Also REST part of the project is used by Android application -> https://github.com/GeorgiKeranov/BlogApp

# Pictures and video
## Pictures -> https://georgikeranov.com/project/Blog%20%28Web%20and%20REST%29
## Video -> https://www.youtube.com/watch?v=hClXpGD-3V0

# REST Documentation

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

### POST /rest/logout -> Logout of the account.

#### Logged out response:
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

### GET /rest/posts?page=0 -> Get posts by page.
Not required param -> page

#### Response:
```JavaScript
[
    {
        "id": 11,
        "title": "Title 6",
        "icon": "Wed Mar 07 12:57:25 EET 201820180209_125457.jpg",
        "description": "Title 6",
        "date": "2018-03-07 12:57:25.0"
    },
    {
        "id": 10,
        "title": "Title 5",
        "icon": "Wed Mar 07 12:56:51 EET 201820180227_182216.jpg",
        "description": "Desc 5",
        "date": "2018-03-07 12:56:51.0"
    },
    {
        "id": 9,
        "title": "Fourth Title",
        "icon": "no",
        "description": "Fourth Descripttionnnnn.",
        "date": "2018-03-07 12:56:26.0"
    },
    {
        "id": 8,
        "title": "Thrid title",
        "icon": "Wed Mar 07 12:53:34 EET 201820180218_192757.jpg",
        "description": "Third description.",
        "date": "2018-03-07 12:53:35.0"
    },
    {
        "id": 7,
        "title": "Second title",
        "icon": "Wed Mar 07 12:52:36 EET 201811158036_866579163413641_491502118_n.jpg",
        "description": "Second description",
        "date": "2018-03-07 12:52:37.0"
    }
]
```
<br/>

### GET /rest/{userUrl}/posts?page=0 -> Get posts by page for user given by userUrl.
Not required param -> page

#### Response:
```JavaScript
[
    {
        "id": 11,
        "title": "Title 6",
        "icon": "Wed Mar 07 12:57:25 EET 201820180209_125457.jpg",
        "description": "Title 6",
        "date": "2018-03-07 12:57:25.0"
    },
    {
        "id": 10,
        "title": "Title 5",
        "icon": "Wed Mar 07 12:56:51 EET 201820180227_182216.jpg",
        "description": "Desc 5",
        "date": "2018-03-07 12:56:51.0"
    },
    {
        "id": 9,
        "title": "Fourth Title",
        "icon": "no",
        "description": "Fourth Descripttionnnnn.",
        "date": "2018-03-07 12:56:26.0"
    },
    {
        "id": 8,
        "title": "Thrid title",
        "icon": "Wed Mar 07 12:53:34 EET 201820180218_192757.jpg",
        "description": "Third description.",
        "date": "2018-03-07 12:53:35.0"
    },
    {
        "id": 7,
        "title": "Second title",
        "icon": "Wed Mar 07 12:52:36 EET 201811158036_866579163413641_491502118_n.jpg",
        "description": "Second description",
        "date": "2018-03-07 12:52:37.0"
    }
]
```
<br/>

### GET /rest/account/posts?page=0 -> Get posts by page for the authenticated user.
Not required param -> page

#### Response:
```JavaScript
[
    {
        "id": 11,
        "title": "Title 6",
        "icon": "Wed Mar 07 12:57:25 EET 201820180209_125457.jpg",
        "description": "Title 6",
        "date": "2018-03-07 12:57:25.0"
    },
    {
        "id": 10,
        "title": "Title 5",
        "icon": "Wed Mar 07 12:56:51 EET 201820180227_182216.jpg",
        "description": "Desc 5",
        "date": "2018-03-07 12:56:51.0"
    },
    {
        "id": 9,
        "title": "Fourth Title",
        "icon": "no",
        "description": "Fourth Descripttionnnnn.",
        "date": "2018-03-07 12:56:26.0"
    },
    {
        "id": 8,
        "title": "Thrid title",
        "icon": "Wed Mar 07 12:53:34 EET 201820180218_192757.jpg",
        "description": "Third description.",
        "date": "2018-03-07 12:53:35.0"
    },
    {
        "id": 7,
        "title": "Second title",
        "icon": "Wed Mar 07 12:52:36 EET 201811158036_866579163413641_491502118_n.jpg",
        "description": "Second description",
        "date": "2018-03-07 12:52:37.0"
    }
]
```

<br/>

### GET /rest/posts/{id}/author -> Get author of post by post id.

#### Response:
```JavaScript
{
    "userUrl": "georgi.keranov",
    "firstName": "Georgi",
    "lastName": "Keranov",
    "email": "georgy.keranov@gmail.com",
    "profile_picture": "Tue Mar 06 17:38:12 EET 20182009-03-27-bikepics-1614374-full.jpg"
}
```

<br/>

### GET /rest/posts/{id}/comments -> Get comments and replies of post by post id.

#### Response:
```JavaScript
[
    {
        "id": 9,
        "comment": "first comment",
        "author": {
            "userUrl": "dimitur.ivanov",
            "firstName": "Dimitur",
            "lastName": "Ivanov",
            "email": "dimitur.ivanov@abv.bg",
            "profile_picture": "no"
        },
        "date": "2018-03-07 13:20:45.0",
        "replies": [
            {
                "id": 15,
                "reply": "first reply for first comment",
                "author": {
                    "userUrl": "dimitur.ivanov",
                    "firstName": "Dimitur",
                    "lastName": "Ivanov",
                    "email": "dimitur.ivanov@abv.bg",
                    "profile_picture": "no"
                },
                "date": "2018-03-07 13:21:04.0"
            }
        ]
    },
    {
        "id": 10,
        "comment": "second comment",
        "author": {
            "userUrl": "dimitur.ivanov",
            "firstName": "Dimitur",
            "lastName": "Ivanov",
            "email": "dimitur.ivanov@abv.bg",
            "profile_picture": "no"
        },
        "date": "2018-03-07 13:20:51.0",
        "replies": [
            {
                "id": 16,
                "reply": "second reply for second comment",
                "author": {
                    "userUrl": "dimitur.ivanov",
                    "firstName": "Dimitur",
                    "lastName": "Ivanov",
                    "email": "dimitur.ivanov@abv.bg",
                    "profile_picture": "no"
                },
                "date": "2018-03-07 13:21:13.0"
            }
        ]
    }
]
```

<br/>

### POST /rest/posts/{id} -> Edit 
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
  "error_msg": "Post cannot be found.(for example)"
}
```

<br/>

### POST /rest/posts/{id}/delete -> Delete post by id.
#### Successful deleted:
```JavaScript
{
  "error": false
}
```
#### Unsuccessful deleted:
```JavaScript
{
  "error": true,
  "error_msg": "You are not the author of that post"
}
```

<br/>

### POST /rest/comment/delete -> Delete comment by id.
Required params: commentId.
#### Successful deleted:
```JavaScript
{
  "error": false
}
```
#### Unsuccessful deleted:
```JavaScript
{
  "error": true,
  "error_msg": "You are not the author of the comment!"
}
```

<br/>

### POST /rest/reply/delete -> Delete reply by id.
Required params: replyId.
#### Successful deleted:
```JavaScript
{
  "error": false
}
```
#### Unsuccessful deleted:
```JavaScript
{
  "error": true,
  "error_msg": "You are not the author of the comment!"
}
```





