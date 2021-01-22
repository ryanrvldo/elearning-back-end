# E-learning Back End Application

Back end application for e-learning.

# API Spec

## Authentication

All API request must be use this authentication

Request:

- Header:
  - Authentication: "Bearer ```token```"

## STUDENT

### LOGIN

#### Request :

- Method : `POST`
- Endpoint : `/api/login`
- Body :

```json
{
  "username": "string",
  "password": "username"
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": {
    "token": "",
    "userId": "",
    "username": "",
    "roleCode": ""
  }
}
```

error

```json
{
  "code": 403,
  "result": "message"
}
```

### Register

#### Request:

- Method : `POST`
- Endpoint : `/api/student/register`
- Body :

```json
{
  "id": "",
  "firstName": "",
  "lastName": "",
  "username": "",
  "password": "",
  "email": "",
  "phone": "",
  "gender": ""
}
```

#### Response :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Dashboard Student

#### Request :

- Method : `GET`
- Endpoint : `/api/student/dashboard/:id`

### Response :

success`

```json
{
  "code": 200,
  "result": {
    "id": "",
    "photo": "",
    "firstName": "",
    "lastName": "",
    "email": "",
    "phoneNumber": "",
    "gender": "",
    "createdAt": ""
  }
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Student's Score

- Method: `GET`
- Endpoint: `api/student/:id/scores`

#### Response:

success

```json
{
  "code": 200,
  "result": {
    "grade": "",
    "code": "",
    "title": "",
    "startTime": "",
    "endTime": ""
  }
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

## COURSE

### Available Course

#### Request :

- Method : `GET`
- Endpoint : `/api/course/available`

#### Response :

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "courseCode": "",
      "courseName": "",
      "capacity": "",
      "periodStart": "",
      "periodEnd": "",
      "teacher": {
        "id": "",
        "code": "",
        "firstName": "",
        "lastName": "",
        "tittle": ""
      },
      "courseCategory": ""
    }
  ]
}
```

### DETAIL COURSE

#### Request :

- Method : `GET`
- Endpoint : `/api/course/:id`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "tittle": "",
      "description": "",
      "subjectName": "",
      "schedule": {
        "id": "",
        "date": "",
        "start_time": "",
        "end_time": ""
      }
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### REGISTER COURSE

#### Request :

- Method : `POST`
- Endpoint : `/api/course/register`
- Body :

```json
{
  "studentId": "",
  "courseId": ""
}
```

#### Response :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### MY COURSE

#### Request :

- Method : `GET`
- Endpoint : `/api/student/:id/course`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "name": "",
      "capacity": "",
      "periodStart": "",
      "periodEnd": "",
      "category": "",
      "teacher": {
        "id": "",
        "code": "",
        "firstName": "",
        "lastName": "",
        "tittle": ""
      }
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

## ATTENDANCE

### Create attendance

#### Request :

- Method : `POST`
- Endpoint : `/api/student/attendance`
- Body:

```json
{
  "moduleId": "",
  "studentId": ""
}
```

#### Response :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

## MODULE

### Detail Module

#### Request:

- Method: `GET`
- Endpoint: `api/module/:id`

#### Response

success

```json
{
  "code": 200,
  "result": {
    "title": "",
    "code": "",
    "description": "",
    "files": [
      {
        "id": "",
        "name": "",
        "extensions": ""
      }
    ],
    "schedule": {
      "date": "",
      "startTime": "",
      "endTime": ""
    }
  }
}
```

## EXAM

### DETAIL MODULE EXAM

#### Request:

- Method: `GET`
- ENDPOINT: `api/exam/module/:id`

#### Response:

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "description": "",
      "type": "",
      "start_time": "",
      "end_time": "",
      "file": [
        {
          "id": "",
          "name": "",
          "extensions": ""
        }
      ]
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Send a student's exam

#### Request:

- Method: `POST`
- Endpoint: `api/exam/student/`
- Body:

```json
{
  "examId": "",
  "studentId": "",
  "file": {
    "id": "",
    "data": ""
  }
}
```

#### Response

```json
{
  "code": 200,
  "result": "message"
}
```

### Send a teacher's exam

#### Request

- Method: `POST`
- Endpoint: `api/exam/module/`

```json
{
  "moduleId": "",
  "description": "",
  "startTime": "",
  "endTime": "",
  "files": [
    {
      "id": "",
      "data": ""
    }
  ]
}
```

### DETAIL MODULE FORUM

#### Request:

- Method: `GET`
- Endpoint: `api/forum/module/:id`

#### Response:

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "content": "",
      "message_time": "",
      "user": {
        "id": "",
        "firstName": "",
        "lastName": "",
        "roleCode": "",
        "photo_id": ""
      }
    }
  ]
}
```

#### Request:

- Method: `POST`
- Endpoint: `api/forum/module/:id`
- body:

```json
{
  "content": "lorem",
  "userId": ""
}
```

#### Response:

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

## Teacher

### Dashboard

#### Request

- Method: `GET`
- Endpoint: `api/teacher/dashboard/:id`

#### Response

```json
{
  "code": 200,
  "result": {
    "id": "",
    "code": "",
    "name": "",
    "description": "",
    "capacity": "",
    "student": 0,
    "periodStart": "",
    "periodEnd": ""
  }
}
```

### Get Student Who Take The Course

#### Request

- Method: `GET`
- Endpoint: `api/course/:id/students`

#### Response

```json
{
  "code": "",
  "result": [
    {
      "id": "",
      "code": "",
      "firstName": "",
      "lastName": "",
      "email": "",
      "phone": "",
      "gender": ""
    }
  ]
}
```

### Get Exam Submission

#### Request

- Method: `GET`,
- Endpoint: `api/exam/:id/submission`

#### Response

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "firstName": "",
      "lastName": "",
      "grade": "",
      "submittedDate": ""
    }
  ]
}
```

