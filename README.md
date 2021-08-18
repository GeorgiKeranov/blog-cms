# Blog

Blog MVC web application with extra REST API bulit with Java and Spring.

You can check the android app that uses REST API from this project [here](https://github.com/GeorgiKeranov/BlogApp).

## Table of contents
- [Technologies used](#technologies-used)
- [Functionalities](#functionalities)
- [Video Preview](#video-preview)
- [Pictures](#pictures)
- [REST Documentation](#rest-documentation)
    - [Register](#register)
    - [Login](#login)
    - [Logout](#logout)
    - [Check if user is authenticated](#check-if-user-is-authenticated)
    - [Get authenticated user](#get-authenticated-user)
    - [Edit authenticated user's account details](#edit-authenticated-users-account-details)
    - [Get user by url](#get-user-by-url)
    - [Create new post](#create-new-post)
    - [Comment on post by id](#comment-on-post-by-id)
    - [Reply on comment by post id and comment id](#reply-on-comment-by-post-id-and-comment-id)
    - [Get post by id](#get-post-by-id)
    - [Get posts](#get-posts)
    - [Get posts by page for user given by userUrl](#get-posts-by-page-for-user-given-by-userurl)
    - [Get posts by page for the authenticated user](#get-posts-by-page-for-the-authenticated-user)
    - [Get author of post by post id](#get-author-of-post-by-post-id)
    - [Get comments and replies of post by post id](#get-comments-and-replies-of-post-by-post-id)
    - [Edit post by id](#edit-post-by-id)
    - [Delete post by id](#delete-post-by-id)
    - [Delete comment by id](#delete-comment-by-id)
    - [Delete reply by id](#delete-reply-by-id)


## Technologies used
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Hibernate
- Thymeleaf
- JSON
- MySQL
- JWT (Json Web Token)

## Functionalities
- Register
- Login
- Create posts with images
- Edit posts
- Add profile picture
- Edit account
- Comment and reply on posts
- Posts by all users are on the home page

## Video Preview

You can see how the project looks like with all the functionalities in this [youtube video](https://www.youtube.com/watch?v=hClXpGD-3V0).

## Pictures

![Screenshot from 2018-03-19 22-17-56](https://user-images.githubusercontent.com/22518317/129608439-aa302d49-fa38-41ef-a81a-8902a708d74c.png)
![Screenshot from 2018-03-19 22-18-32](https://user-images.githubusercontent.com/22518317/129608456-f366b9e1-a063-4f40-a3af-d562be65e442.png)
![Screenshot from 2018-03-19 22-19-12](https://user-images.githubusercontent.com/22518317/129608465-1b79fac0-d5da-4669-8df8-6c2717ee55b1.png)
![Screenshot from 2018-03-19 22-21-01](https://user-images.githubusercontent.com/22518317/129608474-c9739c48-fe4a-488c-be72-4fba4e6032e5.png)
![Screenshot from 2018-03-19 22-22-55](https://user-images.githubusercontent.com/22518317/129608492-e0dc0fee-156d-4c3c-b830-60b4965c60e9.png)
![Screenshot from 2018-03-19 22-23-06](https://user-images.githubusercontent.com/22518317/129608510-b2afa2a1-cb8c-47c4-9ada-2296c90151bc.png)
![Screenshot from 2018-03-19 22-23-21](https://user-images.githubusercontent.com/22518317/129608522-163622b7-9d22-4445-804e-105c02e304fa.png)
![Screenshot from 2018-03-19 22-23-35](https://user-images.githubusercontent.com/22518317/129608534-6713c7fd-7bb5-4e08-8cb3-1eeb76031b43.png)
![Screenshot from 2018-03-19 22-24-35](https://user-images.githubusercontent.com/22518317/129608595-540905ac-75ae-444b-bc92-2af3e62b190b.png)
![Screenshot from 2018-03-19 22-27-26](https://user-images.githubusercontent.com/22518317/129608635-2ef37807-df4d-4bf5-a64e-0dbcab85dfc9.png)
![Screenshot from 2018-03-19 22-27-33](https://user-images.githubusercontent.com/22518317/129608640-6e00c94f-efba-422e-9e02-5bf915265819.png)


## REST Documentation

### Register
URL: `/rest/register`\
Method: `POST`\
Required parameters: `firstName, lastName, username, email, password`

#### Success Response:
```JavaScript
{
  "error": false
}
```

#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "Username already exists"
}
```

--------------------------------------------------

### Login
URL: `/rest/login`\
Method: `POST`\
Required parameters: `username, password`

#### Success Response:
```JavaScript
{
  "authenticated": true
}
```

#### Error Response:
```JavaScript
{
  "authenticated": false
}
```

--------------------------------------------------

### Logout
URL: `/rest/logout`\
Method: `POST`

#### Success Response:
```JavaScript
{
  "authenticated": false
}
```

--------------------------------------------------


### Check if user is authenticated
URL: `/rest/authentication`\
Method: `GET`

#### Success Response:
```JavaScript
{
  "authenticated": true
}
```

#### Error response:
```JavaScript
{
  "authenticated": false
}
```

--------------------------------------------------

### Get authenticated user
URL: `/rest/account`\
Method: `GET`

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

--------------------------------------------------

### Edit authenticated user's account details
URL: `/rest/account`\
Method: `POST`\
Required parameters: `firstName, lastName, email, currPassword`\
Optional parameters: `picture(image file), newPassword`

#### Success Response:
```JavaScript
{
  "error": false
}
```

#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "Current password is invalid"
}
```

--------------------------------------------------

### Get user by url
URL: `/rest/{userUrl}`\
Method: `GET`

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

--------------------------------------------------


### Create new post
URL: `/rest/posts/create`\
Method: `POST`\
Required parameters: `title, description`\
Optional parameters: `picture(image file)`

#### Success Response:
```JavaScript
{
  "error": false
}


```
#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "Size of image cannot be more than 10MB."
}
```

--------------------------------------------------

### Comment on post by id
URL: `/rest/posts/{id}/comment`\
Method: `POST`\
Required parameters: `comment`

#### Success Response:
```JavaScript
{
  "error": false
}
```

#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "There is no comment"
}
```

--------------------------------------------------

### Reply on comment by post id and comment id
URL: `/rest/posts/{id}/reply`\
Method: `POST`\
Required parameters: `reply, commentIdToReply`

#### Success Response:
```JavaScript
{
  "error": false
}
```

#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "The comment to reply doesn't exists."
}
```

--------------------------------------------------

### Get post by id
URL: `/rest/post/{id}`\
Method: `GET`

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

--------------------------------------------------

### Get posts
URL: `/rest/posts`\
Method: `GET`\
Optional parameters: `page`

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
    ...
]
```

--------------------------------------------------

### Get posts by page for user given by userUrl
URL: `/rest/{userUrl}/posts`\
Method: `GET`\
Optional parameters: `page`

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
    ...
]
```

--------------------------------------------------

### Get posts by page for the authenticated user
URL: `/rest/account/posts`\
Method: `GET`\
Optional parameters: `page`

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
    ...
]
```

--------------------------------------------------

### Get author of post by post id
URL: `/rest/posts/{id}/author`\
Method: `GET`

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

--------------------------------------------------

### Get comments and replies of post by post id
URL: `/rest/posts/{id}/comments`\
Method: `GET`

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

--------------------------------------------------

### Edit post by id
URL: `/rest/posts/{id}`\
Method: `POST`\
Required parameters: `title, description`\
Optional parameters: `picture(image file)`

#### Success Response:
```JavaScript
{
  "error": false
}
```

#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "Post cannot be found.(for example)"
}
```

--------------------------------------------------

### Delete post by id
URL: `/rest/posts/{id}/delete`\
Method: `POST`

#### Success Response:
```JavaScript
{
  "error": false
}
```

#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "You are not the author of that post"
}
```

--------------------------------------------------

### Delete comment by id
URL: `/rest/comment/delete`\
Method: `POST`\
Required parameters: `commentId`

#### Success Response:
```JavaScript
{
  "error": false
}
```

#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "You are not the author of the comment!"
}
```

--------------------------------------------------

### Delete reply by id
URL: `/rest/reply/delete`\
Method: `POST`\
Required parameters: `replyId`

#### Success Response:
```JavaScript
{
  "error": false
}
```

#### Error Response:
```JavaScript
{
  "error": true,
  "error_msg": "You are not the author of the comment!"
}
```