### Update Score

#### Request

- Method: `PATCH`
- Endpoint: `api/exam/submission`
- Body:

```json
{
  "submissionId": "",
  "score": ""
}
```

#### Response

```json
{
  "code": 200,
  "result": "message"
}
```

### GET Teacher Profile

- Method: `GET`
- Endpoint: `api/teacher/:id`

#### Response

```json
{
  "code": "",
  "result": {
    "teacher": {
      "firstName": "",
      "lastName": "",
      "email": "",
      "title": "",
      "createdAt": "",
      "gender": "",
      "address": ""
    },
    "experiences": [
      {
        "title": "",
        "description": "",
        "startDate": "",
        "endDate": ""
      }
    ]
  }
}
```

### PUT Teacher Profile

#### Request

- Method: `PUT`
- Endpoint: `api/teacher/:id`
- Body:

```json
{
  "teacher": {
    "firstName": "",
    "lastName": "",
    "email": "",
    "title": "",
    "createdAt": "",
    "gender": "",
    "address": ""
  },
  "experiences": [
    {
      "title": "",
      "description": "",
      "startDate": "",
      "endDate": ""
    }
  ]
}
```

### GET Student Profile

#### Request

- Method: `GET`
- Endpoint: `api/student/:id`

#### Response

```json
{
  "code": "",
  "result": {
    "firstName": "",
    "lastName": "",
    "email": "",
    "title": "",
    "createdAt": "",
    "gender": "",
    "address": ""
  }
}
```

### PUT Student Profile

#### Request

- Method: `PUT`
- Endpoint: `api/student/:id`

#### Response

```json
{
  "code": "",
  "result": {
    "firstName": "",
    "lastName": "",
    "email": "",
    "title": "",
    "createdAt": "",
    "gender": "",
    "address": ""
  }
}
```

### List Teacher

#### Request

- Method: `GET`
- Endpoint: `api/admin/teachers`

### Response

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "first_name": "",
      "last_name": "",
      "phone": "",
      "gender": "",
      "username": ""
    }
  ]
}
```

### REGISTER TEACHER

#### Request :

- Method : `POST`
- Endpoint : `/api/admin/teacher/register`
- Body :

```json
{
  "code": "",
  "first_name": "",
  "last_name": "",
  "phone": "",
  "gender": "",
  "username": "",
  "password": "",
  "email": "",
  "role_code": ""
}
```

#### Response :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### PUT Teacher Profile

#### Request

- Method: `PUT`
- Endpoint: `api/admin/teacher/:id`
- Body:

```json
{
  "firstName": "",
  "lastName": "",
  "email": "",
  "title": "",
  "createdAt": "",
  "gender": "",
  "address": ""
}
```

Response :

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### DELETE Teacher Profile

#### Request

- Method: `DELETE`
- Endpoint: `api/admin/teacher/:id`

#### Response

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 404,
  "result": "message"
}
```

### Get List Course

#### Request :

- Method : `GET`
- Endpoint : `/api/admin/courses`

#### Response :

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "courseCode": "",
      "courseName": "",
      "typeName": "",
      "capacity": "",
      "periodStart": "",
      "status": "",
      "periodEnd": "",
      "courseCategory": "",
      "description": ""
    }
  ]
}
```

### Create Course

#### Request :

- Method : `POST`
- Endpoint : `/api/admin/course`
- Body:

```json
{
  "courseCode": "",
  "courseName": "",
  "typeName": "",
  "capacity": "",
  "periodStart": "",
  "status": "",
  "periodEnd": "",
  "courseCategory": "",
  "description": "",
  "teacherId": ""
}
```

#### RESPONSE :

success

```json
{
  "code": 201,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Delete Course

#### Request :

- Method : `DELETE`
- EndPoint : `/api/admin/course/:id`

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Subject Categories

#### Request

- Method : `GET`
- Endpoint : `/api/admin/subject-categories`

#### Response :

success

```json
{
  "code": 200,
  "result": [
    {
      "id": "",
      "code": "",
      "description": "",
      "subjectName": ""
    }
  ]
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Update Categories

#### Request :

- Method : `PUT`
- Endpoint : `/api/admin/subject-categories`
- Body:

```json
{
  "code": "",
  "description": "",
  "subjectName": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```

### Add Categories

#### Request :

- method : `POST`
- Endpoint : `/api/admin/subject-categories`
- Body:

```json
{
  "code": "",
  "description": "",
  "subjectName": ""
}
```

#### Response :

success

```json
{
  "code": 200,
  "result": "message"
}
```

error

```json
{
  "code": 400,
  "result": "message"
}
```
